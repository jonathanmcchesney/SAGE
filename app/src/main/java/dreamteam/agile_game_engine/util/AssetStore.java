package dreamteam.agile_game_engine.util;

/**
 * This Class was created by Simon
 * Adapted from CSC2022 G.A.G.E
 */

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

import dreamteam.agile_game_engine.audio.Sound;
import dreamteam.agile_game_engine.errorHandling.AssetStoreBitmapNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreShaderNotFoundException;
import dreamteam.agile_game_engine.errorHandling.AssetStoreSoundNotFoundException;
import dreamteam.agile_game_engine.graphics.Shader;

public class AssetStore {

    /*
     * Variable Declarations
     */
    private HashMap<String, Bitmap> bitmapMap;
    private HashMap<String, Sound> soundMap;
    private HashMap<String, Shader> shaderMap;
    private SoundPool soundPool;
    private FileIO fileIO;
    private Logger logger = new Logger("AssetStore");

    /**
     * Constructor Method
     * @param fileIO - FileIO to Load in Assets from Assets Folder
     */
    public AssetStore(FileIO fileIO) {
        this.fileIO = fileIO;
        bitmapMap = new HashMap<String, Bitmap>();
        soundMap = new HashMap<String, Sound>();
        shaderMap = new HashMap<String, Shader>();
        soundPool = new SoundPool(Sound.MAX_CONCURRENT_SOUNDS, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * Adds a Bitmap to the Bitmap Hashmap
     * @param assetName - The Name of the Asset
     * @param bitmap - The bitmap file to add
     * @return returns a success if added successfully, or a false if a failure occurs.
     */
    public boolean add(String assetName, Bitmap bitmap) {
        if (bitmapMap.containsKey(assetName)) {
            logger.error("Agile Game Engine", "Asset Bitmap[" + assetName + "] Already in Store");
            return false;
        }

        bitmapMap.put(assetName, bitmap);
        return true;
    }

    /**
     * Adds a Sound to the Sound Hashmap
     * @param assetName - The Name of the Asset
     * @param sound - The sound file to add.
     * @return returns a true if added successfully, or a false if a failure occurs.
     */
    public boolean add(String assetName, Sound sound) {
        if (soundMap.containsKey(assetName)) {
            return false;
        }

        soundMap.put(assetName, sound);
        return true;
    }

    /**
     * Add a Shader to to the Shader Hashmap
     * @param assetName - The name of the Asset
     * @param shader - The shader file to add.
     * @return returns a true if added successfully, or a false if a failure occurs.
     */
    public boolean add(String assetName, Shader shader)
    {
        if(shaderMap.containsKey(assetName))
        {
            return false;
        }else
        {
            shaderMap.put(assetName, shader);
            return true;
        }
    }

    /**
     * Loads and Adds a Bitmap into the AssetStore
     * @param assetName - The name of the asset to load + add
     * @param fileName - the filepath of the asset
     * @return returns a true if successful, a false if a failure occurs.
     */
    public boolean loadAndAddBitmap(String assetName, String fileName) {
        boolean success = false;

        try {
            Bitmap bitmap = fileIO.loadBitmap(fileName, null);
            success = add(assetName, bitmap);
        } catch (IOException e) {
            logger.error("Bitmap Load Error", "Bitmap file could not be loaded : " + fileName);
        }


        return success;
    }

    /**
     * Loads and Adds a Bitmap into the AssetStore (using a raw Resource ID)
     * @param assetName - The name of the asset to load + add
     * @param resourceId - the Raw resource ID of the asset.
     * @return returns a true if successful, a false if a failure occurs.
     */
    public boolean loadAndAddBitmap(String assetName, int resourceId) {
        boolean success;

        Bitmap bitmap = fileIO.loadBitmapByResource(resourceId);
        success = add(assetName, bitmap);

        return success;
    }

    /**
     * Loads and Adds a Sound into the AssetStore
     * @param assetName - The name of the asset to load + add
     * @param soundFile - The filepath of the Sound file
     * @return returns a true if successful, a false if an error occurs.
     */
    public boolean loadAndAddSound(String assetName, String soundFile) {
        boolean success = true;
        try {
            Sound sound = fileIO.loadSound(soundFile, soundPool);
            success = add(assetName, sound);
        } catch (IOException e) {
            logger.error("Gage", "AssetStore.loadAndAddSound: Cannot load ["
                    + soundFile + "]");
            success = false;
        }

        return success;
    }

    /**
     * Loads and Adds a Sound into the AssetStore
     * @param assetName - The name of the asset to load + add
     * @param shaderFile - The filepath of the shader file.
     * @return
     */
    public boolean loadAndAddShader(String assetName, String shaderFile)
    {
        boolean success = true;

        try{
            Shader newShader = fileIO.loadShader(shaderFile);
            success = add(assetName, newShader);
        }catch(IOException e)
        {
            logger.error("AssetStore", "loadAndAddShader: Cannot load ["
                    + shaderFile + "]");
            success = false;
        }
        return success;
    }

    /**
     * Gets a Shader from the Shader HashMap
     * @param assetName - The name of the Shader to return
     * @return The chosen Shader
     * @throws AssetStoreShaderNotFoundException
     */
    public Shader getShader(String assetName) throws AssetStoreShaderNotFoundException
    {
        Shader returnShader = null;
        returnShader = shaderMap.get(assetName);

        if(returnShader == null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Shader : ");
            sb.append(assetName);
            sb.append(" could not be found");

            throw new AssetStoreShaderNotFoundException(sb.toString(), new Throwable("Shader Not Found"));
        }

        return returnShader;
    }

    /**
     * Gets a Sound from the Sound Hashmap
     * @param assetName - The name of the Sound file to return
     * @return The chosen Sound
     * @throws AssetStoreSoundNotFoundException
     */
    public Sound getSound(String assetName) throws AssetStoreSoundNotFoundException {
        Sound returnSound = null;
        returnSound = soundMap.get(assetName);

        if(returnSound == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sound File : ");
            sb.append(assetName);
            sb.append(" could not be found");
            throw new AssetStoreSoundNotFoundException(sb.toString(), new Throwable("Does not exist in SoundMap"));
        }else
        {
            return returnSound;
        }
    }

    /**
     * Gets a Bitmap from the Bitmap Hashmap
     * @param assetName - The name of the Btimap to return
     * @return The chosen Bitmap
     * @throws AssetStoreBitmapNotFoundException
     */
    public Bitmap getBitmap(String assetName) throws AssetStoreBitmapNotFoundException {
        Bitmap returnBitmap = null;
        returnBitmap = bitmapMap.get(assetName);

        if(returnBitmap == null) {
            StringBuilder sb = new StringBuilder();

            sb.append("Bitmap File : ");
            sb.append(assetName);
            sb.append(" could not be found");
            throw new AssetStoreBitmapNotFoundException(sb.toString(), new Throwable("Does not exist in Bitmap"));
        }else
        {
            return returnBitmap;
        }
    }

    public HashMap<String,Bitmap> getBitmapMap()
    {
        return this.bitmapMap;
    }

    public HashMap<String,Sound> getSoundMap()
    {
        return this.soundMap;
    }

    public HashMap<String,Shader> getShaderMap()
    {
        return this.shaderMap;
    }
}