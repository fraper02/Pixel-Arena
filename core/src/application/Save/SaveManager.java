package application.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import application.entities.Knight;

public class SaveManager {
    private static final String PREFERENCES_NAME = "gamePreferences";

    public static void saveStats(Knight knight) {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        //Aggiungere tutte le statistiche e il livello che sta giocando il player
        prefs.putInteger("score", stats.score);
        prefs.putInteger("lives", stats.lives);
        prefs.flush();
    }

    public static Knight loadStats() {
        //DA VERIFICARE SE ISTANZIARE O NO
        Knight mainCharacter = new Knight();
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        //PRENDERE DATI PER MC
        stats.score = prefs.getInteger("score", 0);
        stats.lives = prefs.getInteger("lives", 3); // Valore predefinito, se necessario
        return stats;
    }
}
