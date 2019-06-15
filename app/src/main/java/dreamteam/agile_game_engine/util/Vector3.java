package dreamteam.agile_game_engine.util;

import java.io.Serializable;

/*
 * This class was Created by Simon
 */

public class Vector3 implements Serializable
{
    /*
    * Variable Declaration
     */
    private float x;
    private float y;
    private float z;

    /**
     * Constructor Method
     * @param x - X Value
     * @param y - Y Value
     * @param z - Z Value
     */
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * This method normalizes the current Vector3
     */
    public void normalize()
    {
        double magnitude = this.magnitude();
        this.x = x / (float)magnitude;
        this.y = y / (float)magnitude;
        this.z = z / (float)magnitude;
    }

    /**
     *Returns the normalized Vector3 value without editing the original Vector
     * @return a normalized Instance of this Vector3
     */
    public Vector3 normalized()
    {
        double magnitude = this.magnitude();

        float normalizeX = x / (float)magnitude;
        float normalizeY = y / (float)magnitude;
        float normalizeZ = z / (float)magnitude;

        return new Vector3(normalizeX, normalizeY, normalizeZ);
    }

    /**
     * Gets the Magnitude of the Vector3
     * @return Returns the Magnitude of this Vector3
     */
    public double magnitude()
    {
        return Math.sqrt((x*x) + (y * y) + (z * z));
    }

    /**
     * Gets the Magnitude squared of this Vector
     * @return Returns the Squared Magnitude of this Vector
     */
    public double sqrMagnitude()
    {
        return ((x*x) + (y*y) + (z*z));
    }


    /**
     * A shorthand way of writing new Vector3(0,0,0)
     * @return Returns a new Vector3(0,0,0)
     */
    public static Vector3 zero()
    {
        return new Vector3(0,0,0);
    }

    /**
     * A shorthand way of writing new Vector3(1,0,0)
     * @return Returns a new Vector3(1,0,0)
     */
    public static Vector3 right()
    {
        return new Vector3(1,0,0);
    }

    /**
     * A shorthand way of writing new Vector3(0,1,0)
     * @return Returns a new Vector3(0,1,0)
     */
    public static Vector3 up()
    {
        return new Vector3(0,1,0);
    }

    /**
     * A shorthand way of writing new Vector3(0,0,1)
     * @return Returns a new Vector3(0,0,1)
     */
    public static Vector3 forward()
    {
        return new Vector3(0,0,1);
    }

    /**
     * Turns this Vector3 into a float array
     * @return A Float[] representation of this Vector3
     */
    public float[] toVector3Array()
    {
        float[] floatArray = new float[3];

        floatArray[0] = this.x;
        floatArray[1] = this.y;
        floatArray[2] = this.z;

        return floatArray;
    }

    /**
     * Promotes this Vector3 to a Vector4 and represents it as a float[]
     * @return Returns a float[] representation of this Vector3 promoted to a Vector4
     */
    public float[] toVector4Array()
    {
        float[] floatArray = new float[4];

        floatArray[0] = this.x;
        floatArray[1] = this.y;
        floatArray[2] = this.z;
        floatArray[3] = 1;

        return floatArray;
    }

    /**
     * Adds two Vector3s together
     * @param vector4ToAdd - The Vector3 to add to the current method.
     * @return Returns the resultant Vector3
     */
    public Vector3 add(Vector3 vector4ToAdd)
    {
        return new Vector3(this.x + vector4ToAdd.getX(),
                this.y + vector4ToAdd.getY(),
                this.z + vector4ToAdd.getZ());
    }

    /**
     * Subtracts one Vector3 from another
     * @param vector3ToSubtract The Vector3 to subtract from the current Vector
     * @return
     */
    public Vector3 subtract(Vector3 vector3ToSubtract)
    {
        return new Vector3(this.x - vector3ToSubtract.getX(),
                this.y - vector3ToSubtract.getY(),
                this.z - vector3ToSubtract.getZ());
    }

    /**
     * Promotes this Vector3 to a Vector4 with it's W = 0
      * @return Returns this Vector3 represented as a Vector4
     */
    public Vector4 toVector4()
    {
        return new Vector4(x,y,z,0);
    }


    /*
    * Getter and Setter Methods
     */

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public float getZ()
    {
        return this.z;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setZ(float z)
    {
        this.z = z;
    }


}