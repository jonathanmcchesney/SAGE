package dreamteam.agile_game_engine.audio;

import android.content.Context;
import android.media.MediaPlayer;

import dreamteam.agile_game_engine.R;

/*
 * This class was created by Aoife
 */
public class SoundEffect
{
    /*
     * Variable declarations
     */
    private MediaPlayer mySound;
    private Context context;
    private int paused;
    private int soundId;

    /**
     * Constructor
     * @param context - the context
     * @param soundId - the sound Id
     */
    public SoundEffect(Context context, int soundId)
    {
        this.context = context;
        this.soundId = soundId;
    }

    /**
     * This method instantiates the mySound to the specified soundId and plays it
     */
    public void play()
    {
        //only plays if not null
        if(mySound == null)
        {
            mySound = MediaPlayer.create(context,soundId);
            mySound.start();
            //only starts again is not playing
        } else if(!mySound.isPlaying())
        {
            mySound.seekTo(paused);
            mySound.start();
        }
    }

    /**
     * This method pauses the mySound variable and saves the pause position
     */
    public void pause()
    {
        mySound.pause();
        paused = mySound.getCurrentPosition();
    }

    /**
     * This method stops the mySound variable completely
     */
    public void stop()
    {
        mySound.release();
        //resets sound to null so it can be restarted in the play method
        mySound = null;
    }
    /*
     * Getters and setters
     */
    public MediaPlayer getMySound()
    {
        return mySound;
    }
    public void setMySound(int newId)
    {
        soundId = newId;
    }
    public int getSoundId()
    {
        return soundId;
    }
    public int getPause()
    {
        return paused;
    }
    public void setPaused(int newPaused)
    {
        paused = newPaused;
    }
}
