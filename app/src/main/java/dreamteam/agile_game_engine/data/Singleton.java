package dreamteam.agile_game_engine.data;

import android.app.Application;

/**
 * This class was created by Jonathan.
 */
public class Singleton extends Application {
    /**
     * Variables data and instance are createed here.
     */
    private static Singleton instance;
    private int data = 0;

    private Singleton(){}

    /**
     * This synchronised method allows for the Singleton instance to be returned.
     * @return
     */
    public static synchronized Singleton getInstance()
    {
        if(instance == null)
        {
            instance = new Singleton();
        }
        return instance;
    }

    /**
     * This mutator allows int data to be set.
     * @param d the data to be set
     */
    public void setData(int d)
    {
        this.data=d;
    }

    /**
     * This accesso allows int data to be retrieved.
     */
    public int getData()
    {
        return this.data;
    }
}
