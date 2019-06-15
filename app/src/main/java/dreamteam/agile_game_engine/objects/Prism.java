package dreamteam.agile_game_engine.objects;

import android.opengl.GLES20;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import dreamteam.agile_game_engine.data.Matrices;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.util.AssetStore;
import dreamteam.agile_game_engine.util.GameObject;
import dreamteam.agile_game_engine.util.Vector3;
import dreamteam.agile_game_engine.graphics.ShaderTools;

/*
 * This class was created by Aoife
 * Extends GameObject and implements Serializable
 */
public class Prism extends GameObject implements Serializable
{
    /*
     * Variable declarations
     */
    private transient FloatBuffer vertexBuffer; //Buffer for vertex-array
    private transient FloatBuffer colorBuffer;

    private int colorHandle;
    private int mvpMatrixHandle;
    private int positionHandle;

    private int program;

    protected float[] modelMatrix;

    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_COLOR = 4;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private final int colorStride = COORDS_PER_COLOR * 4;

    //Front-face
    private Vector3 one = new Vector3( -1.0f, -1.0f, 1.0f); //Front-left
    private Vector3 two = new Vector3( 1.0f, -1.0f, 1.0f); //Front-right
    private Vector3 three = new Vector3(0.0f, 1.0f, 0.0f); //Top

    //Left-face
    private Vector3 four = new Vector3(-1.0f, -1.0f, 1.0f); //Front-left
    private Vector3 five = new Vector3(-1.0f, -1.0f, -1.0f); //Back-left
    private Vector3 six = new Vector3(0.0f, 1.0f, 0.0f); //Top

    //Back-face
    private Vector3 seven = new Vector3(-1.0f, -1.0f, -1.0f); //Back-left
    private Vector3 eight = new Vector3(1.0f, -1.0f, -1.0f); //Back-right
    private Vector3 nine = new Vector3(0.0f, 1.0f, 0.0f); //Top

    //Right-face
    private Vector3 ten = new Vector3(1.0f, -1.0f, 1.0f); //Front-right
    private Vector3 eleven = new Vector3(1.0f, -1.0f, -1.0f); //Back-right
    private Vector3 twelve = new Vector3(0.0f, 1.0f, 0.0f); //Top

    //Bottom square
    private Vector3 thirteen = new Vector3(-1.0f,-1.0f, 1.0f); //Front-left
    private Vector3 fourteen = new Vector3(1.0f, -1.0f, 1.0f); //Front-right
    private Vector3 fifteen = new Vector3(-1.0f, -1.0f, -1.0f); //Back-left
    private Vector3 sixteen = new Vector3(1.0f, -1.0f, 1.0f); //Front-right
    private Vector3 seventeen = new Vector3(1.0f, -1.0f, -1.0f); //Back-right
    private Vector3 eighteen =new Vector3(-1.0f, -1.0f, -1.0f); //Back-left

    private float vertices[] =
            {
            one.getX(), one.getY(), one.getZ(),
            two.getX(), two.getY(), two.getZ(),
            three.getX(), three.getY(), three.getZ(),

            four.getX(), four.getY(), four.getZ(),
            five.getX(), five.getY(), five.getZ(),
            six.getX(), six.getY(), six.getZ(),

            seven.getX(), seven.getY(), seven.getZ(),
            eight.getX(), eight.getY(), eight.getZ(),
            nine.getX(), nine.getY(), nine.getZ(),

            ten.getX(), ten.getY(), ten.getZ(),
            eleven.getX(), eleven.getY(), eleven.getZ(),
            twelve.getX(), twelve.getY(), twelve.getZ(),

            thirteen.getX(), thirteen.getY(), thirteen.getZ(),
            fourteen.getX(), fourteen.getY(), fourteen.getZ(),
            fifteen.getX(), fifteen.getY(), fifteen.getZ(),
            sixteen.getX(), sixteen.getY(), sixteen.getZ(),
            seventeen.getX(), seventeen.getY(), seventeen.getZ(),
            eighteen.getX(), eighteen.getY(), eighteen.getZ(),
    };

    private float colors[] =
            {

                    0.0f, 1.0f, 0.0f, 1.0f, //green
                    0.0f, 0.0f, 1.0f, 1.0f, //blue
                    1.0f, 0.0f, 1.0f, 1.0f, //Top vertex color magneta

                    0.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 0.0f, 1.0f, 1.0f,

                    1.0f, 1.0f, 0.0f, 1.0f,
                    0.0f, 1.0f, 1.0f, 1.0f,
                    1.0f, 0.0f, 1.0f, 1.0f,

                    0.0f, 0.0f, 1.0f, 1.0f,
                    0.0f, 1.0f, 1.0f, 1.0f,
                    1.0f, 0.0f, 1.0f, 1.0f,

                    0.0f, 1.0f, 00f, 1.0f,
                    0.0f, 0.0f, 1.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f, 1.0f,
                    0.0f, 1.0f, 1.0f, 1.0f,
                    1.0f, 1.0f, 0.0f, 1.0f,
            };

    /**
     * Constructor
     * @param as - AssetStore object which loads in the shaders from a text file
     */
    public Prism(AssetStore as)
    {
        modelMatrix = new float[16];
        super.modelMatrix = this.modelMatrix;

        as.loadAndAddShader("prismVertexShader","shaders/prismVertexShader.txt");
        as.loadAndAddShader("prismFragmentShader","shaders/prismFragmentShader.txt");

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

        int vertexShader = 0;
        int fragmentShader = 0;
        try {
            vertexShader = ShaderTools.loadShader(GLES20.GL_VERTEX_SHADER, (as.getShader("prismVertexShader").getShader()));
            fragmentShader = ShaderTools.loadShader(GLES20.GL_FRAGMENT_SHADER, (as.getShader("prismFragmentShader").getShader()));
        } catch (AssetStoreShaderNotFoundException e) {
        }

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
    }

    /**
     * This method draws the object to a mvpMatrix
     * It is overriden from the gameObject class
     * @param p - this is for the projectionMatrix
     * @param v - this is for the viewMatrix
     * @param mv - the modelViewMatrix
     * @param mvpMatrix - the mvpMatrix
     */
    @Override
    public void draw(float[] p, float[] v, float[] mv, float[] mvpMatrix){
        GLES20.glUseProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetAttribLocation(program, "vColor");
        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        Matrices.setUpMVPMatrix(p, v, mv, modelMatrix, mvpMatrix);

        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        GLES20.glDrawArrays(GL10.GL_TRIANGLES, 0, 18);
        //  GLES20.glDrawElements(GL10.GL_LINES, vertices.length, GL10.GL_UNSIGNED_BYTE, IndexBuffer);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
    }

    /**
     * This method writes the prism object to a file
     * @param out - the output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        //Writes all methods not marked "transient".
        out.defaultWriteObject();
    }
    /**
     * This method reads the prism object from a file
     * @param in - the input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        //Reads in all variables not marked with "transient".
        in.defaultReadObject();

        //Prism Specific De-Serialization
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
}
