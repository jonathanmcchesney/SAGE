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

import javax.microedition.khronos.opengles.GL10;

import dreamteam.agile_game_engine.data.Matrices;
import dreamteam.agile_game_engine.errorHandling.AssetStoreBitmapNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.graphics.ShaderTools;
import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.GameObject;
import dreamteam.agile_game_engine.util.Vector3;

/*
 * This class was created by Aoife
 * Extends GameObject and implements Serializable
 */

public class Cube extends GameObject implements Serializable
{
    /*
     * Variable declarations
     */
    private transient FloatBuffer vertexBuffer;
    private transient FloatBuffer colorBuffer;
    private transient FloatBuffer textureBuffer;

    private int colorHandle;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int textureHandle;

    private int program;

    private String[] attributes;

    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_COLOR = 4;
    private static final int COORDS_PER_TEXTURE = 2;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private final int colorStride = COORDS_PER_COLOR * 4;

    private float[] modelMatrix;

    //FRONT FACE
    private  Vector3 one = new Vector3( -1.0f, 1.0f, 1.0f);
    private  Vector3 two = new Vector3( -1.0f, -1.0f, 1.0f);
    private Vector3 three = new Vector3(1.0f, 1.0f, 1.0f);
    private Vector3 four = new Vector3(-1.0f, -1.0f, 1.0f);
    private Vector3 five = new Vector3(1.0f, -1.0f, 1.0f);
    private Vector3 six = new Vector3(1.0f, 1.0f, 1.0f);

    //RIGHT FACE
    private  Vector3 seven = new Vector3(1.0f, 1.0f, 1.0f);
    private Vector3 eight = new Vector3(1.0f, -1.0f, 1.0f);
    private Vector3 nine = new Vector3(1.0f, 1.0f, -1.0f);
    private Vector3 ten = new Vector3(1.0f, -1.0f, 1.0f);
    private Vector3 eleven = new Vector3(1.0f, -1.0f, -1.0f);
    private Vector3 twelve = new Vector3(1.0f, 1.0f, -1.0f);

    //BACK FACE
    private Vector3 thirteen = new Vector3(1.0f, 1.0f, -1.0f);
    private Vector3 fourteen = new Vector3(1.0f, -1.0f, -1.0f);
    private Vector3 fifteen = new Vector3(-1.0f, 1.0f, -1.0f);
    private Vector3 sixteen = new Vector3(1.0f, -1.0f, -1.0f);
    private Vector3 seventeen = new Vector3(-1.0f, -1.0f, -1.0f);
    private Vector3 eighteen =new Vector3(-1.0f, 1.0f, -1.0f);

    //LEFT FACE
    private Vector3 nineteen =new Vector3(-1.0f, 1.0f, -1.0f);
    private Vector3 twenty = new Vector3(-1.0f, -1.0f, -1.0f);
    private Vector3 twentyOne =new Vector3( -1.0f, 1.0f, 1.0f);
    private Vector3 twentyTwo =new Vector3( -1.0f, -1.0f, -1.0f);
    private Vector3 twentyThree =new Vector3( -1.0f, -1.0f, 1.0f);
    private Vector3 twentyFour = new Vector3( -1.0f, 1.0f, 1.0f);

    //TOP FACE
    private Vector3 twentyFive = new Vector3(-1.0f, 1.0f, -1.0f);
    private Vector3 twentySix = new Vector3(-1.0f, 1.0f, 1.0f);
    private Vector3 twentySeven = new Vector3(1.0f, 1.0f, -1.0f);
    private Vector3 twentyEight = new Vector3(-1.0f, 1.0f, 1.0f);
    private Vector3 twentyNine = new Vector3(1.0f, 1.0f, 1.0f);
    private Vector3 thirty = new Vector3(1.0f, 1.0f, -1.0f);

    //BOTTOM FACE
    private Vector3 thirtyOne = new Vector3(1.0f, -1.0f, -1.0f);
    private Vector3 thirtyTwo = new Vector3(1.0f, -1.0f, 1.0f);
    private Vector3 thirtyThree = new Vector3(-1.0f, -1.0f, -1.0f);
    private Vector3 thirtyFour = new Vector3(1.0f, -1.0f, 1.0f);
    private Vector3 thirtyFive = new Vector3(-1.0f, -1.0f, 1.0f);
    private Vector3 thirtySix = new Vector3(-1.0f, -1.0f, -1.0f);

