package dreamteam.agile_game_engine.audio;


import android.media.SoundPool;
/*
 * Adapted from CSC2022 - G.A.G.E.
 * This class was created by Simon
 */
public class Sound
{

    /*
     * Variable declarations
     */
    public static final int MAX_CONCURRENT_SOUNDS = 20;

    private int soundId;
    private SoundPool soundPool;
    private float volume;

    /**
     * Constructor
     * @param soundPool - the soundpool object
     * @param soundId - the unique soundId
     */
    public Sound(SoundPool soundPool, int soundId) {
        // Store the parameters and assume a default playback volume
        this.soundId = soundId;
        this.soundPool = soundPool;
        volume = 1.0f;
    }

    /**
     * This methods plays the specified sound at the volume set in the constructor
     */
    public void play()
    {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    /**
     * This method plays the specified sound at a volume set as a parameter
     * @param volume - volume value
     */
    public void play(float volume)
    {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    /**
     * This method plays the specified sound at volumes set as parameters
     * @param leftVolume - sets the volume of the left speaker
     * @param rightVolume - sets the volume of the right speaker
     */
    public void play(float leftVolume, float rightVolume)
    {
        soundPool.play(soundId, leftVolume, rightVolume, 0, 0, 1);
    }

    /**
     * This method removes the specified sound from the soundpool
     */
    public void dispose()
    {
        soundPool.unload(soundId);
    }
    /*
     * Getter and setters
     */
    public float getVolume()
    {
        return volume;
    }
    public void setVolume(float volume)
    {
        this.volume = volume;
    }
    public int getSoundId(){
        return soundId;
    }
    public void setSoundId(int newSoundId){
        soundId = newSoundId;
    }
}
