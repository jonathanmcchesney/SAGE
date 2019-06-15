package dreamteam.agile_game_engine.util;

/**
 * This Class was created by Simon
 */
public abstract class BaseGameObject
{

    /**
     * start Method to contain Initialization Logic
     */
    public abstract void start();

    /**
     * update Method to contain update Logic
     */
    public abstract void update();

    /**
     * draw Method to contain Rendering Logic
     */
    public void draw()
    {}
}