    private float[] vertices = {

            //FRONT FACE
            one.getX(), one.getY(), one.getZ(),
            two.getX(), two.getY(), two.getZ(),
            three.getX(), three.getY(), three.getZ(),
            four.getX(), four.getY(), four.getZ(),
            five.getX(), five.getY(), five.getZ(),
            six.getX(), six.getY(), six.getZ(),

            //RIGHT FACE
            seven.getX(), seven.getY(), seven.getZ(),
            eight.getX(), eight.getY(), eight.getZ(),
            nine.getX(), nine.getY(), nine.getZ(),
            ten.getX(), ten.getY(), ten.getZ(),
            eleven.getX(), eleven.getY(), eleven.getZ(),
            twelve.getX(), twelve.getY(), twelve.getZ(),

            //BACK FACE
            thirteen.getX(), thirteen.getY(), thirteen.getZ(),
            fourteen.getX(), fourteen.getY(), fourteen.getZ(),
            fifteen.getX(), fifteen.getY(), fifteen.getZ(),
            sixteen.getX(), sixteen.getY(), sixteen.getZ(),
            seventeen.getX(), seventeen.getY(), seventeen.getZ(),
            eighteen.getX(), eighteen.getY(), eighteen.getZ(),

            //LEFT FACE
            nineteen.getX(), nineteen.getY(), nineteen.getZ(),
            twenty.getX(), twenty.getY(), twenty.getZ(),
            twentyOne.getX(), twentyOne.getY(), twentyOne.getZ(),
            twentyTwo.getX(), twentyTwo.getY(), twentyTwo.getZ(),
            twentyThree.getX(), twentyThree.getY(), twentyThree.getZ(),
            twentyFour.getX(), twentyFour.getY(), twentyFour.getZ(),

            //TOP FACE
            twentyFive.getX(), twentyFive.getY(), twentyFive.getZ(),
            twentySix.getX(), twentySix.getY(), twentySix.getZ(),
            twentySeven.getX(), twentySeven.getY(), twentySeven.getZ(),
            twentyEight.getX(), twentyEight.getY(), twentyEight.getZ(),
            twentyNine.getX(), twentyNine.getY(), twentyNine.getZ(),
            thirty.getX(), thirty.getY(), thirty.getZ(),

            //BOTTOM FACE
            thirtyOne.getX(), thirtyOne.getY(), thirtyOne.getZ(),
            thirtyTwo.getX(), thirtyTwo.getY(), thirtyTwo.getZ(),
            thirtyThree.getX(), thirtyThree.getY(), thirtyThree.getZ(),
            thirtyFour.getX(), thirtyFour.getY(), thirtyFour.getZ(),
            thirtyFive.getX(), thirtyFive.getY(), thirtyFive.getZ(),
            thirtySix.getX(), thirtySix.getY(), thirtySix.getZ(),

    };

