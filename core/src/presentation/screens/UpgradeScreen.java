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
import application.entities.Character;
import application.gamelogic.GameLoader;
import presentation.music.MusicManager;

public class UpgradeScreen implements Screen {

    private Game game;
    private Character mainCharacter;
    private int numLevel;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Texture backgroundImageTexture;
    private TextureRegionDrawable backgroundDrawable;
    private TextButton atkButton;
    private TextButton healthButton;
    private TextButton speedButton;
    private TextButton numGems;
    private MusicManager musicManager = MusicManager.getInstance();

    public UpgradeScreen(Game game, Character mainCharacter, int numLevel) {
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.numLevel = numLevel;
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
        font.getData().setScale(1f);
        skin.add("default", font);

        backgroundImageTexture = new Texture(Gdx.files.internal("MenuImages/BackG.png"));
        Image backgroundImage = new Image(backgroundImageTexture);
        backgroundImage.setFillParent(true); // This will make the image fill the entire stage
        stage.addActor(backgroundImage);


        Texture textureUpHeart = new Texture(Gdx.files.internal("MenuImages/heart.png")); // Stato normale
        Texture textureDownHeart = new Texture(Gdx.files.internal("MenuImages/heart.png")); // Stato premuto (opzionale)
        Texture textureDisabledHeart = new Texture(Gdx.files.internal("MenuImages/heart.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpHeart = new TextureRegionDrawable(new TextureRegion(textureUpHeart));
        Drawable drawableDownHeart = new TextureRegionDrawable(new TextureRegion(textureDownHeart));
        Drawable drawableDisabledHeart = new TextureRegionDrawable(new TextureRegion(textureDisabledHeart));

        ImageButton heart = new ImageButton(drawableUpHeart, drawableDownHeart, drawableDisabledHeart);
        heart.setTransform(true);
        heart.setSize(100,100);
        heart.setScale((float)1.6, (float)1.6);
        heart.setPosition(350,220);
        heart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(mainCharacter.getNumGemme() > 0){
                    mainCharacter.setNumGemme(mainCharacter.getNumGemme() - 1);
                    mainCharacter.setMaxHealthPoints(mainCharacter.getMaxHealthPoints() + 30);
                }
            }
        });
        stage.addActor(heart);

