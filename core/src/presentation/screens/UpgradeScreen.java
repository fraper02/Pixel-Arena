package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

import application.entities.Character;
import application.entities.Level;
import application.entities.Villain;

public class UpgradeScreen implements Screen {

    private Game game;
    private Character mainCharacter;
    private int numLevel;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Texture backgroundImageTexture;
    private TextureRegionDrawable backgroundDrawable;

    public UpgradeScreen(Game game, Character mainCharacter, int numLevel) {
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.numLevel = numLevel;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(900, 480));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        font = new BitmapFont(); // Create a default font. You can also load your own font here.
        font.getData().setScale(1.5f);
        skin.add("default", font);

        backgroundImageTexture = new Texture(Gdx.files.internal("MenuImages/BackG.png"));
        Image backgroundImage = new Image(backgroundImageTexture);
        backgroundImage.setFillParent(true); // This will make the image fill the entire stage
        stage.addActor(backgroundImage);


        Texture textureUpHeart = new Texture(Gdx.files.internal("textures/heart.png")); // Stato normale
        Texture textureDownHeart = new Texture(Gdx.files.internal("textures/heart.png")); // Stato premuto (opzionale)
        Texture textureDisabledHeart = new Texture(Gdx.files.internal("textures/heart.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpHeart = new TextureRegionDrawable(new TextureRegion(textureUpHeart));
        Drawable drawableDownHeart = new TextureRegionDrawable(new TextureRegion(textureDownHeart));
        Drawable drawableDisabledHeart = new TextureRegionDrawable(new TextureRegion(textureDisabledHeart));

        ImageButton heart = new ImageButton(drawableUpHeart, drawableDownHeart, drawableDisabledHeart);
        heart.setTransform(true);
        heart.setSize(100,100);
        heart.setScale((float)1.6, (float)1);
        heart.setPosition(350,240);
        stage.addActor(heart);

        Texture textureUpSword = new Texture(Gdx.files.internal("textures/sword.png")); // Stato normale
        Texture textureDownSword = new Texture(Gdx.files.internal("textures/sword.png")); // Stato premuto (opzionale)
        Texture textureDisabledSword = new Texture(Gdx.files.internal("textures/sword.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpSword = new TextureRegionDrawable(new TextureRegion(textureUpSword));
        Drawable drawableDownSword = new TextureRegionDrawable(new TextureRegion(textureDownSword));
        Drawable drawableDisabledSword = new TextureRegionDrawable(new TextureRegion(textureDisabledSword));

        ImageButton sword  = new ImageButton(drawableUpSword,drawableDownSword,drawableDisabledSword);
        sword.setTransform(true);
        sword.setSize(100,100);
        sword.setScale((float)1.6, (float)1);
        sword.setPosition(50,240);
        stage.addActor(sword);

        Texture textureUpWings = new Texture(Gdx.files.internal("textures/wings.png")); // Stato normale
        Texture textureDownWings = new Texture(Gdx.files.internal("textures/wings.png")); // Stato premuto (opzionale)
        Texture textureDisableWings = new Texture(Gdx.files.internal("textures/wings.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpWings= new TextureRegionDrawable(new TextureRegion(textureUpWings));
        Drawable drawableDownWings = new TextureRegionDrawable(new TextureRegion(textureDownWings));
        Drawable drawableDisabledWings = new TextureRegionDrawable(new TextureRegion(textureDisableWings));

        ImageButton wings  = new ImageButton(drawableUpWings,drawableDownWings,drawableDisabledWings);
        wings.setTransform(true);
        wings.setSize(100,100);
        wings.setScale((float)1.6, (float)1);
        wings.setPosition(650,240);
        stage.addActor(wings);

        Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BROWN);
        pixmap.fill();
        backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = backgroundDrawable; // Imposta lo sfondo per lo stato normale del pulsante
        textButtonStyle.down = backgroundDrawable;
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        TextButton saveButton = new TextButton("GO to Save ->", skin , "default");
        TextButton ChooseButton = new TextButton("Choose the Power Ups",skin,"default");
        TextButton atkButton = new TextButton("Health Points: " + mainCharacter.getHealthPoints(), skin, "default");
        TextButton healtButton = new TextButton("Attack: " + mainCharacter.getAttackPower(), skin, "default");
        TextButton speedButton = new TextButton("Speed: " + mainCharacter.getStandardSpeed(), skin, "default");

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        saveButton.setPosition(290, 70);
        saveButton.setSize(260,44);
        ChooseButton.setPosition(290,400);
        ChooseButton.setSize(260,44);
        atkButton.setSize(260,44);
        atkButton.setPosition(50,170);
        healtButton.setSize(260,44);
        healtButton.setPosition(50,170);
        speedButton.setSize(260,44);
        speedButton.setPosition(50,170);
        stage.addActor(saveButton);
        stage.addActor(ChooseButton);
        stage.addActor(atkButton);
        stage.addActor(speedButton);
        stage.addActor(healtButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        stage.dispose();
        backgroundImageTexture.dispose();
    }
}
