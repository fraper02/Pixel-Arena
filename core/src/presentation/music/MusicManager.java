package presentation.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance = new MusicManager();
    private Music musicIntro;
    private Music musicBattle;

    private MusicManager() {
        musicIntro = Gdx.audio.newMusic(Gdx.files.internal("Audio/MenuMusic.mp3"));
        musicIntro.setLooping(true);
        musicIntro.setVolume(0.1f);
        musicBattle = Gdx.audio.newMusic(Gdx.files.internal("Audio/LevelBackground.mp3"));
        musicBattle.setLooping(true);
        musicBattle.setVolume(0.05f);
    }

    public static MusicManager getInstance() {
        return instance;
    }

    public void playIntro() {
        musicIntro.play();
    }

    public void stopIntro() {
        musicIntro.stop();
    }

    public void disposeIntro() {
        musicIntro.dispose();
    }

    public void playBattle(){ musicBattle.play();}

    public void stopBattle(){musicBattle.stop();}

    public void disposeBattle(){musicBattle.dispose();}
}
