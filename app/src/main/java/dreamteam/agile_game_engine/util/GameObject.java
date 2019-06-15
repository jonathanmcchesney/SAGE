package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon.
 */

import android.opengl.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameObject extends BaseGameObject implements Serializable {

    /**
     * Variable Declarations
     */
    protected transient AssetStore as;
    protected float[] modelMatrix;

    private float yMove;
    private float xMove;
    private boolean MovingUp;
    private boolean MovingSide;

    /**
     * GameObject Empty Constructor
     */
    public GameObject() {
    }

    /**
     * GameObject Constructor
     * @param as AssetStore to enable loading of shaders
     */
    public GameObject(AssetStore as) {
        modelMatrix = new float[16];
        this.as = as;
    }

    /**
     * start method to be called upon GameObject Creation
     */
    @Override
    public void start() {
        //Initialization Code
    }

    /**
     * update method to hold update logic
     */
    @Override
    public void update() {
        //update Logic
    }

    /**
     * draw method to contain rendering logic
     * @param p - this is for the projectionMatrix
     * @param v - this is for the viewMatrix
     * @param mvp - this is for the MVPMatrix
     * These are entered into the Matrics.setUpMVPMatrix() for each gameObject
     */
    public void draw(float[] p, float[] v, float[] mv, float[] mvp) {
        //draw Logic
    }

    /**
     * Created by Aoife
     * Translates the GameObject along the Y Axis
     * @param ModelMatrix
     */
    public void translateAlongY(float[] ModelMatrix){
        if (MovingUp) // If we are moving up
            yMove -= 0.005f; // Move up along our yLocation
        else  // Otherwise
            yMove += 0.005f; // Move down along our yLocation

        if (yMove < -3.0f) // If we have gone up too far
            MovingUp = false; // Reverse our direction so we are moving down
        else if (yMove > 3.0f) // Else if we have gone down too far
            MovingUp = true; // Reverse our direction so we are moving up

        Matrix.translateM(ModelMatrix, 0, 0.0f, yMove, 0.0f);
    }

    /**
     * Created by Aoife
     * Translates the GameObject along the X Axis
     * @param ModelMatrix
     */
    public void translateAlongX(float[] ModelMatrix){
        if (MovingSide) // If we are moving up
            xMove -= 0.005f; // Move up along our yLocation
        else  // Otherwise
            xMove += 0.005f; // Move down along our yLocation

        if (xMove < -3.0f) // If we have gone up too far
            MovingSide = false; // Reverse our direction so we are moving down
        else if (xMove > 3.0f) // Else if we have gone down too far
            MovingSide = true; // Reverse our direction so we are moving up

        Matrix.translateM(ModelMatrix, 0, xMove, 0.0f, 0.0f);
    }

    /**
     * Created by Aoife
     * Translates the GameObject in accordance with a touch event
     * @param ModelMatrix
     * @param moveX - The amount to translate this GameObject by along the X Axis.
     * @param moveY - The amount to translate this GameObject by along the Y AXis.
     */
    public void translateByTouch(float[] ModelMatrix, float moveX, float moveY){
        xMove = moveX;
        yMove = moveY;
        Matrix.translateM(ModelMatrix, 0, xMove, yMove, 0.0f);
    }

    /**
     * Scales the GameObject by a Scale Factor
     * @param ModelMatrix
     * @param scale - The scale factor
     */
    public void scale(float[] ModelMatrix, float scale){
        Matrix.scaleM(ModelMatrix, 0, scale, scale, scale);
    }

    /**
     * Created by Aoife
     * Rotates the GameObject by an angle along an axis
     * @param ModelMatrix
     * @param angle
     * @param x - Specifies a rotation around the x axis
     * @param y - Specifies a rotation around the y axis
     * @param z - Specifies a rotation around the z axis
     */
    public void rotate(float[] ModelMatrix, float angle, float x, float y, float z){
        Matrix.rotateM(ModelMatrix, 0, angle, x, y, z);
    }

    /**
     * Created by Aoife
     * Method to Translate the GameObject
     * @param ModelMatrix
     * @param x - translates along the x axis (a negative float moves the gameObject to the left of the x axis,
     *          and positive float moves the gameObject to the right of the x axis)
     * @param y - translates along the y axis (a negative float moves the gameObject down the y axis,
     *          and a positive float moves the gameObject up the y axis)
     * @param z - translates along the z axis (a negative float moves the gameObject towards the camera,
     *          and positive float moves the gameObject away from the camera)
     */
    public void translate(float[] ModelMatrix, float x, float y, float z){
        Matrix.translateM(ModelMatrix, 0, x, y, z);
    }

    /**
     * Serialization Method to Write an Object
     * @param out - The Object Output Stream to write with
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        //Writes all methods not marked "transient".
        out.defaultWriteObject();
    }


    /**
     * Serialization Method to Read an Object
     * @param in - The Object Input Stream to read with
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //Reads in all variables not marked with "transient".
        in.defaultReadObject();
    }

    /*
    Getter and Setter Methods
     */

    public float[] getModelMatrix(){
        return modelMatrix;
    }

    public float getXMove()
    {
        return xMove;
    }

    public void setXMove(float newXMove)
    {
        this.xMove = newXMove;
    }

    public float getYMove()
    {
        return yMove;
    }

    public void setYMove(float newYMove)
    {
        this.yMove = newYMove;
    }

    public boolean getMovingUp()
    {
        return MovingUp;
    }

    public void setMovingUp(boolean newMovingUp)
    {
        this.MovingUp = newMovingUp;
    }

    public boolean getMovingSide()
    {
        return MovingSide;
    }

    public void setMovingSide(boolean newMovingSide)
    {
        this.MovingSide = newMovingSide;
    }

}