    private float colors[] = {
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

    private float textures[] = {
            //front face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,


            // Right face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            // Back face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            // Left face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            // Top face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,

            // Bottom face
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };

    /**
     * Constructor
     * @param as - AssetStore object loads in the shaders from a text file, and a bitmap for the texture
     */
    public Cube(AssetStore as)
    {
        modelMatrix = new float[16];
        super.modelMatrix = this.modelMatrix;

        as.loadAndAddBitmap("philAndMuffin","images/philAndMuffin.png");
        as.loadAndAddShader("cubeVertexShader","shaders/cubeVertexShader.txt");
        as.loadAndAddShader("cubeFragmentShader","shaders/cubeFragmentShader.txt");

        ByteBuffer vertex = ByteBuffer.allocateDirect(vertices.length * 4);
        vertex.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vertex.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        ByteBuffer color = ByteBuffer.allocateDirect(colors.length*4);
        color.order(ByteOrder.nativeOrder());
        colorBuffer = color.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        ByteBuffer texture = ByteBuffer.allocateDirect(textures.length * 4);
        texture.order(ByteOrder.nativeOrder());
        textureBuffer = texture.asFloatBuffer();
        textureBuffer.put(textures);
        textureBuffer.position(0);

        setUpShaders(as);
    }

    /**
     * This method overrides from the gameobject class and draws the cube to a specified MVPMatrix
     * @param p - this is for the projectionMatrix
     * @param v - this is for the viewMatrix
     * @param mv - this is the modelViewMatrix
     * @param mvpMatrix - the mvpMatrix
     */
    @Override
    public void draw(float[] p, float[] v, float[] mv, float[] mvpMatrix)
    {
        GLES20.glUseProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, attributes[0]);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetAttribLocation(program, attributes[1]);
        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        textureHandle = GLES20.glGetAttribLocation(program, attributes[3]);
        GLES20.glEnableVertexAttribArray(textureHandle);
        GLES20.glVertexAttribPointer(textureHandle, COORDS_PER_TEXTURE, GLES20.GL_FLOAT, false, 0, textureBuffer);

        Matrices.setUpMVPMatrix(p, v, mv, modelMatrix, mvpMatrix);
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, attributes[2]);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        GLES20.glDrawArrays(GL10.GL_TRIANGLES, 0, 36);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
        GLES20.glDisableVertexAttribArray(textureHandle);
    }

    /**
     * This method sets up the shaders and binds them to a opengl program
     * The method setUpTexture(As), also uses the following parameter:
     * @param As - The AssetStore object which loads in the shaders from a text file
     */
    public void setUpShaders(AssetStore As)
    {
        setUpTexture(As);

        int vertexShader = 0;
        int fragmentShader = 0;
        try {
            vertexShader = ShaderTools.loadShader(GLES20.GL_VERTEX_SHADER, (As.getShader("cubeVertexShader").getShader()));
            fragmentShader = ShaderTools.loadShader(GLES20.GL_FRAGMENT_SHADER, (As.getShader("cubeFragmentShader").getShader()));
        } catch (AssetStoreShaderNotFoundException e) {

        }

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        //This for loop sets up all the attributes found in the shaders into an array and binds them to the program
        attributes = new String[]{"vPosition", "vColor", "uMVPMatrix", "a_texCoord"};

        if (attributes != null)
        {
            final int size = attributes.length;
            for (int i = 0; i < size; i++)
            {
                GLES20.glBindAttribLocation(program, i, attributes[i]);
            }
        }
        GLES20.glLinkProgram(program);
    }

    /**
     * This method sets up the texture and binds it
     * @param as - AssetStore object which loads in the bitmap to be binded
     */
    public void setUpTexture(AssetStore as)
    {
        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        try {
            Bitmap bmp = as.getBitmap("philAndMuffin");

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
     * This method writes the cube object to a file
     * @param out - the output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        //Writes all methods not marked "transient".
        out.defaultWriteObject();
    }

    /**
     * This method reads the cube object from a file
     * @param in - the input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        //Reads in all variables not marked with "transient".
        in.defaultReadObject();

        //Read
        ByteBuffer vertex = ByteBuffer.allocateDirect(vertices.length * 4);
        vertex.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vertex.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        ByteBuffer color = ByteBuffer.allocateDirect(colors.length*4);
        color.order(ByteOrder.nativeOrder());
        colorBuffer = color.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        ByteBuffer texture = ByteBuffer.allocateDirect(textures.length * 4);
        texture.order(ByteOrder.nativeOrder());
        textureBuffer = texture.asFloatBuffer();
        textureBuffer.put(textures);
        textureBuffer.position(0);
    }
    /*
     * Getters and setters
     */
    public FloatBuffer getVertexBuffer()
    {
        return vertexBuffer;
    }
    public FloatBuffer getColorBuffer()
    {
        return colorBuffer;
    }
    public FloatBuffer getTextureBuffer()
    {
        return textureBuffer;
    }
    public int getColorHandle()
    {
        return colorHandle;
    }
    public void setColorHandle(int x)
    {
        colorHandle = x;
    }
    public int getPositionHandle()
    {
        return positionHandle;
    }
    public void setPositionHandle(int x){
        positionHandle = x;
    }
    public int getTextureHandle()
    {
        return textureHandle;
    }
    public void setTextureHandle(int x)
    {
        textureHandle = x;
    }
    public String getAttribute(int i)
    {
        return attributes[i];
    }
    public void setAttribute(int i, String attribute)
    {
        attributes[i] = attribute;
    }
}