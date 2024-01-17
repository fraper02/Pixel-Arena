package presentation.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelHud extends Stage {
    private Skin skin;
    private BitmapFont font;
    private TextButton healthPoints;
    public LevelHud(){
        skin = new Skin();
        font = new BitmapFont(); // Create a default font. You can also load your own font here.
        font.getData().setScale(1.5f);
        skin.add("default", font);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        Texture heart = new Texture(Gdx.files.internal("textures/heart.png"));
        Drawable heartDrawable = new TextureRegionDrawable(new TextureRegion(heart));
        ImageButton heartButton = new ImageButton(heartDrawable);
        heartButton.setTransform(true);
        heartButton.setSize(100, 100);
        heartButton.setPosition(0 + 10f, Gdx.graphics.getHeight() - heartButton.getHeight());

        healthPoints = new TextButton("", skin, "default");
        healthPoints.setSize(100, 100);
        healthPoints.setPosition(heartButton.getX() + healthPoints.getWidth(), heartButton.getY());
        this.addActor(heartButton);
        this.addActor(healthPoints);
    }

    public void setHealthPoints(int healthPoints) {
        Integer wrapperHealth = healthPoints;
        this.healthPoints.setText(wrapperHealth.toString());
    }

    public void render(){
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.draw();
    }
}
