package application.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import application.entities.Character;

import java.util.ArrayList;

public class InputManager extends InputAdapter {

    Character character;

    ArrayList<Character> characters;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private boolean upPressed = false;

    private boolean downPressed = false;

    private boolean attackPressed = false;

    float keyframe = 0;

    float startTime = 0;

    /**
     * Initialize the InputManager class
     * @param character indicates the MainCharacter used by the User
     * @param characters indicates a List of Character used for the Enemies
     */

    public InputManager(Character character, ArrayList<Character> characters){
        this.character = character;
        this.characters = characters;
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
        if(this.isLeftPressed() || this.isDownPressed() || this.isUpPressed() || this.isRightPressed()){
            if(character.isRunning()){
                character.doRun();
            }else{
                character.doWalk();
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K) && !this.isUpPressed() && !this.isDownPressed() && !this.isRightPressed() && !this.isLeftPressed() && (stateTime - startTime >= 0.6f)){
            character.doAttack();
            startTime = stateTime;
            attackPressed = true;
            //character.checkAttack(character2);
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
