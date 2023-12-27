package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    public LevelScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        stateTime = 0;

        batch = new SpriteBatch();
        map = new TmxMapLoader().load(("mappe/Livello1/Level1.tmx"));
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
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
