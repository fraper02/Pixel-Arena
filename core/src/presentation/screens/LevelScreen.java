package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.PixelArenaGame;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import application.ai.Arch;
import application.ai.Node;
import application.ai.TilesGraph;
import application.entities.Character;
import application.entities.Gemma;
import application.entities.Knight;
import application.entities.Level;
import application.entities.Villain;
import application.gamelogic.GameLoader;
import application.gamelogic.InputManager;
import presentation.hud.LevelHud;
import presentation.music.AudioManager;
import presentation.music.MusicManager;
import presentation.ui.PauseMenu;

public class LevelScreen implements Screen {
    private PixelArenaGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    private Character mainCharacter;
    private TextureRegion currentFrame;
    private TextureRegion currentFrameVillain;
    private InputManager inputManager;
    private List<Villain> enemies;
    private ShapeRenderer shapeRenderer;
    private List<TilesGraph> graphs;
    private HashMap<TilesGraph,Villain> graphVillainHashMap = new HashMap<>();
    private Random random = new Random();
    private Level level;
    private MusicManager musicManager = MusicManager.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();
    private LevelHud hud;
    private PauseMenu pauseMenu;

    private GameLoader loader;
    public LevelScreen(Game game, Character mainCharacter, List<Villain> enemies, int numLevel){
        this.game = (PixelArenaGame) game;
        this.mainCharacter = mainCharacter;
        this.enemies = enemies;
        this.graphs = new ArrayList<>();
        this.level = new Level(numLevel);
    }

    /**
     * Inizializza le variabli necessarie per eseguire l`applicazione
     */
    @Override
    public void show() {
        stateTime = 0;
        batch = new SpriteBatch();

        renderer = new OrthogonalTiledMapRenderer(level.getMap());
        camera = new OrthographicCamera();

        mainCharacter.doStopAndIdle();
        inputManager = new InputManager(mainCharacter, graphVillainHashMap);
        hud = new LevelHud();
        pauseMenu = new PauseMenu(game);
        shapeRenderer = new ShapeRenderer();

        int i = 0;
        if(!enemies.isEmpty()) {
            for (TiledMapTileLayer layer : level.getLegitLayers()) {
                TilesGraph graph = new TilesGraph();
                for (int x = 0; x < layer.getWidth(); x++) {
                    for (int y = 0; y < layer.getHeight(); y++) {
                        TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                        if (cell != null) {
                            Node node = new Node(x, y);
                            graph.addTiles(node);
                        }
                    }
                }

                for (Node fromNode : graph.getTiles()) {
                    for (Node toNode : graph.getTiles()) {
                        if (!fromNode.equals(toNode)) {
                            graph.connectAdjacentNodes(fromNode, toNode);
                        }
                    }
                }
                graphs.add(graph);
                enemies.get(i).setTilesGraph(graph);
                graphVillainHashMap.put(graph, enemies.get(i));
                enemies.get(i).doStopAndIdle();
                enemies.get(i).setPosition((graph.getTiles().get(0).getX() * 16 + 8) - 32, (graph.getTiles().get(0).getY() * 16 + 8) - 24);
                enemies.get(i).setNearNode(graph.getTiles().get(0));
                enemies.get(i).setPath(graph.findPath(graph.getTiles().get(0), graph.getTiles().get(random.nextInt(0, graph.getTiles().size()))));
                i++;
            }
        }


        mainCharacter.setPosition(level.getStartPoint().getX(),  level.getStartPoint().getY());
        musicManager.stopIntro();
        musicManager.playBattle();

        hud.setHealthPoints(mainCharacter.getHealthPoints());
        hud.setGemsCounter(mainCharacter.getNumGemme());
        hud.setNumLevel("Level: " + level.getNumLivello());
        hud.setEnemiesNum(enemies.size());
        Gdx.input.setInputProcessor(inputManager);

    }

