package presentation.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance = new MusicManager();
    private Music gameOverMusic;
    private Music musicIntro;
    private Music musicBattle;

    /**
     * Cerca i vari file audio e li inizializza
     */
    private MusicManager() {
        musicIntro = Gdx.audio.newMusic(Gdx.files.internal("Audio/MenuMusic.mp3"));
        musicIntro.setLooping(true);
        musicIntro.setVolume(0.1f);
        musicBattle = Gdx.audio.newMusic(Gdx.files.internal("Audio/LevelBackground.mp3"));
        musicBattle.setLooping(true);
        musicBattle.setVolume(0.05f);
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/GameOver.mp3"));
        gameOverMusic.setLooping(true);
        gameOverMusic.setVolume(0.05f);
    }

    /**
     * La classe presenta un`istanza da prendere se si vogliono attivare i suoni
     * @return Ritorna un`istanza della classe
     */
    public static MusicManager getInstance() {
        return instance;
    }

    /**
     * Avvia la riproduzione della canzone per il Menu
     */
    public void playIntro() {
        musicIntro.play();
    }

    /**
     * Ferma la riproduzione della canzone per il Menu
     */
    public void stopIntro() {
        musicIntro.stop();
    }

    public void disposeIntro() {
        musicIntro.dispose();
    }

    /**
     * Avvia la riproduzione della canzone per il GameOver
     */
    public void playGameOver() {
        gameOverMusic.play();
    }

    /**
     * Ferma la riproduzione della canzone per il GameOver
     */
    public void stopGameOver() {
        gameOverMusic.stop();
    }

    public void disposeGameOver() {
        gameOverMusic.dispose();
    }

    /**
     * Avvia la riproduzione della canzone durante il gioco
     */
    public void playBattle(){ musicBattle.play();}

    /**
     * Ferma la riproduzione della canzone durante il gioco
     */
    public void stopBattle(){musicBattle.stop();}

    public void disposeBattle(){musicBattle.dispose();}
}
