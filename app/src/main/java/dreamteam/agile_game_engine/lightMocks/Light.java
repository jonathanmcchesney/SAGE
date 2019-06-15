package dreamteam.agile_game_engine.lightMocks;

import dreamteam.agile_game_engine.util.Vector3;

/**
 * This class was created by Shane
 */
public class Light {

    /*
     * Variable declarations
     */
    private Vector3 position, colour;

    /**
     * Constructor
     * @param position - the three point position of the light
     * @param colour - the three float value of the color
     */

    public Light(Vector3 position, Vector3 colour)
    {
        this.position = position;
        this.colour = colour;
    }

    /*
     * Getters and setters
     */
     public Vector3 getColour()
        {
            return colour;
        }
     public void setColour(Vector3 colour)
        {
            this.colour = colour;
        }
     public Vector3 getPosition()
        {
            return position;
        }
     public void setPosition(Vector3 position)
        {
            this.position = position;
        }
}
