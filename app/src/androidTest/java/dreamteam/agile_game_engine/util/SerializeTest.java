package dreamteam.agile_game_engine.util;

/**
 * This class was created by Simon
 */

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import dreamteam.agile_game_engine.objects.Cube;
import dreamteam.agile_game_engine.objects.Prism;
import dreamteam.agile_game_engine.objects.Triangle;

import static junit.framework.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SerializeTest {

    private Context context;

    @Before
    public void setUp()
    {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testSerializeSaveVector2()
    {
        Vector2 testVector2 = new Vector2(1,2);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Vector2 is serialized successfully.
        success = SerializeObject.serializeVector2(testVector2);
        assertTrue(success);
    }

    @Test
    public void testSerializeSaveVector3()
    {
        Vector3 testVector3 = new Vector3(1,2,3);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Vector3 is serialized successfully.
        success = SerializeObject.serializeVector3(testVector3);
        assertTrue(success);
    }

    @Test
    public void testSerializeSaveVector4()
    {
        Vector4 testVector4 = new Vector4(1,2,3,4);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Vector4 is serialized successfully.
        success = SerializeObject.serializeVector4(testVector4);
        assertTrue(success);
    }

    @Test
    public void testSerializeGameObject()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));

        GameObject gameObject = new GameObject(assetStore);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the GameObject is serialized successfully.
        success = SerializeObject.serializeGameObject(gameObject);
        assertTrue(success);
    }

    @Test
    public void testSerializeSaveTriangle()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        Triangle triangle = new Triangle(assetStore);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Triangle is serialized successfully.
        success = SerializeObject.serializeTriangle(triangle);
        assertTrue(success);
    }

    @Test
    public void testSerializeSaveCube()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        Cube cube = new Cube(assetStore);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Cube is serialized successfully.
        success = SerializeObject.serializeCube(cube);
        assertTrue(success);
    }


    @Test
    public void testSerializeSavePrism()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        Prism prism = new Prism(assetStore);
        boolean success = false;

        //Check to make sure we have permission to write to the external storage.
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertEquals(i,0);

        //Ensure the Prism is serialized successfully.
        success = SerializeObject.serializePrism(prism);
        assertTrue(success);
    }


    @Test
    public void testSerializeLoadVector2()
    {
        //Ensuring the Vector2 is saved successfully
        Vector2 testVector2 = new Vector2(1,2);
        boolean success = false;
        success = SerializeObject.serializeVector2(testVector2);
        assertTrue(success);

        //Loading in the serialized Vector2
        Vector2 loadedVector2 = (Vector2)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedVector2.jesas")));

        //Comparing the values between the original Vector2, and the de-serialized Vector2.
        assertEquals(testVector2.getX(), loadedVector2.getX());
        assertEquals(testVector2.getY(), loadedVector2.getY());
    }

    @Test
    public void testSerializeLoadVector3()
    {
        //Ensuring the Vector3 is saved successfully
        Vector3 testVector3 = new Vector3(1,2,3);
        boolean success = false;
        success = SerializeObject.serializeVector3(testVector3);
        assertTrue(success);

        //Loading in the serialized Vector3
        Vector3 loadedVector3 = (Vector3)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedVector3.jesas")));

        //Comparing the values between the original Vector3, and the de-serialized Vector3.
        assertEquals(testVector3.getX(), loadedVector3.getX());
        assertEquals(testVector3.getY(), loadedVector3.getY());
        assertEquals(testVector3.getZ(), loadedVector3.getZ());
    }

    @Test
    public void testSerializeLoadVector4()
    {
        //Ensuring the Vector4 is saved successfully
        Vector4 testVector4 = new Vector4(1,2,3,4);
        boolean success = false;
        success = SerializeObject.serializeVector4(testVector4);
        assertTrue(success);

        //Loading in the serialized Vector4
        Vector4 loadedVector4 = (Vector4)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedVector4.jesas")));

        //Comparing the values between the original Vector4, and the de-serialized Vector4.
        assertEquals(testVector4.getX(), loadedVector4.getX());
        assertEquals(testVector4.getY(), loadedVector4.getY());
        assertEquals(testVector4.getZ(), loadedVector4.getZ());
        assertEquals(testVector4.getW(), loadedVector4.getW());
    }


    @Test
    public void testSerializeLoadGameObject()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));

        //Ensuring the prism is saved successfully
        GameObject testGameObject = new GameObject(assetStore);
        testGameObject.setXMove(12);
        testGameObject.setYMove(14);
        boolean success = false;
        success = SerializeObject.serializeGameObject(testGameObject);
        assertTrue(success);

        //Loading in the serialized Prism
        GameObject loadedGameObject = (GameObject)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedGameObject.jesas")));

        //Comparing the values between the original prism, and the de-serialized prism.
        assertEquals(testGameObject.getXMove(), loadedGameObject.getXMove());
        assertEquals(testGameObject.getYMove(), loadedGameObject.getYMove());
    }

    @Test
    public void testSerializeLoadTriangle()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));

        //Ensuring the triangle is saved successfully
        Triangle testTriangle = new Triangle(assetStore);
        testTriangle.setXMove(12);
        testTriangle.setYMove(14);
        boolean success = false;
        success = SerializeObject.serializeTriangle(testTriangle);
        assertTrue(success);

        //Loading in the serialized triangle
        Triangle loadedTriangle = (Triangle)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedTriangle.jesas")));

        //Comparing the values between the original cube, and the de-serialized triangle.
        assertEquals(testTriangle.getXMove(), loadedTriangle.getXMove());
        assertEquals(testTriangle.getYMove(), loadedTriangle.getYMove());
    }

    @Test
    public void testSerializeLoadCube()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));

        //Ensuring the cube is saved successfully
        Cube testCube = new Cube(assetStore);
        testCube.setXMove(12);
        testCube.setYMove(14);
        boolean success = false;
        success = SerializeObject.serializeCube(testCube);
        assertTrue(success);

        //Loading in the serialized Cube
        Cube loadedCube = (Cube)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedCube.jesas")));

        //Comparing the values between the original cube, and the de-serialized cube.
        assertEquals(testCube.getXMove(), loadedCube.getXMove());
        assertEquals(testCube.getYMove(), loadedCube.getYMove());
    }


    @Test
    public void testSerializeLoadPrism()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));

        //Ensuring the prism is saved successfully
        Prism testPrism = new Prism(assetStore);
        testPrism.setXMove(12);
        testPrism.setYMove(14);
        boolean success = false;
        success = SerializeObject.serializePrism(testPrism);
        assertTrue(success);

        //Loading in the serialized Prism
        Prism loadedPrism = (Prism)SerializeObject.loadObject((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSC3045/agileTestSavedPrism.jesas")));

        //Comparing the values between the original prism, and the de-serialized prism.
        assertEquals(testPrism.getXMove(), loadedPrism.getXMove());
        assertEquals(testPrism.getYMove(), loadedPrism.getYMove());
    }
}