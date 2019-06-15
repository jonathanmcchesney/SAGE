package dreamteam.agile_game_engine.util;

import org.junit.Test;

public class CustomLoggerTest {

    LoggerSetup log = new LoggerSetup();
    Object test = new Object();

    public void setup()
    {
        log.setup();
    }

    @Test
    public void Test_VERBOSE()
    {
        log.verbose();
//            assertEquals("","");
    }

    @Test
    public void Test_DEBUG()
    {
        log.debug();
        // assertEquals();
    }

    @Test
    public void Test_INFO()
    {
        log.info();
        // assertEquals();
    }

    @Test
    public void Test_WARN()
    {
        log.warn();
        // assertEquals();
    }

    @Test
    public void Test_ERROR()
    {
        log.error();
        // assertEquals();
    }

    @Test
    public void Test_WTF()
    {
        log.wtf();
        // assertEquals();
    }

    @Test
    public void Test_DISABLE()
    {
        log.disable();
        log.verbose();
        // assertEquals();
    }
}
