package presentation.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.PixelArenaGame;

import org.ietf.jgss.GSSContext;

import presentation.screens.MainMenuScreen;

public class PauseMenu extends Stage {
    private Skin skin;
    private BitmapFont font;
    private PixelArenaGame pixelArenaGame;
    public PauseMenu(PixelArenaGame game){
        this.pixelArenaGame = game;
        skin = new Skin();
        font = new BitmapFont();
        font.getData().setScale(4f);
        skin.add("default", font);

        /*Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BROWN);
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();*/

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        //textButtonStyle.up = backgroundDrawable;
        skin.add("default", textButtonStyle);

        TextButton pauseTitle = new TextButton("GAME PAUSED", skin, "default");
        pauseTitle.setSize(200, 100);
        pauseTitle.setPosition((Gdx.graphics.getWidth() / 2f) - (pauseTitle.getWidth()/2), Gdx.graphics.getHeight()/2f + pauseTitle.getHeight());

        TextButton resumeButton = new TextButton("Resume", skin, "default");
        resumeButton.setSize(100, 50);
        resumeButton.setPosition((Gdx.graphics.getWidth() / 2f) - (resumeButton.getWidth()/2), pauseTitle.getY() - resumeButton.getHeight() - 200);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pixelArenaGame.togglePause();
            }
        });

        TextButton mainMenuButton = new TextButton("Exit to menu", skin, "default");
        mainMenuButton.setSize(resumeButton.getWidth(), resumeButton.getHeight());
        mainMenuButton.setPosition(resumeButton.getX(), resumeButton.getY() - mainMenuButton.getHeight() - 50);
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pixelArenaGame.togglePause();
                pixelArenaGame.setScreen(new MainMenuScreen(pixelArenaGame));
            }
        });
        this.addActor(pauseTitle);
        this.addActor(resumeButton);
        this.addActor(mainMenuButton);
    }
    public void render(){
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.draw();
    }
}
