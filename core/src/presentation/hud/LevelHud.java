package presentation.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    private TextButton gemsCounter;

    private TextButton numLevel;

    private TextButton enemiesNum;
    public LevelHud(){
        skin = new Skin();
        font = new BitmapFont(); // Create a default font. You can also load your own font here.
        font.getData().setScale(4f);
        skin.add("default", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Texture heart = new Texture(Gdx.files.internal("textures/heart.png"));
        Drawable heartDrawable = new TextureRegionDrawable(new TextureRegion(heart));
        Texture diamond = new Texture(Gdx.files.internal("textures/diamondHud.png"));
        Drawable diamondDrawable = new TextureRegionDrawable(new TextureRegion(diamond));
        Texture enemies = new Texture(Gdx.files.internal("textures/skull.png"));
        Drawable enemiesDrawable = new TextureRegionDrawable(new TextureRegion(enemies));

        ImageButton heartButton = new ImageButton(heartDrawable);
        heartButton.setTransform(true);
        heartButton.setSize(90, 90);
        heartButton.setPosition(0 + 10f, Gdx.graphics.getHeight() - heartButton.getHeight() - 5);
        ImageButton diamondButton = new ImageButton(diamondDrawable);
        diamondButton.setTransform(true);
        diamondButton.setSize(90,90);
        diamondButton.setPosition(Gdx.graphics.getWidth() - diamondButton.getWidth() - 10,Gdx.graphics.getHeight() - heartButton.getHeight() - 5);
        ImageButton enemiesButton = new ImageButton(enemiesDrawable);
        enemiesButton.setTransform(true);
        enemiesButton.setSize(90,90);
        enemiesButton.setPosition(0 + 10f,Gdx.graphics.getHeight() - heartButton.getHeight() - enemiesButton.getHeight() - 5);


        healthPoints = new TextButton("", skin, "default");
        healthPoints.setSize(90, 90);
        healthPoints.setPosition(heartButton.getX() + healthPoints.getWidth(), heartButton.getY());
        gemsCounter = new TextButton("",skin,"default");
        gemsCounter.setSize(90,90);
        gemsCounter.setPosition(diamondButton.getX() - gemsCounter.getWidth(),diamondButton.getY());
        numLevel = new TextButton("",skin,"default");
        numLevel.setSize(90,90);
        numLevel.setPosition((Gdx.graphics.getWidth()/2) - (numLevel.getWidth()/2),Gdx.graphics.getHeight() - numLevel.getHeight() - 5);
        enemiesNum = new TextButton("",skin,"default");
        enemiesNum.setSize(90,90);
        enemiesNum.setPosition(enemiesButton.getX() + enemiesButton.getWidth(), enemiesButton.getY());




        this.addActor(heartButton);
        this.addActor(healthPoints);
        this.addActor(diamondButton);
        this.addActor(gemsCounter);
        this.addActor(numLevel);
        this.addActor(enemiesButton);
        this.addActor(enemiesNum);
    }

    public void setHealthPoints(int healthPoints) {
        Integer wrapperHealth = healthPoints;
        this.healthPoints.setText(wrapperHealth.toString());
    }
    public void setGemsCounter(int gemsCounter) {
        Integer wrapperGems = gemsCounter;
        this.gemsCounter.setText(wrapperGems.toString());
    }

    public void setNumLevel(String numLevel) {
        this.numLevel.setText(numLevel);
    }

    public void setEnemiesNum(int enemiesNum) {
        Integer wrapperEnemies = enemiesNum;
        this.enemiesNum.setText(wrapperEnemies.toString());
    }

    public void render(){
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.draw();
    }
}