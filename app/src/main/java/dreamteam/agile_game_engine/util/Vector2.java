package dreamteam.agile_game_engine.util;

/*
 * This class was created by Simon
 */

import java.io.Serializable;

public class Vector2 implements Serializable {

    /*
    * Variable Declaration
     */
    private float x,y;

    /**
     * Constructor Method
     * @param x - The X Value
     * @param y - The Y Value
     */
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * This method normalizes the Vector 2
     */
    public void normalize()
    {
        double magnitude = this.magnitude();
        this.x = x / (float)magnitude;
        this.y = y / (float)magnitude;
    }

    /**
     * This method returns a normalized Vector2 without changing the original Vector2.
     * @return A normalized instance of this Vector2
     */
    public Vector2 normalized()
    {
        double magnitude = this.magnitude();

        float normalizeX = x / (float)magnitude;
        float normalizeY = y / (float)magnitude;

        return new Vector2(normalizeX, normalizeY);
    }

    /**
     * Gets the magnitude of the Vector2
     * @return the Vector's Magnitude
     */
    public double magnitude()
    {
        return Math.sqrt((x*x) + (y * y));
    }

    /**
     * Gets the magnitude of the Vector2 without squaring it.
     * @return Returns the Squared Magnitude of this Vector2
     */
    public double sqrMagnitude()
    {
        return ((x*x) + (y*y));
    }

    /**
     * Shorthand way of writing new Vector2(0,0)
     * @return A new Vector2 of (0,0)
     */
    public static Vector2 zero()
    {
        return new Vector2(0,0);
    }

    /**
     * Shorthand way of writing new Vector2(1,0)
     * @return A new Vector2 of (1,0)
     */
    public static Vector2 right()
    {
        return new Vector2(1,0);
    }

    /**
     * Shorthand way of writing new Vector2(0,1)
     * @return A new Vector2 of (0,1)
     */
    public static Vector2 up()
    {
        return new Vector2(0,1);
    }

    /**
     * Adds two Vector2s together
     * @param vector2ToAdd the Vector2 to add to the current Vector
     * @return the resultant Vector2
     */
    public Vector2 add(Vector2 vector2ToAdd)
    {
        return new Vector2(this.x + vector2ToAdd.getX(),
                this.y + vector2ToAdd.getY());
    }

    /**
     * Subtracts one Vector2 from another
     * @param vector2ToSubtract - The Vector2 to subtract from this.
     * @return The resultant Vector2.
     */
    public Vector2 subtract(Vector2 vector2ToSubtract)
    {
        return new Vector2(this.x - vector2ToSubtract.getX(),
                this.y - vector2ToSubtract.getY());
    }

    /**
     * Promotes the Vector2 to a Vector3
     * @return a new Vector3 created from this Vector2 with it's Z Values = 0
     */
    public Vector3 toVector3()
    {
        return new Vector3(x, y, 0);
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


    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

}