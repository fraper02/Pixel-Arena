package presentation.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import application.gamelogic.InputManager;
import presentation.music.MusicManager;

public class LevelScreen implements Screen {
    private Game game;
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
    private List<GraphPath<Node>> paths;
    private HashMap<TilesGraph,Villain> graphVillainHashMap = new HashMap<>();
    private Random random = new Random();
    private Level level;
    private MusicManager musicManager = MusicManager.getInstance();
    public LevelScreen(Game game, Character mainCharacter, List<Villain> enemies, int numLevel){
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.enemies = enemies;
        this.graphs = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.level = new Level(numLevel);
    }

    /**
     * Initialize the variables necessary to execute the application
     */
    @Override
    public void show() {
        stateTime = 0;
        batch = new SpriteBatch();

        renderer = new OrthogonalTiledMapRenderer(level.getMap());
        camera = new OrthographicCamera();

        mainCharacter.doStopAndIdle();
        inputManager = new InputManager(mainCharacter, graphVillainHashMap);

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

        Gdx.input.setInputProcessor(inputManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!mainCharacter.isAlive()){
            mainCharacter.setHealthPoints(mainCharacter.getMaxHealthPoints());
            this.game.setScreen(new LevelScreen(this.game, new Knight(0, 0), enemies,1));
        }

        this.update();
        stateTime += delta;


        camera.position.set(mainCharacter.getX() + 32, mainCharacter.getY() + 32,0);
        camera.zoom = 0.6f;
        camera.update();
        renderer.setView(camera);
        renderer.getBatch().begin();
        for(MapLayer layer : level.getNormalLayer()){
            renderer.renderTileLayer((TiledMapTileLayer) layer);
        }
        renderer.getBatch().end();
        this.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for(Character v: enemies){
            Villain v1 = (Villain) v;
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(v1.getActionArea().getX(),v1.getActionArea().getY(), v1.getActionArea().getWidth(), v1.getActionArea().getHeight());
            for(Node node: v1.getTilesGraph().getTiles()){

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(node.getX() * 16 + 8, node.getY() * 16 + 8, 8);
            }
            for(Node node: v1.getCurrentPath()){

                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.circle(node.getX() * 16 + 8, node.getY() * 16 + 8, 8);
            }
            shapeRenderer.setColor(Color.BLUE);

            for(Arch a: v1.getTilesGraph().getConnectionsLines()){

                a.render(shapeRenderer);
            }
        }
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(mainCharacter.getNearNode().getX()*16+8,mainCharacter.getNearNode().getY()*16+8,8);
        shapeRenderer.end();

        currentFrame = inputManager.nextFrame(stateTime);


        if(enemies.isEmpty()){
            renderer.getBatch().begin();
            for(MapLayer layer : level.getEndTiles()){
                renderer.renderTileLayer((TiledMapTileLayer) layer);
            }
            renderer.getBatch().end();
            if(mainCharacter.getMovementBox().overlaps(level.getEndLevel())){
                this.game.setScreen(new UpgradeScreen(this.game, mainCharacter,level.getNumLivello()));
            }
        }else if(mainCharacter.getMovementBox().overlaps(level.getEndLevel())){
            mainCharacter.setPosition(mainCharacter.getPreviousX(), mainCharacter.getPreviousY());
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Gemma g: level.getGems()){
            batch.draw(g.getTexture(), g.getHitBox().x, g.getHitBox().y);
        }
        for(Villain v : enemies){
            if(v.getY() >= mainCharacter.getY()){
                currentFrameVillain = v.getNextStep(stateTime,mainCharacter);
                batch.draw(currentFrameVillain, v.getX(), v.getY());
            }
        }
        batch.draw(currentFrame,mainCharacter.getX(),mainCharacter.getY());
        for(Villain v : enemies){
            if(v.getY() < mainCharacter.getY()){
                currentFrameVillain = v.getNextStep(stateTime,mainCharacter);
                batch.draw(currentFrameVillain, v.getX(), v.getY());
            }
        }
        batch.end();
        renderer.getBatch().begin();
        renderer.renderTileLayer(level.getLeavesLayer());
        renderer.renderTileLayer(level.getBridgeLayer());
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    /**
     * Method that updates the status of the game calling different check methods
     */
    public void update(){
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
     * Check if there was a collision between the main character and the map borders
     * and the enemies and the map border
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
     * check if there was a collision between characters
     */
    private void charactersCollision(){
        for(Character v : enemies){
            mainCharacter.collisionCheck(v);
            v.collisionCheck(mainCharacter);
        }
    }

    /**
     * Check if the main character is stepping on a healing base
     * if so then it calls the healing method
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
     * This method updates all the variable regarding the Ai graphs and the nodes associated to
     * the character and the enemies and updates the path for every enemy
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
     * Check if the player picked up a gem
     */
    private void checkGems(){
        for(int gem = 0; gem < level.getGems().size(); gem++){
            if(mainCharacter.getMovementBox().overlaps(level.getGems().get(gem).getHitBox())){
                mainCharacter.setNumGemme(mainCharacter.getNumGemme() + 1);
                level.getGems().remove(gem);
            }
        }
    }
}
