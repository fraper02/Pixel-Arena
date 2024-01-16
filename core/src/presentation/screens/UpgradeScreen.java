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


        Texture textureUp = new Texture(Gdx.files.internal("textures/heart.png")); // Stato normale
        Texture textureDown = new Texture(Gdx.files.internal("textures/heart.png")); // Stato premuto (opzionale)
        Texture textureDisabled = new Texture(Gdx.files.internal("textures/heart.png")); // Stato disabilitato (opzionale)

        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(textureUp));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(textureDown));
        Drawable drawableDisabled = new TextureRegionDrawable(new TextureRegion(textureDisabled));

        ImageButton buttonKnight = new ImageButton(drawableUp, drawableDown, drawableDisabled);
        buttonKnight.setTransform(true);
        buttonKnight.setSize(100,100);
        buttonKnight.setScale((float)1.6, (float)1);
        buttonKnight.setPosition(223,170);
        stage.addActor(buttonKnight);

        Texture textureUpArcher = new Texture(Gdx.files.internal("textures/sword.png")); // Stato normale
        Texture textureDownArcher = new Texture(Gdx.files.internal("textures/sword.png")); // Stato premuto (opzionale)
        Texture textureDisabledArcher = new Texture(Gdx.files.internal("textures/sword.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpArcher = new TextureRegionDrawable(new TextureRegion(textureUpArcher));
        Drawable drawableDownArcher = new TextureRegionDrawable(new TextureRegion(textureDownArcher));
        Drawable drawableDisabledArcher = new TextureRegionDrawable(new TextureRegion(textureDisabledArcher));

        buttonKnight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        ImageButton buttonArcher  = new ImageButton(drawableUpArcher,drawableDownArcher,drawableDisabledArcher);
        buttonArcher.setTransform(true);
        buttonArcher.setSize(100,100);
        buttonArcher.setScale((float)1.6, (float)1);
        buttonArcher.setPosition(23,170);
        stage.addActor(buttonArcher);

        Texture textureUpWizard = new Texture(Gdx.files.internal("textures/wings.png")); // Stato normale
        Texture textureDownWizard = new Texture(Gdx.files.internal("textures/wings.png")); // Stato premuto (opzionale)
        Texture textureDisableWizard = new Texture(Gdx.files.internal("textures/wings.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpWizard = new TextureRegionDrawable(new TextureRegion(textureUpWizard));
        Drawable drawableDownWizard = new TextureRegionDrawable(new TextureRegion(textureDownWizard));
        Drawable drawableDisabledWizard = new TextureRegionDrawable(new TextureRegion(textureDisableWizard));

        ImageButton buttonWizard  = new ImageButton(drawableUpWizard,drawableDownWizard,drawableDisabledWizard);
        buttonWizard.setTransform(true);
        buttonWizard.setSize(100,100);
        buttonWizard.setScale((float)1.6, (float)1);
        buttonWizard.setPosition(423,170);
        stage.addActor(buttonWizard);

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
        TextButton playButton = new TextButton("GO to Save ->", skin , "default");
        TextButton ChooseButton = new TextButton("Choose the Power Ups",skin,"default");

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                List<Villain> enemies = new ArrayList<>();
                enemies.add(new Villain(0,0));
                enemies.add(new Villain(0,0));
                enemies.add(new Villain(0,0));
                //game.setScreen(new LevelScreen(game,character,enemies,1)); // Switch to the game screen
            }
        });

        playButton.setPosition(290, 70);
        playButton.setSize(260,44);
        ChooseButton.setPosition(290,400);
        ChooseButton.setSize(260,44);
        stage.addActor(playButton);
        stage.addActor(ChooseButton);
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
