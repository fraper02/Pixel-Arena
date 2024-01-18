package application.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Gemma {
    private Rectangle hitBox;
    private Texture texture;

    /**
     * Crea una nuova gemma con una hitbox e una texture
     * @param x il punto di partenza dell'hitbox sull'asse delle x
     * @param y il punto di partenza dell'hitbox sull'asse delle y
     */
    public Gemma(float x, float y){
        this.hitBox = new Rectangle(x, y, 16, 16);
        this.texture = new Texture(Gdx.files.internal("textures/diamond.png"));
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Texture getTexture() {
        return texture;
    }
}
