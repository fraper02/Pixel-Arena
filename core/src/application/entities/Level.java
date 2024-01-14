package application.entities;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int numLivello;
    private List<Rectangle> collisionObjects = new ArrayList<>();
    private List<Rectangle> healingBases = new ArrayList<>();
    private MapLayers normalLayer;
    private TiledMapTileLayer leavesLayer;
    private TiledMapTileLayer bridgeLayer;
    private List<TiledMapTileLayer> legitLayers = new ArrayList<>();
    private TiledMap map;

    public Level(int numLivello){
        this.numLivello = numLivello;
        map = new TmxMapLoader().load(("mappe/Livello" + numLivello + "/Level" + numLivello + ".tmx"));
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

        MapGroupLayer nodesLayers = (MapGroupLayer) map.getLayers().get("nodesLayers");
        MapLayers mapLayers = nodesLayers.getLayers();

        for(MapLayer l : mapLayers){
            legitLayers.add((TiledMapTileLayer) l);
        }
    }

    public int getNumLivello() {
        return numLivello;
    }

    public void setNumLivello(int numLivello) {
        this.numLivello = numLivello;
    }

    public List<Rectangle> getCollisionObjects() {
        return collisionObjects;
    }

    public void setCollisionObjects(List<Rectangle> collisionObjects) {
        this.collisionObjects = collisionObjects;
    }

    public List<Rectangle> getHealingBases() {
        return healingBases;
    }

    public void setHealingBases(List<Rectangle> healingBases) {
        this.healingBases = healingBases;
    }

    public MapLayers getNormalLayer() {
        return normalLayer;
    }

    public void setNormalLayer(MapLayers normalLayer) {
        this.normalLayer = normalLayer;
    }

    public TiledMapTileLayer getLeavesLayer() {
        return leavesLayer;
    }

    public void setLeavesLayer(TiledMapTileLayer leavesLayer) {
        this.leavesLayer = leavesLayer;
    }

    public TiledMapTileLayer getBridgeLayer() {
        return bridgeLayer;
    }

    public void setBridgeLayer(TiledMapTileLayer bridgeLayer) {
        this.bridgeLayer = bridgeLayer;
    }

    public List<TiledMapTileLayer> getLegitLayers() {
        return legitLayers;
    }

    public void setLegitLayers(List<TiledMapTileLayer> legitLayers) {
        this.legitLayers = legitLayers;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
}
