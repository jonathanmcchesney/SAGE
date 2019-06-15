package dreamteam.agile_game_engine.util;

import android.util.Log;

/**
 * This class was created by Jonathan.
 */

public class Logger {

    /**
     * Variable Declarations
     */
    private static final String DELIMINATOR = "{}";
    private static boolean loggerDisabled;
    private String tag, className;

    /**
     * Custom Logger constructor
     * @param tag associated with the Logger
     */
    public Logger(String tag)
    {
        this.className = "";
        this.tag = tag;
    }

    /**
     * @param c the class associated with the Logger
     * @param tag the tag associated with the Logger
     */
    private Logger(Class c, String tag)
    {
        this.className = getClassName(c.getName());
        this.tag = tag;
    }

    /**
     * Method for disabling the Custom Logger
     * @param isDisabled true if disabled
     */
    public static void disableLogging(boolean isDisabled)
    {
        Logger.loggerDisabled = isDisabled;
    }

    /**
     * Method for returning the desired Log Level
     * @param tag the tag associated
     * @param level the Log level associated
     * @return
     */
    public static boolean isLoggable(String tag, int level)
    {
        return Log.isLoggable(tag, level);
    }

    /**
     * returns a verbose level log message
     * @param obj the object associated with the Logger
     */
    public void verbose(Object obj)
    {
        if (!loggerDisabled)
        {
            Log.v(tag, getString(obj));
        }
        return;
    }

    /**
     * returns a debug level log message
     * @param obj the object associated with the Logger
     */
    public void debug(Object obj)
    {
        if (!loggerDisabled)
        {
            Log.d(tag, getString(obj));
        }
        return;
    }

    /**
     * returns an info level log message
     * @param obj the object associated with the Logger
     */
    public void info(Object obj)
    {
        if (!loggerDisabled)
        {
            Log.i(tag, getString(obj));
        }
        return;
    }

    /**
     * returns a warn level log message
     * @param obj the object associated with the Logger
     */
    public void warn(Object obj)
    {
        if (!loggerDisabled)
        {
            Log.w(tag, getString(obj));
        }
        return;
    }

    /**
     * returns an error level log message
     * @param obj the object associated with the Logger
     */
    public void error(Object obj)
    {
        Log.e(tag, getString(obj));
    }

    /**
     * returns an assert level log message
     * @param obj the object associated with the Logger
     */
    public void wtf(Object obj)
    {
        Log.wtf(tag, getString(obj));
    }

