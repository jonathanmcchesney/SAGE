package dreamteam.agile_game_engine.objects;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import dreamteam.agile_game_engine.data.Matrices;
import dreamteam.agile_game_engine.errorHandling.AssetStoreBitmapNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.GameObject;
import dreamteam.agile_game_engine.util.Vector3;
import dreamteam.agile_game_engine.graphics.ShaderTools;

/*
 * Adapted from https://developer.android.com/guide/topics/graphics/opengl.html
 * This class was written by Erin and Aoife
 */
public class Triangle extends GameObject implements Serializable
{
    /*
     * Variable declarations
     */
    private transient FloatBuffer vertexBuffer;
    private static final int COORDS_PER_VERTEX = 3;
    private float[] modelMatrix;

    Vector3 one = new Vector3(0.0f, 0.5f, 0.0f);
    Vector3 two = new Vector3(-0.5f, -0.5f, 0.0f);
    Vector3 three = new Vector3(0.5f, -0.5f, 0.0f);

    float vertices[] = new float[]{
            this.one.getX(), this.one.getY(), this.one.getZ(),
            this.two.getX(), this.two.getY(), this.two.getZ(),
            this.three.getX(), this.three.getY(), this.three.getZ()
    };

    private int imageProgram;

    private int mMVPMatrixHandle;
    private int mPositionHandle;

    private final int vertexCount = vertices.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;


    /**
     * Constructor
     * @param as - AssetStore object to load in the shaders from a text file and the bitmap for the texture
     */
    public Triangle(AssetStore as)
    {
        modelMatrix = new float[16];
        super.modelMatrix = this.modelMatrix;

        as.loadAndAddBitmap("greenstuff","images/greenstuff.jpg");
        as.loadAndAddShader("imageVertexShader","shaders/imageVertexShader.txt");
        as.loadAndAddShader("imageFragmentShader","shaders/imageFragmentShader.txt");

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        setupShaders(as);
    }

    /**
     * This method draws to a final mvpMatrix
     * It is overriden from the super class
     * @param p - this is for the projectionMatrix
     * @param v - this is for the viewMatrix
     * @param mv - this is for the modelViewMatrix
     * @param mvpMatrix - this is for the mvpMatrix
     */
    @Override
    public void draw(float[] p, float[] v, float[] mv, float[] mvpMatrix)
    {
        mPositionHandle = GLES20.glGetAttribLocation(imageProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // Get handle to texture coordinates location
        int mTexCoordLoc = GLES20.glGetAttribLocation(imageProgram, "a_texCoord" );

        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray ( mTexCoordLoc );

        // Prepare the texturecoordinates
        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        Matrices.setUpMVPMatrix(p, v, mv, modelMatrix, mvpMatrix);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(imageProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }

    /**
     * This method sets up the bitmap loaded and binds it to a texture
     * @param as - AssetStore object used to get the bitmap
     */
    public void setupImage(AssetStore as)
    {
        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        try
        {
            Bitmap bmp = as.getBitmap("greenstuff");

            //bind texture to texturename
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //load the bitmap into the bound texture
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        }catch(AssetStoreBitmapNotFoundException e){}
    }

    /**
     * This method loads in ths sahders and attaches them to a program used by OpenGl
     * @param as - AssetStore object gets the shaders
     */
    public void setupShaders(AssetStore as)
    {
        setupImage(as);

        int vertexShader = 0;
        int fragmentShader = 0;

        try
        {
            //Create the shaders, images
            vertexShader = ShaderTools.loadShader(GLES20.GL_VERTEX_SHADER, (as.getShader("imageVertexShader").getShader()));
            fragmentShader = ShaderTools.loadShader(GLES20.GL_FRAGMENT_SHADER, (as.getShader("imageFragmentShader").getShader()));

            imageProgram = GLES20.glCreateProgram();
            GLES20.glAttachShader(imageProgram, vertexShader);
            GLES20.glAttachShader(imageProgram, fragmentShader);
            GLES20.glLinkProgram(imageProgram);

            // Set our shader program
            GLES20.glUseProgram(imageProgram);
        } catch (AssetStoreShaderNotFoundException e) {}
    }

    /**
     * This method writes the triangle object to a file
     * @param out - the output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        //Writes all methods not marked "transient".
        out.defaultWriteObject();
    }

    /**
     * This method reads the triangle object from a file
     * @param in - the input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        //Reads in all variables not marked with "transient".
        in.defaultReadObject();

        //Read
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    /*
     * Getters and setters
     */
    public FloatBuffer getVertexBuffer()
    {
        return vertexBuffer;
    }
    public int getmMVPMatrixHandle(){
        return mMVPMatrixHandle;
    }
    public void setmMVPMatrixHandle(int newMvp){
        mMVPMatrixHandle = newMvp;
    }
    public int getmPositionHandle(){
        return mPositionHandle;
    }
    public void setmPositiionHandle(int newPosition){
        mMVPMatrixHandle = newPosition;
    }

}