package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon
 */

import org.junit.Test;
import static junit.framework.Assert.*;

public class BaseGameObjectExtensionTest {

   private BaseGameObjectExtension testObject = new BaseGameObjectExtension();

    @Test
    public void testBaseGameObjectExtensionCreation()
    {
        assertEquals(testObject.position.getX(), 0, 0);
        assertEquals(testObject.position.getY(), 0, 0);
        assertEquals(testObject.position.getZ(), 0, 0);
    }

    @Test
    public void testBaseGameObjectExtensionDrawMethod()
    {
        assertEquals(testObject.startBool, false);
        assertEquals(testObject.updateBool, false);
        assertEquals(testObject.drawBool, false);

        testObject.start();
        testObject.update();
        testObject.draw();

        assertEquals(testObject.startBool, true);
        assertEquals(testObject.updateBool, true);
        assertEquals(testObject.drawBool, true);

    }

}
