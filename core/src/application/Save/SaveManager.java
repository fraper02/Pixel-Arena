package application.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

import application.entities.Character;
import application.entities.Knight;
import application.gamelogic.InputManager;

public class SaveManager {
    private static final String PREFERENCES_NAME = "gamePreferences";

    private HashMap<Character, Integer> mappa = new HashMap<>();

    public static void saveStats(Character mainCharacter, int numLivello) {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        prefs.putInteger("vitaMassima", mainCharacter.getMaxHealthPoints());
        prefs.putInteger("atkPower", mainCharacter.getAttackPower());
        prefs.putFloat("stdSpeed", mainCharacter.getStandardSpeed());
        prefs.putString("tipo", mainCharacter.getTipo());
        prefs.putInteger("livello",numLivello);
        prefs.flush();
    }

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
