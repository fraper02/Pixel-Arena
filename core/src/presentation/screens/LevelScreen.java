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
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

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
    private OrthogonalTiledMapRenderer renderer;
    private float stateTime;
    private Character mainCharacter;
    private TextureRegion currentFrame;

    private TextureRegion currentFrameVillain;
    InputManager inputManager;
    private List<Character> enemies;

    TiledMapTileLayer legitLayer;

    GraphPath<Node> path;

    ArrayList<Node> nodes;

    ShapeRenderer shapeRenderer;

    TilesGraph graph;

    public LevelScreen(Game game, Character mainCharacter, List<Character> enemies){
        this.game = game;
        this.mainCharacter = mainCharacter;
        this.enemies = enemies;
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
        for (MapObject object : collisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                collisionObjects.add(rect);
            }
        }

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        mainCharacter.doStopAndIdle();
        inputManager = new InputManager(mainCharacter,enemies);

        nodes = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();


        legitLayer = (TiledMapTileLayer) map.getLayers().get("nodesLayer");
        int width = legitLayer.getWidth();
        int height = legitLayer.getHeight();

        graph = new TilesGraph();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TiledMapTileLayer.Cell cell = legitLayer.getCell(x, y);
                if (cell != null) {
                    Node node = new Node(x, y);
                    nodes.add(node);
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


        mainCharacter.setPosition((nodes.get(0).getX() * 16 + 8) - 32,  (nodes.get(0).getY() * 16 + 8) - 24);

        mainCharacter.setNearNode(graph.getTiles().get(0));

        enemies.get(0).setPosition((nodes.get(4).getX() * 16 + 8) - 32,(nodes.get(4).getY() * 16 + 8) - 24);
        //enemies.get(1).setPosition((nodes.get(26).getX() * 16 + 8) - 32,(nodes.get(26).getY() * 16 + 8) - 24);
        //enemies.get(2).setPosition((nodes.get(43).getX() * 16 + 8) - 32,(nodes.get(43).getY() * 16 + 8) - 24);
        //*enemies.get(3).setPosition((nodes.get(65).getX() * 16 + 8) - 32,(nodes.get(65).getY() * 16 + 8) - 24);

        enemies.get(0).setNearNode(graph.getTiles().get(4));

        /*enemies.get(1).setNearNode(graph.getTiles().get(26));
        enemies.get(2).setNearNode(graph.getTiles().get(43));
        enemies.get(3).setNearNode(graph.getTiles().get(65));*/

        System.out.println("SIZE: " + graph.getTiles().size());

        Random random = new Random();
        Gdx.input.setInputProcessor(inputManager);

        path = graph.findPath(graph.getTiles().get(4), graph.getTiles().get(random.nextInt(0,87)));
        Villain v = (Villain) enemies.get(0);
        v.setPath(path);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.update();
        stateTime += delta;


        camera.position.set(mainCharacter.getX() + 32, mainCharacter.getY() + 32,0);
        camera.zoom = 0.8f;
        camera.update();
        renderer.setView(camera);
        renderer.render();

        this.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for(Node node: graph.getTiles()){

            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(node.getX() * 16 + 8, node.getY() * 16 + 8, 8);
        }
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(enemies.get(0).getNearNode().getX()*16 +8,enemies.get(0).getNearNode().getY()*16 + 8,8);

        for(Arch a: graph.getConnectionsLines()){

            a.render(shapeRenderer);
        }
        shapeRenderer.end();

        currentFrame = inputManager.nextFrame(stateTime);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Character v : enemies) {
            Villain villain = (Villain) enemies.get(0);
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
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    public void update(){
        for(Node node : graph.getTiles()){
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

        for(Character v: enemies){
                Villain villain = (Villain) v;
                path = graph.findPath(v.getNearNode(), mainCharacter.getNearNode());
                ((Villain) v).setPath(path);
        }

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
     * Method that updates the status of the game calling different check methods
     */
    private void update(){
        this.mapCollision();
        this.charactersCollision();
    }

    /**
     * Check if there was a collision between the main character and the map borders
     */
    private void mapCollision(){
        for(Rectangle rec: collisionObjects){
            if(rec.overlaps(mainCharacter.getMovementBox())){
                mainCharacter.setPosition(mainCharacter.getPreviousX(), mainCharacter.getPreviousY());
                break;
            }
        }
    }

    /**
     * check if there was a collision between characters
     */
    private void charactersCollision(){
        for(Character v : enemies){
            mainCharacter.collisionCheck(v);
        }
    }
}
