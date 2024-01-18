package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import application.entities.Character;
import application.entities.Villain;
import application.gamelogic.GameLoader;
import presentation.music.MusicManager;

public class GameOverScreen implements Screen{

    private List<Villain> enemies = new ArrayList<>();
    private Game game;
    private Character mainCharacter;
    private int numLevel;
    Stage stage;
    Skin skin;
    private Texture backgroundImageTexture;
    BitmapFont font;

    TextureRegionDrawable backgroundDrawable;

    TextButton restartGame;

    TextButton backToMainMenu;

    GameLoader loader;

    MusicManager musicManager = MusicManager.getInstance();

    public GameOverScreen(List<Villain> enemies,Game game,Character mainCharacter, int numLevel){
        this.enemies = enemies;
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.numLevel = numLevel;
    }

    /**
     * Inizializza tutti i vari assets presenti e li posiziona sullo schermo, collegandogli i rispettivi ActionListeners
     */
    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        musicManager.playGameOver();

        backgroundImageTexture = new Texture(Gdx.files.internal("MenuImages/BackGameOver.png"));
        Image backgroundImage = new Image(backgroundImageTexture);
        backgroundImage.setFillParent(true); // This will make the image fill the entire stage
        stage.addActor(backgroundImage);

        skin = new Skin();
        font = new BitmapFont(); // Create a default font. You can also load your own font here.
        font.getData().setScale(2.4f);
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BROWN); // Sostituisci con il tuo colore preferito
        pixmap.fill();
        backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = backgroundDrawable; // Imposta lo sfondo per lo stato normale del pulsante
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        restartGame = new TextButton("Restart Game", skin,"default");
        backToMainMenu = new TextButton("Main Menu",skin,"default");

        restartGame.setSize(280,100);
        restartGame.setPosition((Gdx.graphics.getWidth() / 2f) - (restartGame.getWidth()/2f), 0 + restartGame.getHeight() * 3);

        backToMainMenu.setSize(280,100);
        backToMainMenu.setPosition(restartGame.getX(),backToMainMenu.getHeight() * 2 - 20);


        loader = new GameLoader(game,mainCharacter,numLevel);

        restartGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Restart");
                mainCharacter.setHealthPoints(mainCharacter.getMaxHealthPoints());
                mainCharacter.setAlive(true);
                musicManager.stopGameOver();
                loader.load();
            }
        });

        backToMainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ritorna");
                musicManager.stopGameOver();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(restartGame);
        stage.addActor(backToMainMenu);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renderizza la schermata di Game Over rendendola visibile
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
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
        musicManager.disposeGameOver();
    }
}
