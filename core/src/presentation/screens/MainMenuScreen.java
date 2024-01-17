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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


import presentation.music.MusicManager;

import java.sql.Wrapper;

import application.Save.SaveManager;
import application.Save.SaveWrapper;
import application.gamelogic.GameLoader;


public class MainMenuScreen implements Screen {
    private Stage stage;
    private Game game;
    private BitmapFont font;
    private Skin skin;
    private Texture backgroundImageTexture;
    private Texture mainLabel;
    private TextureRegionDrawable backgroundDrawable;
    private TextureRegionDrawable backgroundDrawable2;
    private MusicManager musicManager = MusicManager.getInstance();
    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(900, 480));
        Gdx.input.setInputProcessor(stage);

        backgroundImageTexture = new Texture(Gdx.files.internal("MenuImages/BackG.png"));
        Image backgroundImage = new Image(backgroundImageTexture);
        backgroundImage.setFillParent(true); // This will make the image fill the entire stage
        stage.addActor(backgroundImage);

        skin = new Skin();
        font = new BitmapFont(); // Create a default font. You can also load your own font here.
        font.getData().setScale(1.5f);
        skin.add("default", font);


        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BROWN); // Sostituisci con il tuo colore preferito
        pixmap.fill();
        Pixmap pixmap2 = new Pixmap(1,1,Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap2.fill();
        backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        backgroundDrawable2 = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = backgroundDrawable; // Imposta lo sfondo per lo stato normale del pulsante
        textButtonStyle.down = backgroundDrawable2;
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextButton playButton = new TextButton("New Game", skin,"default");
        TextButton loadGameButton = new TextButton("Load Game",skin,"default");
        TextButton exitGameButton = new TextButton("Exit Game",skin,"default");

        playButton.setSize(155,44);
        playButton.setPosition(350, 0 + playButton.getHeight() * 3);

        loadGameButton.setSize(155,44);
        loadGameButton.setPosition(350,playButton.getY() - loadGameButton.getHeight() - 10);

        exitGameButton.setSize(155, 44);
        exitGameButton.setPosition(350, loadGameButton.getY() - exitGameButton.getHeight() - 10);

        mainLabel = new Texture(Gdx.files.internal("MenuImages/LABEL.png"));
        Image mainLabelImg = new Image(mainLabel);
        mainLabelImg.setPosition(280,250);
        mainLabelImg.setSize(340,300);
        stage.addActor(mainLabelImg);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseCharacterScreen(game)); // Switch to the game screen
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveWrapper wrapper = SaveManager.loadStats();//Save and load the game from the last save
                GameLoader gl = new GameLoader(game, wrapper.getMainCharacter(), wrapper.getNumLevel());
                System.out.println(wrapper.getNumLevel());
                if(wrapper.getNumLevel() <= gl.getMaxNumLevel()){
                    gl.load();
                }
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit(); // Exit the game
            }
        });

        stage.addActor(exitGameButton);
        stage.addActor(playButton);
        stage.addActor(loadGameButton);

        musicManager.stopBattle();
        musicManager.playIntro();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        //table.invalidateHierarchy();
    }

    @Override
    public void pause() {
        // Handle pause if necessary
    }

    @Override
    public void resume() {
        // Handle resume if necessary
    }

    @Override
    public void hide() {
        // Dispose of resources when this screen is no longer shown
        //batch.dispose();
        //skin.dispose();
        //backgroundTexture.dispose();
    }

    @Override
    public void dispose() {
        // Dispose of all resources
        stage.dispose();
        stage.dispose();
        skin.dispose(); // Dispose of the skin
        font.dispose();
        backgroundDrawable.getRegion().getTexture().dispose();
        backgroundImageTexture.dispose();
        //batch.dispose();
        // skin.dispose();
        //backgroundTexture.dispose();
    }
}
