package dreamteam.agile_game_engine.objects;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import dreamteam.agile_game_engine.errorHandling.AssetStoreBitmapNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.graphics.ShaderTools;
import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.GameObject;

/*
 * This class was created by Shane
 */
public class Billboard
{

    /*
     * Variable declarations
     */
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private final FloatBuffer billboardTextureCoordinates;

    private final float[] MVPMatrix = new float[16];
    private final float[] ModelMatrix = new float[16];
    private final float[] MVMatrix = new float[16];

    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;
    private int mTextureDataHandle;

    private static float squareCoords[] = {
            -0.5f, 0.5f, -3.0f,   // top left
            -0.5f, -0.5f, -3.0f,// bottom left
            0.5f, -0.5f, -3.0f, // bottom right
            0.5f, 0.5f, -3.0f //top right
    }; // top right

    private final float[] billboardTextureCoordinateData =
            {
                    // Front face
                    1.0f, 0.0f,
                    0.0f, 0.0f,
                    1.0f, 1.0f,
                    0.0f, 1.0f
            };
    private float color[] = {1f, 1f, 1f, 1.0f};

    private static final int COORDS_PER_VERTEX = 3;
    private static final int vertexStride = COORDS_PER_VERTEX * 4;
    private static final int mBytesPerFloat = 4;
    private final int mTextureCoordinateDataSize = 2;

    //AssetStore planned to be integrated for shaders
    private final String vertexShaderCode =
            "attribute vec2 a_TexCoordinate;" +
                    "varying vec2 v_TexCoordinate;" +
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "v_TexCoordinate = a_TexCoordinate;" +
                    "}";
    private final String fragmentShaderCode =
            "uniform sampler2D u_Texture; " +
                    "varying vec2 v_TexCoordinate;" +
                    "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "gl_FragColor = (vColor * texture2D(u_Texture, v_TexCoordinate));" +
                    "}";

    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

    /**
     * Constructor
     * @param as - AssetStore object to load in textures
     */
    public Billboard(AssetStore as) {

        as.loadAndAddBitmap("brick", "images/brick.jpg");

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        ByteBuffer texture = ByteBuffer.allocateDirect(billboardTextureCoordinateData.length * 4);
        texture.order(ByteOrder.nativeOrder());
        FloatBuffer textureBuffer = texture.asFloatBuffer();
        textureBuffer.put(billboardTextureCoordinateData);
        textureBuffer.position(0);

        billboardTextureCoordinates = ByteBuffer.allocateDirect(billboardTextureCoordinateData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        billboardTextureCoordinates.put(billboardTextureCoordinateData).position(0);

       setupShaders(as);
    }

    /**
     * This sets up the shaders and link them to a program.
     * It also sets up the TextureImage with the method setupImage()
     * @param as - AssetStore object to load in shaders.
     */
    public void setupShaders(AssetStore as){
        mTextureDataHandle = setupImage(as);
        int vertexShader = 0;
        int fragmentShader = 0;
        try {
            //Create the shaders, images
            vertexShader = ShaderTools.loadShader(GLES20.GL_VERTEX_SHADER, (vertexShaderCode));
            fragmentShader = ShaderTools.loadShader(GLES20.GL_FRAGMENT_SHADER, (fragmentShaderCode));

            mProgram = GLES20.glCreateProgram();
            GLES20.glAttachShader(mProgram, vertexShader);
            GLES20.glAttachShader(mProgram, fragmentShader);
            GLES20.glLinkProgram(mProgram);

            // Set our shader program
            GLES20.glUseProgram(mProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets up a loaded in bitmap as the texture and binds it
     * @param as - AssetStore object loads in the bitmap
     * @return - the texture to be used
     */
    public int setupImage(AssetStore as) {
        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        try {
            Bitmap bmp = as.getBitmap("brick");

            //bind texture to texturename
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //load the bitmap into the bound texture
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);


        } catch (AssetStoreBitmapNotFoundException ignored) {
            ignored.printStackTrace();
        }
        return texturenames[0];
    }

    /**
     *This method draws the billboard to the screen
     * @param viewMatrix - the viewMatrix
     * @param projectionMatrix - the projectionMatrix
     */
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        Matrix.setIdentityM(ModelMatrix, 0);
        Matrix.translateM(ModelMatrix, 0, 0, 0, 2f);
        Matrix.multiplyMM(MVMatrix, 0, viewMatrix, 0, ModelMatrix, 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    MVMatrix[i * 4 + j] = 1.0f;
                } else {
                    MVMatrix[i * 4 + j] = 0.0f;
                }
            }
        }
        drawBillboard(projectionMatrix);
    }

    /**
     * This method draws the billboard and is called in the draw() method
     * @param projectionMatrix
     */
    private void drawBillboard(float[] projectionMatrix) {

        Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, MVMatrix, 0);

        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");

        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
        billboardTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false,
                0, billboardTextureCoordinates);

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MVPMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }
}