    /**
     * Aggiorna costantemente il frame corrente che permette l`aggiornamento visivo del gioco
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!mainCharacter.isAlive()) {
            musicManager.stopBattle();
            this.game.setScreen(new GameOverScreen(enemies, game, mainCharacter, level.getNumLivello()));
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                game.togglePause();

            }
            if (!game.isPaused()) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                Gdx.input.setInputProcessor(inputManager);

                this.update();
                stateTime += delta;


                camera.position.set(mainCharacter.getX() + 32, mainCharacter.getY() + 32, 0);
                camera.zoom = 0.3f;
                camera.update();
                renderer.setView(camera);
                renderer.getBatch().begin();
                for (MapLayer layer : level.getNormalLayer()) {
                    renderer.renderTileLayer((TiledMapTileLayer) layer);
                }
                renderer.getBatch().end();
                this.update();

                currentFrame = inputManager.nextFrame(stateTime);


                if (enemies.isEmpty()) {
                    renderer.getBatch().begin();
                    for (MapLayer layer : level.getEndTiles()) {
                        renderer.renderTileLayer((TiledMapTileLayer) layer);
                    }
                    renderer.getBatch().end();
                    if (mainCharacter.getMovementBox().overlaps(level.getEndLevel())) {
                        mainCharacter.setHealthPoints(mainCharacter.getMaxHealthPoints());
                        this.game.setScreen(new UpgradeScreen(this.game, mainCharacter, level.getNumLivello()));
                    }
                } else if (mainCharacter.getMovementBox().overlaps(level.getEndLevel())) {
                    mainCharacter.setPosition(mainCharacter.getPreviousX(), mainCharacter.getPreviousY());
                }
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                for (Gemma g : level.getGems()) {
                    batch.draw(g.getTexture(), g.getHitBox().x, g.getHitBox().y);
                }
                for (Villain v : enemies) {
                    if (v.getY() >= mainCharacter.getY()) {
                        currentFrameVillain = v.getNextStep(stateTime, mainCharacter);
                        batch.draw(currentFrameVillain, v.getX(), v.getY());
                    }
                }
                batch.draw(currentFrame, mainCharacter.getX(), mainCharacter.getY());
                for (Villain v : enemies) {
                    if (v.getY() < mainCharacter.getY()) {
                        currentFrameVillain = v.getNextStep(stateTime, mainCharacter);
                        batch.draw(currentFrameVillain, v.getX(), v.getY());
                    }
                }
                batch.end();
                renderer.getBatch().begin();
                renderer.renderTileLayer(level.getLeavesLayer());
                renderer.renderTileLayer(level.getBridgeLayer());
                renderer.getBatch().end();
                hud.render();
            } else {
                Gdx.input.setInputProcessor(pauseMenu);
                pauseMenu.render();
            }
        }
    }


    /**
     * Viene richiamato ogni volta che cambia la finestra, e aggiorna il Viewport della camera con le nuove dimensioni della finestra
     * @param width Larghezza della finestra
     * @param height Altezza della finestra
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    /**
     * Metodo che aggiorna lo stato del gioco chiamando differenti metodi di check
     */
    public void update(){
        hud.setHealthPoints(mainCharacter.getHealthPoints());
        hud.setGemsCounter(mainCharacter.getNumGemme());
        hud.setEnemiesNum(enemies.size());
        this.updateAI();
        this.mapCollision();
        this.charactersCollision();
        this.checkHealingBases();
        this.checkGems();
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

    /**
     * Controlla se c`e` stata una collisione tra il main character e i bordi della mappa, e tra i nemici e i bordi della mappa
     */
    private void mapCollision(){
        for(Rectangle rec: level.getCollisionObjects()){
            if(rec.overlaps(mainCharacter.getMovementBox())){
                mainCharacter.setPosition(mainCharacter.getPreviousX(), mainCharacter.getPreviousY());
                break;
            }
        }
        for(Rectangle rec: level.getCollisionObjects()){
            for(Character v: enemies){
                if (rec.overlaps(v.getMovementBox())){
                    v.setPosition(((v.getNearNode().getX() * 16) + 8 - 32),(v.getNearNode().getY() * 16) + 8 - 24);
                    break;
                }
            }
        }
    }

    /**
     * Controlla se c`e` stata una collisione tra personaggi
     */
    private void charactersCollision(){
        for(Character v : enemies){
            mainCharacter.collisionCheck(v);
            v.collisionCheck(mainCharacter);
        }
    }

    /**
     * Controlla se il main character e` sopra la base di ricarica della vita, se si` chiama il metodo di cura
     */
    private void checkHealingBases(){
        for(Rectangle rec: level.getHealingBases()){
            if(rec.overlaps(mainCharacter.getMovementBox())){
                mainCharacter.doHeal(stateTime);
                break;
            }
        }
    }

    /**
     * Questo metodo aggiorna tutte le variabili che riguardano la parte dei grafi dell`AI e i nodi associati al main character e ai nemici:
     * Inoltre aggiorna i cammini per ogni nemico
     */
    private void updateAI(){
        for(TilesGraph g: graphs) {
            Villain v = graphVillainHashMap.get(g);
            if (!v.isAlive()) {
                enemies.remove(v);
            } else {
                for (Node n : g.getTiles()) {
                    if ((Vector2.dst(mainCharacter.getX() + 32, mainCharacter.getY() + 24, mainCharacter.getNearNode().getX() * 16 + 8, mainCharacter.getNearNode().getY() * 16 + 8) >
                            Vector2.dst(mainCharacter.getX() + 32, mainCharacter.getY() + 24, n.getX() * 16 + 8, n.getY() * 16 + 8))) {
                        mainCharacter.setNearNode(n);
                        mainCharacter.setTilesGraph(g);
                    }
                    if (Vector2.dst(v.getX() + 32, v.getY() + 24, v.getNearNode().getX() * 16 + 8, v.getNearNode().getY() * 16 + 8) >
                            Vector2.dst(v.getX() + 32, v.getY() + 24, n.getX() * 16 + 8, n.getY() * 16 + 8)) {
                        v.setNearNode(n);
                    }
                }
                if ((Vector2.dst(mainCharacter.getX() + 32, mainCharacter.getY() + 24, mainCharacter.getNearNode().getX() * 16 + 8, mainCharacter.getNearNode().getY() * 16 + 8)) > 40) {
                    Knight k = (Knight) mainCharacter;
                    mainCharacter.setNearNode(k.getLoneNode());
                    mainCharacter.setTilesGraph(null);
                }
                if (g.hasNode(mainCharacter.getNearNode())) {
                    v.setPath(g.findPath(v.getNearNode(), mainCharacter.getNearNode()));
                } else {
                    if (!v.isWalking()) {
                        v.setRandDestination(random.nextInt(0, g.getTiles().size() - 1));
                        v.setPath(g.findPath(v.getNearNode(), g.getTiles().get(v.getRandDestination())));
                    } else {
                        v.setPath(g.findPath(v.getNearNode(), g.getTiles().get(v.getRandDestination())));
                    }
                }
            }
        }
    }

    /**
     * Controlla se il giocatore ha raccolto una gemma
     */
    private void checkGems(){
        for(int gem = 0; gem < level.getGems().size(); gem++){
            if(mainCharacter.getMovementBox().overlaps(level.getGems().get(gem).getHitBox())){
                audioManager.playGemPickup();
                mainCharacter.setNumGemme(mainCharacter.getNumGemme() + 1);
                level.getGems().remove(gem);
            }
        }
    }
}