    /**
     * returns a verbose level log message
     * @param message the message associated with the log
     * @param args the argumenets asssociated with the logger
     */
    public void verbose(String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Throwable t = getThrowable(args);
            if (t != null)
            {
                Log.v(tag, fMessage(message, args), t);
            }
            else
            {
                Log.v(tag, fMessage(message, args));
            }
        }
        return;
    }

    /**
     * returns a debug level log message
     * @param message the message associated with the log
     * @param args the argumenets asssociated with the logger
     */
    public void debug(String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Throwable t = getThrowable(args);
            if (t != null)
            {
                Log.d(tag, fMessage(message, args), t);
            }
            else
            {
                Log.d(tag, fMessage(message, args));
            }
        }
        return;
    }

    /**
     * returns an info level log message
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    public void info(String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Throwable t = getThrowable(args);
            if (t == null)
            {
                Log.i(tag, fMessage(message, args));
            }
            else
            {
                Log.i(tag, fMessage(message, args), t);
            }
        }
        return;
    }

    /**
     * returns a warn level log message
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    public void warn(String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Throwable t = getThrowable(args);
            if (t != null)
            {
                Log.w(tag, fMessage(message, args), t);
            }
            else
            {
                Log.w(tag, fMessage(message, args));
            }
        }
        return;
    }

    /**
     * returns an error level log message
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    public void error(String message, Object... args)
    {
        Throwable t = getThrowable(args);
        if (t != null)
        {
            Log.e(tag, fMessage(message, args), t);
        }
        else
        {
            Log.e(tag, fMessage(message, args));
        }
    }

    /**
     * returns an assert level log message
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    public void wtf(String message, Object... args)
    {
        Log.wtf(tag, fMessage(message, args));
    }

    /**
     * returns a deprecated info level log message
     * @param t the throwable instance associated with the log
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    @Deprecated
    public void info(Throwable t, String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Log.i(tag, fMessage(message, args), t);
        }
        return;
    }

    /**
     * returns a deprecated warn level log message
     * @param t the throwable instance associated with the log
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    @Deprecated
    public void warn(Throwable t, String message, Object... args)
    {
        if (!loggerDisabled)
        {
            Log.w(tag, fMessage(message, args), t);
        }
        return;
    }

    /**
     * returns a deprecated error level log message
     * @param t the throwable instance associated with the log
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    @Deprecated
    public void error(Throwable t, String message, Object... args)
    {
        Log.e(tag, fMessage(message, args), t);
    }

    /**
     * returns a deprecated assert level log message
     * @param t the throwable instance associated with the log
     * @param message the message associated with the log
     * @param args the arguments associated with the logger
     */
    @Deprecated
    public void wtf(Throwable t, String message, Object... args)
    {
        Log.wtf(tag, fMessage(message, args), t);
    }

    /**
     * returns an error level log message
     * @param message the message associated with the log
     * @param t the throwable instance associated with the log
     */
    public void error(String message, Throwable t)
    {
        Log.e(tag, message, t);
    }

    /**
     * formats the message being passed into the logger using the DELIMINATOR
     * @param message the message associated with the log
     * @param args the arguments associated with the log
     * @return
     */
    private String fMessage(String message, Object... args)
    {
        StringBuilder sb = new StringBuilder(className + message);
        if (args != null && message != null)
        {
            for (Object arg : args)
            {
                int index = sb.indexOf(DELIMINATOR);
                if (index == -1)
                {
                    break;
                }
                sb.replace(index, index + DELIMINATOR.length(), arg == null ? "null" : arg.toString());
            }
        }
        return sb.toString();
    }

    /**
     * Accessor for getting the throwable instance
     * @param args the arguments associated
     * @return
     */
    private Throwable getThrowable(Object[] args)
    {
        if (args.length == 0||args == null)
        {
            return null;
        }
        Object lastObject = args[args.length - 1];
        if (lastObject instanceof Throwable)
        {
            return (Throwable) lastObject;
        }
        return null;
    }

    /**
     * accessor for getting the tag or returning the default value
     * @param c the class associated
     * @return
     */
    private String getTag(Class c)
    {
        String className = c.getName();
        String tag[] = className.split("\\.");
        if (tag.length < 2||tag == null)
        {
            return "dreamteam.agile_game_engine"; // Default tag
        }
        else
        {
            return tag[1];
        }
    }

    /**
     * accessor for getting the string
     * @param obj the associated object
     * @return
     */
    private String getString(Object obj)
    {
        if (obj != null)
        {
            return fMessage(obj.toString());
        }
        return fMessage(null);
    }

    /**
     * accessor for getting the stack trace
     * @param t the throwable instance associated
     * @return
     */
    public static String getStackTraceString(Throwable t)
    {
        return Log.getStackTraceString(t);
    }

    /**
     * accessor for getting the class name
     * @param className the class name associated
     * @return
     */
    private String getClassName(String className)
    {
        return className + " >> ";
    }

    /**
     * accessor for getting the logger
     * @param tag the tag associated
     * @return
     */
    public static Logger getLogger(String tag)
    {
        return new Logger(tag);
    }

    /**
     * accessor for getting the logger
     * @param c the class associated
     * @param tag the tag associated
     * @return
     */
    public static Logger getLogger(Class c, String tag)
    {
        return new Logger(c, tag);
    }
}
