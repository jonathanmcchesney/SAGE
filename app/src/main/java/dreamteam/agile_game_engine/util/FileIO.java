package dreamteam.agile_game_engine.util;

/**
 * This Class was created By Simon - Adapted from CSC2022 G.A.G.E
 */

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.graphics.BitmapFactory.Options;

import dreamteam.agile_game_engine.audio.Sound;
import dreamteam.agile_game_engine.graphics.Shader;


public class FileIO {

    /**
     * Variable Declaration
     */
    private Context context;
    private AssetManager assetManager;
    private String externalStoragePath;

    /**
     * FileIO Constructor
     * @param context
     */
    public FileIO(Context context) {
        this.context = context;
        this.assetManager = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * Loads a Bitmap from a String
     * @param fileName - The name of the Bitmap
     * @param format
     * @return Returns the Bitmap that has been loaded
     * @throws IOException
     */
    public Bitmap loadBitmap(String fileName, Bitmap.Config format) throws IOException {
        Options options = new Options();
        options.inPreferredConfig = format;
        InputStream in = null;
        Bitmap bitmap = null;

        try {
            in = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                StringBuilder str = new StringBuilder();
                str.append("Error loading : ");
                str.append(fileName);
                throw new IOException(str.toString());
            }
        } catch (IOException e) {
            StringBuilder str = new StringBuilder();
            str.append("Error loading : ");
            str.append(fileName);
            throw new IOException(str.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            return bitmap;
        }
    }

    /**
     * Loads a Bitmap from a Resource ID
     * @param id - The Resource ID.
     * @return Returns the Bitmap that has been loaded
     */
    public Bitmap loadBitmapByResource(int id)
    {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(context.getResources(), id);

        return bitmap;
    }

    /**
     * Loads a Sound from a String
     * @param filename - The File Name of the Sound file
     * @param soundPool - The Sound Pool
     * @return The Sound file that has been loaded
     * @throws IOException
     */
    public Sound loadSound(String filename, SoundPool soundPool) throws IOException {
        try {
            AssetFileDescriptor assetDescriptor = assetManager.openFd(filename);

            int soundId = soundPool.load(assetDescriptor, 0);
            return new Sound(soundPool, soundId);
        } catch (IOException e) {
            String message = "Could not load sound [" + filename + "]";
            throw new IOException(message);
        }
    }

    /**
     * Loads a Shader from a String
     * @param fileName - The filee name of the Shader file
     * @return The Shader file that has been loaded
     * @throws IOException
     */
    public Shader loadShader(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine = reader.readLine();
        while(currentLine != null)
        {
            stringBuilder.append(currentLine);
            currentLine = reader.readLine();
        }
        reader.close();

        if(stringBuilder.length() > 0) {
            return new Shader(stringBuilder.toString());
        }
        return null;
    }

}