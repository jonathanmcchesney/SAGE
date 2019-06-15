package dreamteam.agile_game_engine.util;

/*
* This Class was created by Simon
 */

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dreamteam.agile_game_engine.objects.Cube;
import dreamteam.agile_game_engine.objects.Prism;
import dreamteam.agile_game_engine.objects.Triangle;

public class SerializeObject {

    private static Logger logger = new Logger("Serialization");

    /**
     * Method to Serialize A Vector2 Object
     * @param vector2 - Vector2 Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeVector2(Vector2 vector2)
    {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedVector2.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedVector2.jesas");
            oos.writeObject(vector2);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return false;
        }
    }

    /**
     * Method to Serialize A Vector3 Object
     * @param vector3 - Vector23 Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeVector3(Vector3 vector3)
    {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedVector3.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedVector3.jesas");
            oos.writeObject(vector3);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return false;
        }
    }

    /**
     * Method to Serialize A Vector4 Object
     * @param vector4 - Vector4 Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeVector4(Vector4 vector4)
    {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedVector4.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedVector4.jesas");
            oos.writeObject(vector4);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return false;
        }
    }

    /**
     * Method to Serialize A GameObject
     * @param gameObject - GameObject to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeGameObject(GameObject gameObject)
    {
        try {
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedGameObject.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedGameObject");
            oos.writeObject(gameObject);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e );
            return false;
        }
    }

    /**
     * Method to Serialize A Triangle Object
     * @param triangle - Triangle Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeTriangle(Triangle triangle)
    {
        try {
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedTriangle.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedTriangle");
            oos.writeObject(triangle);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e );
            return false;
        }
    }

    /**
     * Method to Serialize A Cube Object
     * @param cube - Cube Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializeCube(Cube cube)
    {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedCube.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedCube");
            oos.writeObject(cube);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return false;
        }
    }

    /**
     * Method to Serialize A Prism Object
     * @param prism - Prism Object to Serialize
     * @return Returns true if a success.
     */
    public static boolean serializePrism(Prism prism)
    {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/");
            directory.mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "/agileTestSavedPrism.jesas"));
            logger.debug(Environment.getExternalStorageDirectory().getAbsolutePath() + "/agileTestSavedPrism");
            oos.writeObject(prism);
            oos.flush();
            oos.close();
            return true;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return false;
        }
    }

    /**
     * Method to load in any serialized object.
     * @param file - the file path of the serialized file
     * @return the file as a de-serialized object
     */
    public static Object loadObject(File file)
    {
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream((file)));
            Object obj = ois.readObject();
            return obj;
        }catch(IOException e)
        {
            logger.error("Serialization Failure", "Serialization Failure : " + e);
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}