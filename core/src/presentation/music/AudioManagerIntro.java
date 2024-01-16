package presentation.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManagerIntro {
    private static AudioManagerIntro instance = new AudioManagerIntro();
    private Music music;

    private AudioManagerIntro() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Minecraft.mp3"));
        music.setLooping(true);
    }

    public static AudioManagerIntro getInstance() {
        return instance;
    }

    public void playMusic() {
        music.play();
    }

    public void stopMusic() {
        music.stop();
    }

    public void dispose() {
        music.dispose();
    }
}
