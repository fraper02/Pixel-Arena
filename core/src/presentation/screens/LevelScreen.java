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
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import application.ai.Arch;
import application.ai.Node;
import application.ai.TilesGraph;
import application.entities.Character;
import application.entities.Knight;
import application.entities.Villain;
import application.gamelogic.InputManager;

public class LevelScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private List<Rectangle> collisionObjects = new ArrayList<>();
    private List<Rectangle> healingBases = new ArrayList<>();
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    private Character mainCharacter;
    private TextureRegion currentFrame;

    private TextureRegion currentFrameVillain;
    InputManager inputManager;
    private List<Villain> enemies;

    TiledMapTileLayer legitLayer;
    TiledMapTileLayer legitLayer2;
    TiledMapTileLayer legitLayer3;

    ShapeRenderer shapeRenderer;

    MapLayers normalLayer;
    TiledMapTileLayer leavesLayer;
    TiledMapTileLayer bridgeLayer;

    List<TiledMapTileLayer> legitLayers;

    List<TilesGraph> graphs;

    List<GraphPath<Node>> paths;
    private HashMap<TilesGraph,Villain> graphVillainHashMap = new HashMap<>();
    private Random random = new Random();
    public LevelScreen(Game game, Character mainCharacter, List<Villain> enemies){
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.enemies = enemies;
        this.legitLayers = new ArrayList<>();
        this.graphs = new ArrayList<>();
        this.paths = new ArrayList<>();

    }

    /**
     * Initialize the variables necessary to execute the application
     */
    @Override
    public void show() {
        stateTime = 0;
        batch = new SpriteBatch();

        TiledMap map = new TmxMapLoader().load(("mappe/Livello1/Level1.tmx"));
        MapLayer collisionLayer = map.getLayers().get("Object Layer 1");
        MapLayer healingLayer = map.getLayers().get("HealingBases");
        for (MapObject object : collisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                collisionObjects.add(rect);
            }
        }
        for (MapObject object : healingLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                healingBases.add(rect);
            }
        }
        MapGroupLayer groupLayer = (MapGroupLayer) map.getLayers().get("NormalLayer");
        normalLayer = groupLayer.getLayers();
        leavesLayer = (TiledMapTileLayer) map.getLayers().get("tree&leavesWalkable");
        bridgeLayer = (TiledMapTileLayer) map.getLayers().get("Ponte");

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        mainCharacter.doStopAndIdle();
        inputManager = new InputManager(mainCharacter,enemies);

        shapeRenderer = new ShapeRenderer();
        legitLayers.add((TiledMapTileLayer) map.getLayers().get("nodesLayer"));
        legitLayers.add((TiledMapTileLayer) map.getLayers().get("nodesLayer2"));
        legitLayers.add((TiledMapTileLayer) map.getLayers().get("nodesLayer3"));

        int i = 0;
        for(TiledMapTileLayer layer : legitLayers){
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
            enemies.get(i).setGraph(graph);
            graphVillainHashMap.put(graph,enemies.get(i));
            i++;
        }


        mainCharacter.setPosition((graphs.get(0).getTiles().get(0).getX() * 16 + 8) - 32,  (graphs.get(0).getTiles().get(0).getY() * 16 + 8) - 24);
        mainCharacter.setNearNode(graphs.get(0).getTiles().get(0));

        enemies.get(0).setPosition((graphs.get(0).getTiles().get(4).getX() * 16 + 8) - 32,(graphs.get(0).getTiles().get(4).getY() * 16 + 8) - 24);
        enemies.get(1).setPosition((graphs.get(1).getTiles().get(26).getX() * 16 + 8) - 32,(graphs.get(1).getTiles().get(26).getY() * 16 + 8) - 24);
        enemies.get(2).setPosition((graphs.get(2).getTiles().get(43).getX() * 16 + 8) - 32,(graphs.get(2).getTiles().get(43).getY() * 16 + 8) - 24);
        //*enemies.get(3).setPosition((nodes.get(65).getX() * 16 + 8) - 32,(nodes.get(65).getY() * 16 + 8) - 24);

        enemies.get(0).setNearNode(graphs.get(0).getTiles().get(4));
        enemies.get(1).setNearNode(graphs.get(1).getTiles().get(26));
        enemies.get(2).setNearNode(graphs.get(2).getTiles().get(43));
        //enemies.get(3).setNearNode(graph.getTiles().get(65));*/


        Gdx.input.setInputProcessor(inputManager);

        paths.add(graphs.get(0).findPath(graphs.get(0).getTiles().get(4), graphs.get(0).getTiles().get(random.nextInt(0,93))));
        paths.add(graphs.get(1).findPath(graphs.get(1).getTiles().get(7),graphs.get(1).getTiles().get(random.nextInt(0,189))));
        paths.add(graphs.get(2).findPath(graphs.get(2).getTiles().get(15),graphs.get(2).getTiles().get(random.nextInt(0,70))));
        int vi = 0;
        for(Villain v: enemies){
            v.setPath(paths.get(vi));
            vi++;
        }

        enemies.get(1).doStopAndIdle();
        enemies.get(2).doStopAndIdle();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.update();
        stateTime += delta;


        camera.position.set(mainCharacter.getX() + 32, mainCharacter.getY() + 32,0);
        camera.zoom = 1.4f;
        camera.update();
        renderer.setView(camera);
        renderer.getBatch().begin();
        for(MapLayer layer : normalLayer){
            renderer.renderTileLayer((TiledMapTileLayer) layer);
        }
        renderer.getBatch().end();
        this.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for(Character v: enemies){
            Villain v1 = (Villain) v;
            for(Node node: v1.getVillainGraph().getTiles()){

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(node.getX() * 16 + 8, node.getY() * 16 + 8, 8);
            }
            for(Node node: v1.getCurrentPath()){

                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.circle(node.getX() * 16 + 8, node.getY() * 16 + 8, 8);
            }
            shapeRenderer.setColor(Color.BLUE);

            for(Arch a: v1.getVillainGraph().getConnectionsLines()){

                a.render(shapeRenderer);
            }
        }
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(mainCharacter.getNearNode().getX()*16+8,mainCharacter.getNearNode().getY()*16+8,8);
        shapeRenderer.end();

        currentFrame = inputManager.nextFrame(stateTime);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Villain v : enemies) {
            Villain villain = (Villain) v;
            currentFrameVillain = villain.getNextStep(stateTime,mainCharacter);
            if(mainCharacter.getY() > villain.getY()){
                batch.draw(currentFrame,mainCharacter.getX(),mainCharacter.getY());
                if(villain.isAlive()){
                    batch.draw(currentFrameVillain,villain.getX(),villain.getY());
                }
            }
            else {
                if(villain.isAlive()){
                    batch.draw(currentFrameVillain,villain.getX(),villain.getY());
                }
                batch.draw(currentFrame,mainCharacter.getX(),mainCharacter.getY());
            }
        }
        batch.end();
        renderer.getBatch().begin();
        renderer.renderTileLayer(leavesLayer);
        renderer.renderTileLayer(bridgeLayer);
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
        for(Node node : graphs.get(0).getTiles()){
            if(Vector2.dst(mainCharacter.getX() + 32,mainCharacter.getY() + 24,mainCharacter.getNearNode().getX() * 16 + 8,mainCharacter.getNearNode().getY() * 16 + 8) >
                    Vector2.dst(mainCharacter.getX() + 32,mainCharacter.getY() + 24,node.getX()* 16 + 8,node.getY() * 16 + 8)){
                mainCharacter.setNearNode(node);
            }
            for(Character v: enemies){
                if(Vector2.dst(v.getX() + 32,v.getY() + 24,v.getNearNode().getX() * 16 + 8,v.getNearNode().getY() * 16 + 8) >
                        Vector2.dst(v.getX() + 32,v.getY() + 24,node.getX()* 16 + 8,node.getY() * 16 + 8)){
                    v.setNearNode(node);
                }
            }

        }


        for(TilesGraph g: graphs){
            Villain v = graphVillainHashMap.get(g);
            for(Node n : g.getTiles()){
                if(Vector2.dst(mainCharacter.getX() + 32,mainCharacter.getY() + 24,mainCharacter.getNearNode().getX() * 16 + 8,mainCharacter.getNearNode().getY() * 16 + 8) >
                        Vector2.dst(mainCharacter.getX() + 32,mainCharacter.getY() + 24,n.getX()* 16 + 8,n.getY() * 16 + 8)) {
                    mainCharacter.setNearNode(n);
                }
                if(Vector2.dst(v.getX() + 32,v.getY() + 24,v.getNearNode().getX() * 16 + 8,v.getNearNode().getY() * 16 + 8) >
                        Vector2.dst(v.getX() + 32,v.getY() + 24,n.getX()* 16 + 8,n.getY() * 16 + 8)) {
                    v.setNearNode(n);
                    }
            }
                if(g.hasNode(mainCharacter.getNearNode())){
                    v.setPath(g.findPath(v.getNearNode(),mainCharacter.getNearNode()));
                }
                else{
                    if(!v.isWalking()) {
                        v.setRandDestination(random.nextInt(0, g.getTiles().size() - 1));
                        v.setPath(g.findPath(v.getNearNode(), g.getTiles().get(v.getRandDestination())));
                    }
                    else{
                        v.setPath(g.findPath(v.getNearNode(), g.getTiles().get(v.getRandDestination())));
                    }
                }
        }

        this.mapCollision();
        this.charactersCollision();
        this.checkHealingBases();
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
        for(Rectangle rec: collisionObjects){
            if(rec.overlaps(mainCharacter.getMovementBox())){
                mainCharacter.setPosition(mainCharacter.getPreviousX(), mainCharacter.getPreviousY());
                break;
            }
        }
        for(Rectangle rec: collisionObjects){
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
        for(Rectangle rec: healingBases){
            if(rec.overlaps(mainCharacter.getMovementBox())){
                mainCharacter.doHeal();
                break;
            }
        }
    }
}
