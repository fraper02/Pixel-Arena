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

import application.Save.SaveManager;
import application.entities.Knight;
import application.entities.Villain;
import application.entities.Character;
import application.gamelogic.GameLoader;

import java.util.ArrayList;
import java.util.List;

public class ChooseCharacterScreen implements Screen {

    private Game game;
    private Texture backgroundImageTexture;
    private Stage stage;
    private BitmapFont font;
    private TextureRegionDrawable backgroundDrawable;
    private Skin skin;
    private Knight k1 = new Knight(0,0);
    private Character character = k1;


    public ChooseCharacterScreen(Game game) {
        this.game = game;
    }

    /**
     * Inizializza tutti i vari assets presenti e li posiziona sullo schermo, collegandogli i rispettivi ActionListeners
     */
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


        Texture textureUp = new Texture(Gdx.files.internal("MenuImages/ATM.png")); // Stato normale
        Texture textureDown = new Texture(Gdx.files.internal("MenuImages/ATM.png")); // Stato premuto (opzionale)
        Texture textureDisabled = new Texture(Gdx.files.internal("MenuIMages/ATM.png")); // Stato disabilitato (opzionale)

        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(textureUp));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(textureDown));
        Drawable drawableDisabled = new TextureRegionDrawable(new TextureRegion(textureDisabled));

        ImageButton buttonKnight = new ImageButton(drawableUp, drawableDown, drawableDisabled);
        buttonKnight.setTransform(true);
        buttonKnight.setSize(225,200);
        buttonKnight.setScale((float)1.6, (float)1);
        buttonKnight.setPosition(265,170);
        stage.addActor(buttonKnight);

        Texture textureUpArcher = new Texture(Gdx.files.internal("MenuImages/ArcPoster.png")); // Stato normale
        Texture textureDownArcher = new Texture(Gdx.files.internal("MenuImages/ArcPoster.png")); // Stato premuto (opzionale)
        Texture textureDisabledArcher = new Texture(Gdx.files.internal("MenuImages/ArcPoster.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpArcher = new TextureRegionDrawable(new TextureRegion(textureUpArcher));
        Drawable drawableDownArcher = new TextureRegionDrawable(new TextureRegion(textureDownArcher));
        Drawable drawableDisabledArcher = new TextureRegionDrawable(new TextureRegion(textureDisabledArcher));

        ImageButton buttonArcher  = new ImageButton(drawableUpArcher,drawableDownArcher,drawableDisabledArcher);
        buttonArcher.setTransform(true);
        buttonArcher.setSize(245,200);
        buttonArcher.setScale((float)1.6, (float)1);
        buttonArcher.setPosition(23,170);
        stage.addActor(buttonArcher);

        Texture textureUpWizard = new Texture(Gdx.files.internal("MenuImages/WizPosterOff.png")); // Stato normale
        Texture textureDownWizard = new Texture(Gdx.files.internal("MenuImages/WizPosterOff.png")); // Stato premuto (opzionale)
        Texture textureDisableWizard = new Texture(Gdx.files.internal("MenuImages/WizPosterOff.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpWizard = new TextureRegionDrawable(new TextureRegion(textureUpWizard));
        Drawable drawableDownWizard = new TextureRegionDrawable(new TextureRegion(textureDownWizard));
        Drawable drawableDisabledWizard = new TextureRegionDrawable(new TextureRegion(textureDisableWizard));

        ImageButton buttonWizard  = new ImageButton(drawableUpWizard,drawableDownWizard,drawableDisabledWizard);
        buttonWizard.setTransform(true);
        buttonWizard.setSize(225,200);
        buttonWizard.setScale((float)1.6, (float)1);
        buttonWizard.setPosition(500,170);
        stage.addActor(buttonWizard);

        Texture backArrow = new Texture(Gdx.files.internal("MenuImages/freccia.png")); // Stato normale
        Drawable backArrowDrawable = new TextureRegionDrawable(new TextureRegion(backArrow));
        ImageButton buttonArrow = new ImageButton(backArrowDrawable);
        buttonArrow.setTransform(true);
        buttonArrow.setSize(70,70);
        buttonArrow.setPosition(0, stage.getHeight() - buttonArrow.getHeight());
        buttonArrow.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(buttonArrow);

        Texture myTexture = new Texture(Gdx.files.internal("MenuImages/Bordo.png"));
        Image bordo = new Image(myTexture);
        bordo.setSize(190,210);
        bordo.setPosition(348,165);
        stage.addActor(bordo);

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
        TextButton playButton = new TextButton("GO in the Pixel Arena ->", skin , "default");
        TextButton ChooseButton = new TextButton("Choose the Character",skin,"default");

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveManager.saveStats(character, 1);
                GameLoader gl = new GameLoader(game, character, 1);
                gl.load();// Switch to the game screen
            }
        });

        playButton.setPosition(290, 70);
        playButton.setSize(260,44);
        ChooseButton.setPosition(290,400);
        ChooseButton.setSize(260,44);
        stage.addActor(playButton);
        stage.addActor(ChooseButton);
    }

    /**
     * Renderizza il menu di scelta del personaggio rendendolo visibile
     * @param delta The time in seconds since the last render.
     */
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
