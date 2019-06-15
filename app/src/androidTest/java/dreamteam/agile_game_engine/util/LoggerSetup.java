package dreamteam.agile_game_engine.util;

public class LoggerSetup {

    Logger LOG = new Logger("test");
    Object testObj = new Object();

    public void setup()
    {
        LOG.disableLogging(false);
    }

    public void disable(){ LOG.disableLogging(true);}

    public void verbose()
    {
        LOG.verbose("This is a log level Verbose test", testObj);
    }

    public void debug()
    {
        LOG.debug("This is a log level Debug test", testObj);
    }

    public void info()
    {
        LOG.info("This is a log level Info test", testObj);
    }

    public void warn()
    {
        LOG.warn("This is a log level Warning test", testObj);
    }

    public void error()
    {
        LOG.error("This is a log level Error test", testObj);
    }

    public void wtf()
    {
        LOG.wtf("This is a log level What a Terrible Failure test", testObj);
    }
}