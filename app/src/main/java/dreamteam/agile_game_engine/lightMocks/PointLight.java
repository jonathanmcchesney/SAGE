package dreamteam.agile_game_engine.lightMocks;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import dreamteam.agile_game_engine.util.MyShaders;
import dreamteam.agile_game_engine.util.Vector3;

import static android.content.ContentValues.TAG;

/*
 * Adapted from http://www.learnopengles.com/android-lesson-two-ambient-and-diffuse-lighting/
 * This class was crated by Shane
 */
public class PointLight {

    /*
     * Variable declarations
     */
    private int mPointProgramHandle;
    String pointVertexShader, pointFragmentShader;

    private float[] mLightPosInModelSpace;
    private final float[] mLightPosInEyeSpace = new float[4];
    private final float[] mLightPosInWorldSpace = new float[4];
    private float[] mLightModelMatrix = new float[16];

    /** Constructor
     * @Param shaderPack - MyShaders object, used to get vert and frag shader Strings for OpenGL ES
     * */
    public PointLight(MyShaders shaderPack, Vector3 position)
    {

        pointVertexShader = shaderPack.getPointVertexShader();
        pointFragmentShader = shaderPack.getPointFragmentShader();
        mLightPosInModelSpace = new float[] {position.getX(), position.getY(), position.getZ(), 1.0f};

        final int pointVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
        final int pointFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);
        mPointProgramHandle = createAndLinkProgram(pointVertexShaderHandle, pointFragmentShaderHandle, new String[] {"a_Position"});
    }

    /**
     * This method draws the pointlight
     * @param mViewMatrix - the viewMatrix
     * @param mMVPMatrix - the mvpMatrix
     * @param mProjectionMatrix - projectionMatrix
     */
    public void draw(float [] mViewMatrix, float [] mMVPMatrix, float [] mProjectionMatrix)
    {
        calcLightPos(mViewMatrix);
        GLES20.glUseProgram(mPointProgramHandle);
        drawLight(mViewMatrix, mMVPMatrix, mProjectionMatrix);
    }

    /** This method calls the drawLight method.
     * @Param: mViewMatrix - ViewMatrix(Camera?) passed in from Renderer
     * @Param: mMVPMatrix - MVP Matrix passed in from Renderer
     * @Param: mProjectionMatrix - Projection matrix passed from Renderer.*/
    private void calcLightPos(float [] mViewMatrix)
    {
        Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0);
    }

    /**
     * This method creats and links the program which stores the shaders
     * @param vertexShaderHandle - the vertexShaderHandle which contains the string
     * @param fragmentShaderHandle - the fragmentShaderHandle which contains the string
     * @param attributes - the attributes the handles need to access the shaders
     * @return
     */
    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes)
    {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            if (attributes != null)
            {
                final int size = attributes.length;
                for (int i = 0; i < size; i++)
                {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            GLES20.glLinkProgram(programHandle);
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }

    /**
     * This method complies the shaders
     * @param shaderType - openGl shader type
     * @param shaderSource - the string source for each shader
     * @return - the final shader handle
     */
    private int compileShader(final int shaderType, final String shaderSource)
    {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    /**
     * This method draws the point light to the screen
     * @param mViewMatrix - the viewMatrix
     * @param mMVPMatrix - the mvpMatrix
     * @param mProjectionMatrix - the projectionMatrix
     */
    private void drawLight(float [] mViewMatrix, float [] mMVPMatrix, float [] mProjectionMatrix)
    {
        final int pointMVPMatrixHandle = GLES20.glGetUniformLocation(mPointProgramHandle, "u_MVPMatrix");
        final int pointPositionHandle = GLES20.glGetAttribLocation(mPointProgramHandle, "a_Position");


        GLES20.glVertexAttrib3f(pointPositionHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);

        GLES20.glDisableVertexAttribArray(pointPositionHandle);

        // Pass in the transformation matrix.
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mLightModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(pointMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }

    /*
     * Getters
     */
    public float[] getmLightPosInEyeSpace()
    {
        return mLightPosInEyeSpace;
    }
}
