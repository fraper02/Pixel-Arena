package application.gamelogic;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.List;

import application.entities.Villain;
import application.entities.Character;
import presentation.screens.LevelScreen;
import presentation.screens.MainMenuScreen;


public class GameLoader {
    private List<Villain> enemies = new ArrayList<>();
    private Game game;
    private Character mainCharacter;
    private int numLevel;
    private int maxNumLevel = 2;

    /**
     * Carica una lista di nemici adeguata al numero di livello passato
     * @param game il gioco corrente
     * @param mainCharacter il personaggio del giocatore
     * @param numLevel il numero del livello da caricare
     */
    public GameLoader(Game game, Character mainCharacter, int numLevel) {
        this.game = game;
        int i;
        if(numLevel == 1){
            for(i = 0; i < 3; i++) {
                enemies.add(new Villain(0, 0));
            }
        }else if(numLevel == 2){
            for(i = 0; i < 4; i++){
                enemies.add(new Villain(0, 0));
            }
        }else{
            this.game.setScreen(new MainMenuScreen(this.game));
        }
        this.mainCharacter = mainCharacter;
        this.numLevel = numLevel;
    }

    /**
     * Carica un level screen con la lista di nemici inizializzata
     */
    public void load(){
        this.game.setScreen(new LevelScreen(game, mainCharacter, enemies, numLevel));
    }

    public int getMaxNumLevel() {
        return maxNumLevel;
    }
}
