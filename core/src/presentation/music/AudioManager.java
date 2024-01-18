package presentation.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static final AudioManager instance = new AudioManager();
    private Sound hit;
    private Sound miss;
    private Sound gemPickup;

    /**
     * Cerca i vari file audio e li inizializza
     */
    private AudioManager() {
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.mp3"));
        miss = Gdx.audio.newSound(Gdx.files.internal("audio/miss.mp3"));
        gemPickup = Gdx.audio.newSound(Gdx.files.internal("audio/gemPickup.mp3"));
    }

    /**
     * La classe presenta un`istanza da prendere se si vogliono attivare i suoni
     * @return Ritorna un`istanza della classe
     */
    public static AudioManager getInstance() {
        return instance;
    }

    /**
     * Avvia la riproduzione del suono Hit
     */
    public void playHit() {
        hit.play(0.1f);
    }

    public void disposeHit() {
        hit.dispose();
    }

    /**
     * Avvia la riproduzione del suono Miss
     */
    public void playMiss(){ miss.play(0.1f);}

    public void disposeMiss(){miss.dispose();}

    /**
     * Avvia la riproduzione del suono GemPickup
     */
    public void playGemPickup(){gemPickup.play(0.1f);}

    public void disposeGemPickup(){gemPickup.dispose();}
}
