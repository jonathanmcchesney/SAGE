package dreamteam.agile_game_engine.util;

import dreamteam.agile_game_engine.util.Vector3;

/**
 * This class was created by Jonathan.
 */

public class Camera
{
    /**
     * Variable declarations
     */
    private Vector3 position = new Vector3(0,5,0);
    private  float eyeX,  eyeY,  eyeZ,       // Eye is looking in front of the origi
                   camX,  camY,  camZ,       // Camera is looking in front of origin also.
                   upX,   upY,   upZ;        // Up vector is set.

    /**
     * Default constructor
     */
    public Camera()
    {
        eyeX = 0.0f;
        eyeY = 0.0f;
        eyeZ = -3.0f;
        camX = 0.0f;
        camY = 0.0f;
        camZ = 0.0f;
        upX = 0.0f;
        upY = 1.0f;
        upZ = 0.0f;
    }

    /**
     * Constructor
     * @param eyeX - the  eye's x coordinate
     * @param eyeY - the eye's y coordinate
     * @param eyeZ - the eye's z coordinate
     * @param camX - the camera's x coordinate
     * @param camY - the camera's y coordinate
     * @param camZ - the camera's z coordinate
     * @param upX - the up vector's x coordinate
     * @param upY - the up vector's y coordinate
     * @param upZ - the up vector's z coordinate
     */
    public Camera(float eyeX, float eyeY, float eyeZ, float camX, float camY, float camZ, float upX, float upY, float upZ)
    {
        this.eyeX = eyeX;
        this.eyeY = eyeY;
        this.eyeZ = eyeZ;
        this.camX = camX;
        this.camY = camY;
        this.camZ = camZ;
        this.upX = upX;
        this.upY = upY;
        this.upZ = upZ;
    }

    /**
     * This method moves the eye's x-coordinate by a specified float
     * @param eyeXModifier - a float that adds to the eye's x-coordinate
     * @return - the eyeXModifier, plus the specified float
     */
    public float moveEyeX(float eyeXModifier)
    {
        return this.eyeX += eyeXModifier;
    }

    /**
     * This method moves the eye's y-coordinate by a specified float
     * @param eyeYModifier - a float that adds to the eye's y-coordinate
     * @return - the eyeYModifier, plus the specified float
     */
    public float moveEyeY(float eyeYModifier)
    {
        return this.eyeY += eyeYModifier;
    }

    /**
     * This method moves the eye's z-coordinate by a specified float
     * @param eyeZModifier - a float that adds to the eye's z-coordinate
     * @return - the eyeZModifier, plus the specified float
     */
    public float moveEyeZ(float eyeZModifier)
    {
        return this.eyeZ += eyeZModifier;
    }

    /**
     * This method moves the camera's x-coordinate by a specified float
     * @param camXModifier - a float that adds to the camera's x-coordinate
     * @return - the camXModifier, plus the specified float
     */
    public float moveCamX(float camXModifier)
    {
        return this.camX += camXModifier;
    }

    /**
     * This method moves the camera's y-coordinate by a specified float
     * @param camYModifier - a float that adds to the camera's y-coordinate
     * @return - the camYModifier, plus the specified float
     */
    public float moveCamY(float camYModifier)
    {
        return this.camY += camYModifier;
    }

    /**
     * This method moves the camera's z-coordinate by a specified float
     * @param camZModifier - a float that adds to the camera's z-coordinate
     * @return - the camZModifier, plus the specified float
     */
    public float moveCamZ(float camZModifier)
    {
        return this.camZ += camZModifier;
    }

    /**
     * This method moves the up-vector's x-coordinate by a specified float
     * @param upXModifier - a float that adds to the up vector's x-coordinate
     * @return - the upXModifier, plus the specified float
     */
    public float moveUpX(float upXModifier)
    {
        return this.upX += upXModifier;
    }

    /**
     * This method moves the up-vector's y-coordinate by a specified float
     * @param upYModifier - a float that adds to the up vector's y-coordinate
     * @return - the upYModifier, plus the specified float
     */
    public float moveUpY(float upYModifier)
    {
        return this.upY += upYModifier;
    }

    /**
     * This method moves the up-vector's z-coordinate by a specified float
     * @param upZModifier - a float that adds to the up vector's z-coordinate
     * @return - the upZModifier, plus the specified float
     */
    public float moveUpZ(float upZModifier)
    {
        return this.upZ += upZModifier;
    }

   /*
    *Getters and setters
    */
    public float getEyeX()
    {
        return eyeX;
    }
    public void setEyeX(float eyeX)
    {
        this.eyeX = eyeX;
    }
    public float getEyeY()
    {
        return eyeY;
    }
    public void setEyeY(float eyeY)
    {
        this.eyeY = eyeY;
    }
    public float getEyeZ()
    {
        return eyeZ;
    }
    public void setEyeZ(float eyeZ)
    {
        this.eyeZ = eyeZ;
    }
    public float getCamX()
    {
        return camX;
    }
    public void setCamX(float camX)
    {
        this.camX = camX;
    }
    public float getCamY()
    {
        return camY;
    }
    public void setCamY(float camY)
    {
        this.camY = camY;
    }
    public float getCamZ()
    {
        return camZ;
    }
    public void setCamZ(float camZ)
    {
        this.camZ = camZ;
    }
    public float getUpX()
    {
        return upX;
    }
    public void setUpX(float upX)
    {
        this.upX = upX;
    }
    public float getUpY()
    {
        return upY;
    }
    public void setUpY(float upY)
    {
        this.upY = upY;
    }
    public float getUpZ()
    {
        return upZ;
    }
    public void setUpZ(float upZ)
    {
        this.upZ = upZ;
    }
    public Vector3 getPosition()
    {
        return position;
    }
}
