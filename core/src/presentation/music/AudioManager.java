package presentation.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static final AudioManager instance = new AudioManager();
    private Sound hit;
    private Sound miss;

    private AudioManager() {
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.mp3"));
        miss = Gdx.audio.newSound(Gdx.files.internal("audio/miss.mp3"));
    }

    public static AudioManager getInstance() {
        return instance;
    }

    public void playHit() {
        hit.play(0.1f);
    }

    public void disposeHit() {
        hit.dispose();
    }

    public void playMiss(){ miss.play(0.1f);}

    public void disposeMiss(){miss.dispose();}
}
