package dreamteam.agile_game_engine.lightMocks;

import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.Log;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

    public class MockShaders implements GLSurfaceView.Renderer
    {
        private static final String LOGTAG = "MockShaders";     /** This Logging Tag will be useful for debugging.*/

        private float[] modelMatrix = new float[16],        /** This is used to move models from object space to world space. */
                        projectionMatrix = new float[16],   /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
                        viewMatrix = new float[16],         /** The "camera" of sorts. This transforms world space to eye space, eg things relative to our eye; */
                        MVPMatrix = new float[16],          /** Model + View + Projection matricies, this will be passed into shader program. */
                        lightModelMatrix = new float[16];   /** Model matrix designated for the light position. */

        private final FloatBuffer cubePositions, cubeColours, cubeNormals;        /** Cube data stored in a float buffers. */

        private int MVPMatrixHandle,          /** This will be used to pass in the transformation matrix. */
                    modelMatrixHandle,        /** This will be used to pass in the modelview matrix. */
                    lightPositionHandle,      /** This will be used to pass in the light position. */
                    modelPositionHandle,      /** This will be used to pass in model position information. */
                    modelColourHandle,        /** This will be used to pass in model color information. */
                    modelNormalHandle,        /** This will be used to pass in model normal information. */
                    programHandle,            /** Shading program. */
                    lightPointProgramHandle;  /** Light point program. */


        private final int bytesPerFloat = 4,                /** How many bytes per float. */
                          elementsPositionDataSize = 3,     /** Size of the position data in elements. */
                          elementsColourDataSize = 4,       /** Size of the color data in elements. */
                          elementsNormalDataSize = 3;       /** Size of the normal data in elements. */


        private float[] lightPositionModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f},        /** Light centered on the origin in model space. */
                        lightPositionWorldSpace = new float[4],                                /** Current position of light in world space (after model transformation). */
                        lightPositionEyeSpace = new float[4];                                  /** Current position of light in eye space (after model transformation). */

        public MockShaders()
        {

            final float[] cubePositionData =
                    {
                            // Front face
                            -1.0f, 1.0f, 1.0f,
                            -1.0f, -1.0f, 1.0f,
                            1.0f, 1.0f, 1.0f,
                            -1.0f, -1.0f, 1.0f,
                            1.0f, -1.0f, 1.0f,
                            1.0f, 1.0f, 1.0f,

                            // Right face
                            1.0f, 1.0f, 1.0f,
                            1.0f, -1.0f, 1.0f,
                            1.0f, 1.0f, -1.0f,
                            1.0f, -1.0f, 1.0f,
                            1.0f, -1.0f, -1.0f,
                            1.0f, 1.0f, -1.0f,

                            // Back face
                            1.0f, 1.0f, -1.0f,
                            1.0f, -1.0f, -1.0f,
                            -1.0f, 1.0f, -1.0f,
                            1.0f, -1.0f, -1.0f,
                            -1.0f, -1.0f, -1.0f,
                            -1.0f, 1.0f, -1.0f,

                            // Left face
                            -1.0f, 1.0f, -1.0f,
                            -1.0f, -1.0f, -1.0f,
                            -1.0f, 1.0f, 1.0f,
                            -1.0f, -1.0f, -1.0f,
                            -1.0f, -1.0f, 1.0f,
                            -1.0f, 1.0f, 1.0f,

                            // Top face
                            -1.0f, 1.0f, -1.0f,
                            -1.0f, 1.0f, 1.0f,
                            1.0f, 1.0f, -1.0f,
                            -1.0f, 1.0f, 1.0f,
                            1.0f, 1.0f, 1.0f,
                            1.0f, 1.0f, -1.0f,

                            // Bottom face
                            1.0f, -1.0f, -1.0f,
                            1.0f, -1.0f, 1.0f,
                            -1.0f, -1.0f, -1.0f,
                            1.0f, -1.0f, 1.0f,
                            -1.0f, -1.0f, 1.0f,
                            -1.0f, -1.0f, -1.0f,
                    };

            /** Red, Green, Blue, Alpha. */
            final float[] cubeColorData =
                    {
                            // Front face (red)
                            1.0f, 0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f, 1.0f,
                            1.0f, 0.0f, 0.0f, 1.0f,

                            // Right face (green)
                            0.0f, 1.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f, 1.0f,
                            0.0f, 1.0f, 0.0f, 1.0f,

                            // Back face (blue)
                            0.0f, 0.0f, 1.0f, 1.0f,
                            0.0f, 0.0f, 1.0f, 1.0f,
                            0.0f, 0.0f, 1.0f, 1.0f,
                            0.0f, 0.0f, 1.0f, 1.0f,
                            0.0f, 0.0f, 1.0f, 1.0f,
                            0.0f, 0.0f, 1.0f, 1.0f,

                            // Left face (yellow)
                            1.0f, 1.0f, 0.0f, 1.0f,
                            1.0f, 1.0f, 0.0f, 1.0f,
                            1.0f, 1.0f, 0.0f, 1.0f,
                            1.0f, 1.0f, 0.0f, 1.0f,
                            1.0f, 1.0f, 0.0f, 1.0f,
                            1.0f, 1.0f, 0.0f, 1.0f,

                            // Top face (cyan)
                            0.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 1.0f, 1.0f, 1.0f,

                            // Bottom face (magenta)
                            1.0f, 0.0f, 1.0f, 1.0f,
                            1.0f, 0.0f, 1.0f, 1.0f,
                            1.0f, 0.0f, 1.0f, 1.0f,
                            1.0f, 0.0f, 1.0f, 1.0f,
                            1.0f, 0.0f, 1.0f, 1.0f,
                            1.0f, 0.0f, 1.0f, 1.0f
                    };

            final float[] cubeNormalData =
                    {
                            // Front face
                            0.0f, 0.0f, 1.0f,
                            0.0f, 0.0f, 1.0f,
                            0.0f, 0.0f, 1.0f,
                            0.0f, 0.0f, 1.0f,
                            0.0f, 0.0f, 1.0f,
                            0.0f, 0.0f, 1.0f,

                            // Right face
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,
                            1.0f, 0.0f, 0.0f,

                            // Back face
                            0.0f, 0.0f, -1.0f,
                            0.0f, 0.0f, -1.0f,
                            0.0f, 0.0f, -1.0f,
                            0.0f, 0.0f, -1.0f,
                            0.0f, 0.0f, -1.0f,
                            0.0f, 0.0f, -1.0f,

                            // Left face
                            -1.0f, 0.0f, 0.0f,
                            -1.0f, 0.0f, 0.0f,
                            -1.0f, 0.0f, 0.0f,
                            -1.0f, 0.0f, 0.0f,
                            -1.0f, 0.0f, 0.0f,
                            -1.0f, 0.0f, 0.0f,

                            // Top face
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,
                            0.0f, 1.0f, 0.0f,

                            // Bottom face
                            0.0f, -1.0f, 0.0f,
                            0.0f, -1.0f, 0.0f,
                            0.0f, -1.0f, 0.0f,
                            0.0f, -1.0f, 0.0f,
                            0.0f, -1.0f, 0.0f,
                            0.0f, -1.0f, 0.0f
                    };

            // Initialize buffers.
            cubePositions = ByteBuffer.allocateDirect(cubePositionData.length * bytesPerFloat)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            cubePositions.put(cubePositionData).position(0);

            cubeColours = ByteBuffer.allocateDirect(cubeColorData.length * bytesPerFloat)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            cubeColours.put(cubeColorData).position(0);

            cubeNormals = ByteBuffer.allocateDirect(cubeNormalData.length * bytesPerFloat)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            cubeNormals.put(cubeNormalData).position(0);
        }

        protected String getVertexShader()
        {
            final String vertexShader =
                    "uniform mat4 u_MVPMatrix;      \n"
                            + "uniform mat4 u_MVMatrix;       \n"
                            + "uniform vec3 u_LightPos;       \n"

                            + "attribute vec4 a_Position;     \n"
                            + "attribute vec4 a_Color;        \n"
                            + "attribute vec3 a_Normal;       \n"

                            + "varying vec4 v_Color;          \n"

                            + "void main()                    \n"
                            + "{                              \n"
                            + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
                            + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
                            + "   float distance = length(u_LightPos - modelViewVertex);             \n"
                            + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
                            + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);       \n"
                            + "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));  \n"
                            + "   v_Color = a_Color * diffuse;                                       \n"
                            + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
                            + "}                                                                     \n";

            return vertexShader;
        }

        protected String getFragmentShader()
        {
            final String fragmentShader =
                    "precision mediump float;       \n"
                            + "varying vec4 v_Color;          \n"
                            + "void main()                    \n"
                            + "{                              \n"
                            + "   gl_FragColor = v_Color;     \n"
                            + "}                              \n";

            return fragmentShader;
        }

        @Override
        public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
        {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glEnable(GLES20.GL_CULL_FACE);  // Uncomment
            GLES20.glEnable(GLES20.GL_DEPTH_TEST); // Uncomment

            final float eyeX = 0.0f,  eyeY = 0.0f,  eyeZ = -0.5f,    // Eye is looking in front of the origin.
                        camX = 0.0f,  camY = 0.0f,  camZ = -5.0f,    // Camera is looking in front of origin also.
                        upX = 0.0f,   upY = 1.0f,   upZ = 0.0f;      // Up vector is set.

            // View matrix is set (camera position).
            Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, camX, camY, camZ, upX, upY, upZ);

            final String vShader = getVertexShader(), fShader = getFragmentShader();

            final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vShader),
                      fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fShader);

            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                    new String[] {"a_Position",  "a_Color", "a_Normal"});

            final String pointVertexShader =
                    "uniform mat4 u_MVPMatrix;      \n"
                            +	"attribute vec4 a_Position;     \n"
                            + "void main()                    \n"
                            + "{                              \n"
                            + "   gl_Position = u_MVPMatrix   \n"
                            + "               * a_Position;   \n"
                            + "   gl_PointSize = 5.0;         \n"
                            + "}                              \n";

            final String pointFragmentShader =
                    "precision mediump float;       \n"
                            + "void main()                    \n"
                            + "{                              \n"
                            + "   gl_FragColor = vec4(1.0,    \n"
                            + "   1.0, 1.0, 1.0);             \n"
                            + "}                              \n";

            final int pointVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader),
                      pointFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);

            lightPointProgramHandle = createAndLinkProgram(pointVertexShaderHandle, pointFragmentShaderHandle,
                    new String[] {"a_Position"});
        }

        @Override
        public void onSurfaceChanged(GL10 glUnused, int width, int height)
        {
            GLES20.glViewport(0, 0, width, height);

            final float ratio = (float) width / height,
                        bottom = -1.0f,
                        top = 1.0f,
                        right = ratio,
                        left = -ratio,
                        near = 1.0f,
                        far = 10.0f;

            Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
        }

        @Override
        public void onDrawFrame(GL10 glUnused)
        {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            // Complete rotation every 10 secs.
            long time = SystemClock.uptimeMillis() % 10000L;
            float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

            // Per-vertex light program.
            GLES20.glUseProgram(programHandle);

            // Program handles for drawing the cubes.
            MVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
            modelMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVMatrix");
            lightPositionHandle = GLES20.glGetUniformLocation(programHandle, "u_LightPos");
            modelPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
            modelColourHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");
            modelNormalHandle = GLES20.glGetAttribLocation(programHandle, "a_Normal");

            calculateLightPos(angleInDegrees);

            // draw the cubes.
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, 4.0f, 0.0f, -7.0f);
            Matrix.rotateM(modelMatrix, 0, angleInDegrees, 1.0f, 0.0f, 0.0f);
            drawCube();

            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, -4.0f, 0.0f, -7.0f);
            Matrix.rotateM(modelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
            drawCube();

            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, 0.0f, 4.0f, -7.0f);
            Matrix.rotateM(modelMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
            drawCube();

            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, 0.0f, -4.0f, -7.0f);
            drawCube();

            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, 0.0f, 0.0f, -5.0f);
            Matrix.rotateM(modelMatrix, 0, angleInDegrees, 1.0f, 1.0f, 0.0f);
            drawCube();

            // draw the point light.
            GLES20.glUseProgram(lightPointProgramHandle);
            drawLight();
        }

        private void drawCube()
        {
            // Pass in the position
            cubePositions.position(0);
            GLES20.glVertexAttribPointer(modelPositionHandle, elementsPositionDataSize, GLES20.GL_FLOAT, false,
                    0, cubePositions);

            GLES20.glEnableVertexAttribArray(modelPositionHandle);

            // Pass in the colour
            cubeColours.position(0);
            GLES20.glVertexAttribPointer(modelColourHandle, elementsColourDataSize, GLES20.GL_FLOAT, false,
                    0, cubeColours);

            GLES20.glEnableVertexAttribArray(modelColourHandle);

            // Pass in the normal
            cubeNormals.position(0);
            GLES20.glVertexAttribPointer(modelNormalHandle, elementsNormalDataSize, GLES20.GL_FLOAT, false,
                    0, cubeNormals);

            GLES20.glEnableVertexAttribArray(modelNormalHandle);

            // View * Model Matrix. Stored in MVP.
            Matrix.multiplyMM(MVPMatrix, 0, viewMatrix, 0, modelMatrix, 0);

            // Pass in ModelView matrix.
            GLES20.glUniformMatrix4fv(modelMatrixHandle, 1, false, MVPMatrix, 0);

            // ModelView * Projection Matrix, stored in MVP. MVP now has model*view*projection.
            Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, MVPMatrix, 0);

            // Pass the combined MVP matrix.
            GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);

            // Pass the light position in eye space.
            GLES20.glUniform3f(lightPositionHandle, lightPositionEyeSpace[0], lightPositionEyeSpace[1], lightPositionEyeSpace[2]);

            // draw the cube.
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
        }

        /**
         * draw the point light.
         */
        private void drawLight()
        {
            final int pointMVPMatrixHandle = GLES20.glGetUniformLocation(lightPointProgramHandle, "u_MVPMatrix"),
                      pointPositionHandle = GLES20.glGetAttribLocation(lightPointProgramHandle, "a_Position");

            // Pass in the point light position.
            GLES20.glVertexAttrib3f(pointPositionHandle, lightPositionModelSpace[0], lightPositionModelSpace[1], lightPositionModelSpace[2]);

            // No buffer object, disable vertex arrays.
            GLES20.glDisableVertexAttribArray(pointPositionHandle);

            // Pass in transformation MVP matrix.
            Matrix.multiplyMM(MVPMatrix, 0, viewMatrix, 0, lightModelMatrix, 0);
            Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, MVPMatrix, 0);
            GLES20.glUniformMatrix4fv(pointMVPMatrixHandle, 1, false, MVPMatrix, 0);

            // draw the point light.
            GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
        }

        /**
         * @param shaderType Shader type.
         * @param shaderSource Shader source code.
         * @return OpenGL handle to shader.
         */
        private int compileShader(final int shaderType, final String shaderSource)
        {
            int shaderHandle = GLES20.glCreateShader(shaderType);

            if (shaderHandle != 0)
            {
                // Pass in shader source.
                GLES20.glShaderSource(shaderHandle, shaderSource);

                // Compile shader.
                GLES20.glCompileShader(shaderHandle);

                // Get compilation status.
                final int[] compileStatus = new int[1];
                GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

                // If compilation failed, delete shader.
                if (compileStatus[0] == 0)
                {
                    Log.e(LOGTAG, "ERROR in compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                    GLES20.glDeleteShader(shaderHandle);
                    shaderHandle = 0;
                }
            }

            if (shaderHandle == 0)
            {
                throw new RuntimeException("ERROR in creating shader.");
            }

            return shaderHandle;
        }

        /**
         * @param vertexShaderHandle OpenGL handle to already-compiled vertex shader.
         * @param fragmentShaderHandle OpenGL handle to already-compiled fragment shader.
         * @param attributes Attributes that need to be bound to the program.
         * @return OpenGL handle to the program.
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

                // Link the two shaders together into a program.
                GLES20.glLinkProgram(programHandle);

                // Get the link status.
                final int[] linkStatus = new int[1];
                GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

                // If the link failed, delete the program.
                if (linkStatus[0] == 0)
                {
                    Log.e(LOGTAG, "ERROR in compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                    GLES20.glDeleteProgram(programHandle);
                    programHandle = 0;
                }
            }

            if (programHandle == 0)
            {
                throw new RuntimeException("ERROR in creating program.");
            }

            return programHandle;
        }

        public float[] getLightPositionModelSpace() {
            return lightPositionModelSpace;
        }

        public void setLightPositionModelSpace(float[] lightPositionModelSpace) {
            this.lightPositionModelSpace = lightPositionModelSpace;
        }

        public void calculateLightPos(float angleInDegrees){
            // Calculate pos of the light. Rotate and then move around the back of the object.
            Matrix.setIdentityM(lightModelMatrix, 0);
            Matrix.translateM(lightModelMatrix, 0, 0.0f, 0.0f, -5.0f);
            Matrix.rotateM(lightModelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
            Matrix.translateM(lightModelMatrix, 0, 0.0f, 0.0f, 2.0f);

            Matrix.multiplyMV(lightPositionWorldSpace, 0, lightModelMatrix, 0, lightPositionModelSpace, 0);
            Matrix.multiplyMV(lightPositionEyeSpace, 0, viewMatrix, 0, lightPositionWorldSpace, 0);
        }
}
