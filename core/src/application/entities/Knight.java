package application.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import application.ai.Node;

public class Knight extends Character{
    private final Node loneNode = new Node(1000, 1000);
    /**
     * Initialize a knight with its value of maxHealthPoints, attackPower, standardSpeed
     * and the textures for the character's animations
     * @param x indicates position on the x axis
     * @param y indicates position on the y axis
     */
    public Knight(float x, float y) {
        super(500, 10, 0.6f, x, y);
        Texture spriteSheets = new Texture(Gdx.files.internal("textures/Raver_P0.png"));
        TextureRegion[][] frames = TextureRegion.split(spriteSheets,64,64);
        TextureRegion[] textureIdleDown = new TextureRegion[8];
        TextureRegion[] textureIdleRight = new TextureRegion[8];
        TextureRegion[] textureIdleLeft = new TextureRegion[8];
        TextureRegion[] textureIdleUp = new TextureRegion[8];
        TextureRegion[] textureWalkDown = new TextureRegion[8];
        TextureRegion[] textureWalkLeft = new TextureRegion[8];
        TextureRegion[] textureWalkRight = new TextureRegion[8];
        TextureRegion[] textureWalkUp = new TextureRegion[8];
        TextureRegion[] textureRunDown = new TextureRegion[8];
        TextureRegion[] textureRunUp = new TextureRegion[8];
        TextureRegion[] textureRunLeft = new TextureRegion[8];
        TextureRegion[] textureRunRight = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            textureIdleDown[i] = frames[0][i];
            textureIdleRight[i] = frames[1][i];
            textureIdleUp[i] = frames[2][i];
            textureIdleLeft[i] = frames[3][i];
            textureWalkDown[i] = frames[4][i];
            textureWalkRight[i] = frames[5][i];
            textureWalkUp[i] = frames[6][i];
            textureWalkLeft[i] = frames[7][i];
            textureRunDown[i] = frames[8][i];
            textureRunRight[i] = frames[9][i];
            textureRunUp[i] = frames[10][i];
            textureRunLeft[i] = frames[11][i];
        }
        TextureRegion[] textureAttackDown = new TextureRegion[6];
        TextureRegion[] textureAttackLeft = new TextureRegion[6];
        TextureRegion[] textureAttackUp = new TextureRegion[6];
        TextureRegion[] textureAttackRight = new TextureRegion[6];
        for(int i = 0; i < 6; i++){
            textureAttackDown[i] = frames[12][i];
            textureAttackRight[i] = frames[13][i];
            textureAttackUp[i] = frames[14][i];
            textureAttackLeft[i] = frames[15][i];
        }
        this.idleDown = new Animation<>(0.1f, textureIdleDown);
        this.idleLeft = new Animation<>(0.1f, textureIdleLeft);
        this.idleUp = new Animation<>(0.1f, textureIdleUp);
        this.idleRight = new Animation<>(0.1f, textureIdleRight);
        this.walkDown = new Animation<>(0.1f, textureWalkDown);
        this.walkLeft = new Animation<>(0.1f, textureWalkLeft);
        this.walkUp = new Animation<>(0.1f, textureWalkUp);
        this.walkRight = new Animation<>(0.1f, textureWalkRight);
        this.runDown = new Animation<>(0.1f, textureRunDown);
        this.runLeft = new Animation<>(0.1f, textureRunLeft);
        this.runUp = new Animation<>(0.1f, textureRunUp);
        this.runRight = new Animation<>(0.1f, textureRunRight);
        this.attackDown = new Animation<>(0.1f, textureAttackDown);
        this.attackLeft = new Animation<>(0.1f, textureAttackLeft);
        this.attackUp = new Animation<>(0.1f, textureAttackUp);
        this.attackRight = new Animation<>(0.1f, textureAttackRight);

        this.attackBoxDown = new Rectangle(this.getX() + 10, this.getY(),45,27);
        this.attackBoxLeft = new Rectangle(this.getX() + 2, this.getY() +6, 27,45);
        this.attackBoxUp = new Rectangle(this.getX() +10,this.getY() + 34,45,27);
        this.attackBoxRight = new Rectangle(this.getX() + 35, this.getY() + 6,27,45);
    }

    public Node getLoneNode(){
        return this.loneNode;
    }
}
