package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon
 */

import java.io.Serializable;

public class Vector4 implements Serializable {

    /**
     * Variable Declarations
     */

    private float x;
    private float y;
    private float z;
    private float w;

    /**
     * Constructor Methods
     * @param x - The X Value
     * @param y - The Y Value
     * @param z - The Z Value
     * @param w - The W Value
     */
    public Vector4(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


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

    public float getW() { return this.w;}

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

    public void setW(float w) { this.w = w;}

    /**
     * This method normalizes the current Vector3
     */
    public void normalize()
    {
        double magnitude = this.magnitude();
        this.x = x / (float)magnitude;
        this.y = y / (float)magnitude;
        this.z = z / (float)magnitude;
        this.w = w / (float)magnitude;
    }

    /**
     *Returns the normalized Vector4 value without editing the original Vector
     * @return a normalized Instance of this Vector4
     */
    public Vector4 normalized()
    {
        double magnitude = this.magnitude();

        float normalizeX = x / (float)magnitude;
        float normalizeY = y / (float)magnitude;
        float normalizeZ = z / (float)magnitude;
        float normalizeW = w / (float)magnitude;

        return new Vector4(normalizeX, normalizeY, normalizeZ, normalizeW);
    }

    /**
     * Gets the Magnitude of the Vector4
     * @return Returns the Magnitude of this Vector4
     */
    public double magnitude()
    {
        return Math.sqrt((x*x) + (y * y) + (z * z) + (w * w));
    }

    /**
     * Gets the Magnitude squared of this Vector4
     * @return Returns the Squared Magnitude of this Vector4
     */
    public double sqrMagnitude()
    {
        return ((x*x) + (y*y) + (z*z) + (w * w));
    }

    /**
     * A shorthand way of writing new Vector4(0,0,0,0)
     * @return Returns a new Vector4(0,0,0,0)
     */
    public static Vector4 zero()
    {
        return new Vector4(0,0,0,0);
    }

    /**
     * A shorthand way of writing new Vector4(1,0,0,0)
     * @return Returns a new Vector4(1,0,0,0)
     */
    public static Vector4 right()
    {
        return new Vector4(1,0,0,0);
    }

    /**
     * A shorthand way of writing new Vector4(0,1,0,0)
     * @return Returns a new Vector4(0,1,0,0)
     */
    public static Vector4 up()
    {
        return new Vector4(0,1,0,0);
    }

    /**
     * A shorthand way of writing new Vector4(0,0,1,0)
     * @return Returns a new Vector4(0,0,1,0)
     */
    public static Vector4 forward()
    {
        return new Vector4(0,0,1,0);
    }

    /**
     * A shorthand way of writing new Vector4(0,0,0,1)
     * @return Returns a new Vector4(0,0,0,1)
     */
    public static Vector4 alpha()
    {
        return new Vector4(0,0,0,1);
    }


    /**
     * Adds two Vector4s together
     * @param vector4ToAdd - The Vector4 to add to the current method.
     * @return Returns the resultant Vector4
     */
    public Vector4 add(Vector4 vector4ToAdd)
    {
        return new Vector4(this.x + vector4ToAdd.getX(),
                this.y + vector4ToAdd.getY(),
                this.z + vector4ToAdd.getZ(),
                this.w + vector4ToAdd.getW());
    }

    /**
     * Subtracts one Vector4 from another
     * @param vector4ToSubtract The Vector4 to subtract from the current Vector
     * @return
     */
    public Vector4 subtract(Vector4 vector4ToSubtract)
    {
        return new Vector4(this.x - vector4ToSubtract.getX(),
                this.y - vector4ToSubtract.getY(),
                this.z - vector4ToSubtract.getZ(),
                this.w - vector4ToSubtract.getW());
    }

}