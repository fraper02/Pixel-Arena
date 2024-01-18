package application.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

import application.entities.Character;
import application.entities.Knight;
import application.gamelogic.InputManager;


public class SaveManager {
    private static final String PREFERENCES_NAME = "gamePreferences";

    /**
     * Metodo che permette il salvataggio del livello e delle statistiche del giocatore in un file .xml
     * @param mainCharacter Il personaggio principale
     * @param numLivello Il numero del livello successivo da cui ricominciera` a giocare
     */
    public static void saveStats(Character mainCharacter, int numLivello) {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        prefs.putInteger("vitaMassima", mainCharacter.getMaxHealthPoints());
        prefs.putInteger("atkPower", mainCharacter.getAttackPower());
        prefs.putFloat("stdSpeed", mainCharacter.getStandardSpeed());
        prefs.putString("tipo", mainCharacter.getTipo());
        prefs.putInteger("livello",numLivello);
        prefs.flush();
    }

    /**
     * Metodo che permette di riprendere tutte le statistiche del giocatore ed il livello da cui ripartira` a giocare
     * @return Ritorna un SaveWrapper che contiene il personaggio principale e il numero del livello
     * @post Il SaveWrapper non deve essere nullo
     */
    public static SaveWrapper loadStats() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        Character mainCharacter = null;
        if(prefs.getString("tipo").equalsIgnoreCase("Knight")){
            mainCharacter = new Knight(0,0);
        }
        mainCharacter.setMaxHealthPoints(prefs.getInteger("vitaMassima"));
        mainCharacter.setAttackPower(prefs.getInteger("atkPower"));
        mainCharacter.setSpeed(prefs.getFloat("stdSpeed"));
        SaveWrapper saveWrapper = new SaveWrapper(prefs.getInteger("livello"), mainCharacter);
        return saveWrapper;
    }
}