        Texture textureUpSword = new Texture(Gdx.files.internal("MenuImages/sword.png")); // Stato normale
        Texture textureDownSword = new Texture(Gdx.files.internal("MenuImages/sword.png")); // Stato premuto (opzionale)
        Texture textureDisabledSword = new Texture(Gdx.files.internal("MenuImages/sword.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpSword = new TextureRegionDrawable(new TextureRegion(textureUpSword));
        Drawable drawableDownSword = new TextureRegionDrawable(new TextureRegion(textureDownSword));
        Drawable drawableDisabledSword = new TextureRegionDrawable(new TextureRegion(textureDisabledSword));

        ImageButton sword  = new ImageButton(drawableUpSword,drawableDownSword,drawableDisabledSword);
        sword.setTransform(true);
        sword.setSize(100,100);
        sword.setScale((float)1.6, (float)1.6);
        sword.setPosition(50,220);
        sword.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(mainCharacter.getNumGemme() > 0){
                    mainCharacter.setNumGemme(mainCharacter.getNumGemme() - 1);
                    mainCharacter.setAttackPower(mainCharacter.getAttackPower() + 10);
                }
            }
        });
        stage.addActor(sword);

        Texture textureUpWings = new Texture(Gdx.files.internal("MenuImages/wings.png")); // Stato normale
        Texture textureDownWings = new Texture(Gdx.files.internal("MenuImages/wings.png")); // Stato premuto (opzionale)
        Texture textureDisableWings = new Texture(Gdx.files.internal("MenuImages/wings.png")); // Stato disabilitato (opzionale)

        Drawable drawableUpWings= new TextureRegionDrawable(new TextureRegion(textureUpWings));
        Drawable drawableDownWings = new TextureRegionDrawable(new TextureRegion(textureDownWings));
        Drawable drawableDisabledWings = new TextureRegionDrawable(new TextureRegion(textureDisableWings));

        ImageButton wings  = new ImageButton(drawableUpWings,drawableDownWings,drawableDisabledWings);
        wings.setTransform(true);
        wings.setSize(100,100);
        wings.setScale((float)1.6, (float)1.6);
        wings.setPosition(650,220);
        wings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(mainCharacter.getNumGemme() > 0){
                    mainCharacter.setNumGemme(mainCharacter.getNumGemme() - 1);
                    mainCharacter.setSpeed(mainCharacter.getStandardSpeed() + 0.3f);
                }
            }
        });
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
        TextButton chooseButton = new TextButton("Choose the Power Ups",skin,"default");
        TextButton saveAndContinueButton = new TextButton("Save and continue ->", skin , "default");
        saveAndContinueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveManager.saveStats(mainCharacter, numLevel +1);
                GameLoader gl = new GameLoader(game, mainCharacter, numLevel + 1);
                if(numLevel + 1 <= gl.getMaxNumLevel()){
                    gl.load();
                }
            }
        });
        TextButton saveAndExit = new TextButton("Save and exit", skin , "default");
        saveAndExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveManager.saveStats(mainCharacter, numLevel +1);
                musicManager.stopBattle();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        atkButton = new TextButton("Attack: " + mainCharacter.getAttackPower(), skin, "default");
        healthButton = new TextButton("Health Points: " + mainCharacter.getMaxHealthPoints(), skin, "default");
        speedButton = new TextButton("Speed: " + mainCharacter.getStandardSpeed(), skin, "default");
        numGems = new TextButton("x" + mainCharacter.getNumGemme(), skin, "default");

        numGems.setSize(75, 50);
        numGems.setPosition(stage.getWidth() - numGems.getWidth(), stage.getHeight() - numGems.getHeight());
        Texture textureGem = new Texture(Gdx.files.internal("textures/diamond.png"));
        Drawable drawableGem = new TextureRegionDrawable(textureGem);
        ImageButton gem = new ImageButton(drawableGem);
        gem.setTransform(true);
        gem.setSize(50, 50);
        gem.setPosition(stage.getWidth() - numGems.getWidth()/2 - gem.getWidth(), stage.getHeight() - gem.getHeight());

        saveAndContinueButton.setPosition(290, 70);
        saveAndContinueButton.setSize(260,44);
        saveAndExit.setPosition(290, saveAndContinueButton.getY() - saveAndContinueButton.getHeight() - 10f);
        saveAndExit.setSize(260, 44);
        chooseButton.setPosition(290,400);
        chooseButton.setSize(260,44);
        atkButton.setSize(150,20);
        atkButton.setPosition(50,170);
        healthButton.setSize(150,20);
        healthButton.setPosition(350,170);
        speedButton.setSize(150,20);
        speedButton.setPosition(650,170);
        stage.addActor(saveAndContinueButton);
        stage.addActor(saveAndExit);
        stage.addActor(chooseButton);
        stage.addActor(atkButton);
        stage.addActor(healthButton);
        stage.addActor(speedButton);
        stage.addActor(numGems);
        stage.addActor(gem);
    }

    /**
     * Renderizza il menu di scelta del personaggio rendendolo visibile
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        this.update();
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

    /**
     * Aggiorna i vari parametri presenti a schermo
     */
    public void update(){
        atkButton.setText("Attack: " + mainCharacter.getAttackPower());
        healthButton.setText("Health Points: " + mainCharacter.getMaxHealthPoints());
        speedButton.setText("Speed: " + mainCharacter.getStandardSpeed());
        numGems.setText("x" + mainCharacter.getNumGemme());
    }
}
