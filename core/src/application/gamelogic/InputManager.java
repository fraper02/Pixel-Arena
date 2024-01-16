package application.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import application.ai.TilesGraph;
import application.entities.Character;
import application.entities.Villain;
import presentation.music.AudioManager;

import java.util.HashMap;
import java.util.List;

public class InputManager extends InputAdapter {

    private Character character;
    private HashMap<TilesGraph,Villain> graphVillainHashMap;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean attackPressed = false;
    float keyframe = 0;
    float startTime = 0;
    private AudioManager audioManager = AudioManager.getInstance();

    /**
     * Initialize the InputManager class
     * @param character indicates the MainCharacter used by the User
     * @param graphVillainHashMap indicates a Map of each Graph with the assigned Enemy
     */

    public InputManager(Character character, HashMap<TilesGraph,Villain> graphVillainHashMap){
        this.character = character;
        this.graphVillainHashMap = graphVillainHashMap;
    }

    /**
     * Initialize the variables for the Input Directions Check(Pressed Case)
     */

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = true;
                character.setDirection(Character.Directions.WEST);
                break;
            case Input.Keys.W:
                upPressed = true;
                character.setDirection(Character.Directions.NORTH);
                break;
            case Input.Keys.S:
                downPressed = true;
                character.setDirection(Character.Directions.SOUTH);
                break;
            case Input.Keys.D:
                rightPressed = true;
                character.setDirection(Character.Directions.EAST);
                break;
        }
        return true;
    }

    /**
     * A toggle for running variable
     */
    private void toggleRun(){
            character.setRunning(!character.isRunning());
    }

    /**
     * A method that return the Animation Character Frame
     * @param stateTime  is the input to pick a key frame from your Animation
     * @return the frame of current Animation
     */
    public TextureRegion nextFrame(float stateTime){
        keyframe = stateTime;
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
            this.toggleRun();
        }
        if(this.isLeftPressed()){
            if(character.isRunning()){
                character.doRun("LEFT");
            }else{
                character.doWalk("LEFT");
            }
        }
        else if(this.isDownPressed()){
            if(character.isRunning()){
                character.doRun("DOWN");
            }else{
                character.doWalk("DOWN");
            }
        }
        else if(this.isUpPressed()){
            if(character.isRunning()){
                character.doRun("UP");
            }else{
                character.doWalk("UP");
            }
        }
        else if(this.isRightPressed()){
            if(character.isRunning()){
                character.doRun("RIGHT");
            }else{
                character.doWalk("RIGHT");
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K) && !this.isUpPressed() && !this.isDownPressed() && !this.isRightPressed() && !this.isLeftPressed() && (stateTime - startTime >= 0.6f)){
            character.doAttack();
            startTime = stateTime;
            attackPressed = true;
            if(character.getTilesGraph() != null){
                if(character.checkAttack(graphVillainHashMap.get(character.getTilesGraph()))){
                    audioManager.playHit();
                }else{
                    audioManager.playMiss();
                }
            }else{
                audioManager.playMiss();
            }

            keyframe = 0;
        }

        if(stateTime - startTime >= 0.6f){
            attackPressed = false;
            character.setAttacking(false);
        }

        if(!character.isAttacking() && !this.isUpPressed() && !this.isDownPressed() && !this.isRightPressed() && !this.isLeftPressed()){
            character.doStopAndIdle();
        }

        return character.getCurrentAnimation().getKeyFrame(keyframe,true);
    }

    /**
     * Initialize the variables for the Input Directions Check(Released Case)
     */

    @Override
    public boolean keyUp(int keycode){
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.S:
                downPressed = false;
                break;
            case Input.Keys.D:
                rightPressed = false;
                break;
        }
        return true;
    }

    public boolean isLeftPressed(){return leftPressed;}
    public boolean isRightPressed(){return rightPressed;}

    public boolean isUpPressed(){return upPressed;}

    public boolean isDownPressed(){return downPressed; }

}
