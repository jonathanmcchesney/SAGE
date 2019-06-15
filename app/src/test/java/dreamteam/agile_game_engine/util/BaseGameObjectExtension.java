package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon
 */

public class BaseGameObjectExtension extends BaseGameObject {

    protected boolean startBool = false;
    protected boolean updateBool = false;
    protected boolean drawBool = false;

    protected Vector3 position = Vector3.zero();

    @Override
    public void start() {
        startBool = true;
    }

    @Override
    public void update() {
        updateBool = true;
    }

    @Override
    public void draw() {
        drawBool = true;
    }
}