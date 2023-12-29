package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

import application.entities.Character;
import application.entities.Knight;
import application.gamelogic.InputManager;

public class LevelScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    private Character mainCharacter;

    private ArrayList<Character> characters;

    private TextureRegion currentFrame;

    InputManager inputManager;
    public LevelScreen(Game game, Character mainCharacter, ArrayList<Character> characters){
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.characters = characters;
    }

    /**
     * Initialize the variables necessary to execute the application
     */
    @Override
    public void show() {
        stateTime = 0;

        batch = new SpriteBatch();
        map = new TmxMapLoader().load(("mappe/Livello1/Level1.tmx"));
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        mainCharacter.doStopAndIdle();
        inputManager = new InputManager(mainCharacter,characters);
        Gdx.input.setInputProcessor(inputManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        camera.position.set(0,0,0);
        camera.zoom = 0.8f;
        camera.update();
        renderer.setView(camera);
        renderer.render();

        currentFrame = inputManager.nextFrame(stateTime);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(currentFrame,mainCharacter.getX(),mainCharacter.getY());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
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

    }
}
