package dreamteam.agile_game_engine.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dreamteam.agile_game_engine.R;
import dreamteam.agile_game_engine.audio.Sound;
import dreamteam.agile_game_engine.errorHandling.AssetStoreBitmapNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreSoundNotFoundException;
import dreamteam.agile_game_engine.graphics.Shader;

/**
* This class was created by Simon
 */

@RunWith(AndroidJUnit4.class)
public class AssetStoreTest {

    private Context context;
    Logger logger = new Logger("AssetStoreTest");


    @Before
    public void setUp()
    {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testLoadAndAddBitmapByID()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddBitmap("Testbitmap", R.drawable.testbitmap);
        try {
            Bitmap bitmap = assetStore.getBitmap("Testbitmap");
        } catch (AssetStoreBitmapNotFoundException e) {
        }
        assertTrue(success);
    }

    @Test
    public void testLoadAndAddBitmap()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddBitmap("Testbitmap", "images/testbitmap.png");
        Bitmap bitmap = null;
        try {
            bitmap = assetStore.getBitmap("Testbitmap");
        } catch (AssetStoreBitmapNotFoundException e) {
        }
        assertNotNull(bitmap);
    }

    @Test
    public void testLoadAndAddBitmapInvalidData()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddBitmap("invalidBitmap", 2);
        logger.debug("Test success?", "Is invalidBitmap present? : " + assetStore.getBitmapMap().containsKey("invalidBitmap"));
        try {
            assertNull(assetStore.getBitmap("Testbitmap"));
        } catch (AssetStoreBitmapNotFoundException e) {

        }
    }

    @Test
    public void testGetInvalidBitmapData()
    {
        AssetStore assetStore = new AssetStore(new FileIO((context)));
        boolean success = false;

        try {
            assetStore.getBitmap("invalid");
        } catch (AssetStoreBitmapNotFoundException e) {
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void testGetInvalidSoundData()
    {
        AssetStore assetStore = new AssetStore(new FileIO((context)));
        boolean success = false;

        try{
            assetStore.getSound("invalid");
        }catch(AssetStoreSoundNotFoundException e){
            success = true;
        }

        assertTrue(success);
    }

    @Test
    public void testLoadAndAddSoundValidData()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddSound("test", "sounds/swag.mp3");
        Sound testSound = assetStore.getSoundMap().get("test");
        assertNotNull(testSound);
    }

    @Test
    public void testLoadAndAddSoundInvalidData()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddSound("test", "sounds/null.wav");
        Sound testSound = assetStore.getSoundMap().get("test");
        assertNull(testSound);
    }

    @Test
    public void testLoadAndAddShaderValidData()
    {
        AssetStore assetStore = new AssetStore(new FileIO(context));
        boolean success = assetStore.loadAndAddShader("TestShader", "shaders/testshader.txt");
        assertTrue(success);
    }

    @Test
    public void testGetValidShader()
    {
        boolean success = false;
        AssetStore assetStore = new AssetStore(new FileIO(context));
        assetStore.loadAndAddShader("TestShader", "shaders/testshader.txt");

        Shader newShader = null;

        try {
            newShader = assetStore.getShader("TestShader");
            success = true;
        }catch(AssetStoreShaderNotFoundException e)
        {
            success = false;
        }
        assertTrue(success);
    }

    @Test
    public void testGetInvalidShader()
    {
        boolean success = false;
        AssetStore assetStore = new AssetStore(new FileIO(context));

        try
        {
            Shader newShader = assetStore.getShader("InvalidShader");
            success = false;
        }catch(AssetStoreShaderNotFoundException e)
        {
            success = true;
        }
        assertTrue(success);
    }
}