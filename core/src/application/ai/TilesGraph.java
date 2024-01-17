package application.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;
import java.util.Iterator;

public class TilesGraph implements IndexedGraph<Node> {

    private TileHeuristic tileHeuristic = new TileHeuristic();
    private ArrayList<Node> tiles = new ArrayList<>();
    private ArrayList<Arch> Connections = new ArrayList<>();
    private ObjectMap<Node, Array<Connection<Node>>> nodesMap = new ObjectMap<>();

    public TilesGraph(){}

    private int lastNodeIndex = 0;


    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return tiles.size();
    }

    /**
     * return all fromNode connections
     * @param fromNode the node whose outgoing connections will be returned
     * @return An Array with the connections.
     * @Pre il nodo fromNode deve essere non nullo
     */

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if(nodesMap.containsKey(fromNode)){
            return nodesMap.get(fromNode);
        }

        return new Array<>(0);
    }

    /**
     * Adds nodes to the ArrayList, and updates the index that will be used by the IndexedA*
     * @param node represent the passed node
     * @Pre node deve essere un nodo non nullo.
     */

    public void addTiles(Node node){
        node.setIndex(lastNodeIndex);
        lastNodeIndex++;

        tiles.add(node);
    }

    /**
     * A method that menage the way to connect the different node.
     * @param fromNode is the start node.
     * @param toNode is the arrival node.
     * @Pre Numeri di nodi nel grafo > 1, fromNode e toNode devono essere Nodi non nulli e diversi tra loro.
     */
    public void connectAdjacentNodes(Node fromNode, Node toNode){
        if(fromNode.isAdjacentTo(toNode,3)) {
            Arch arch = new Arch(fromNode, toNode);
            if (!nodesMap.containsKey(fromNode)) {
                nodesMap.put(fromNode, new Array<Connection<Node>>());
            }
            nodesMap.get(fromNode).add(arch);
            Connections.add(arch);
        }
    }

    /**
     * This method menage the way to find a path in the graph, we use the A* Algorithm.
     * @param startNode
     * @param goalNode represent the node where the mainCharacter is situated.
     * @return il path da startNode a GoaLNode
     * @Pre startNode e goalNode devono essere due nodi validi non nulli nel grafo
     * @Pre Esiste un percorso tra startNode al goalNode
     */
    public GraphPath<Node> findPath(Node startNode, Node goalNode){
        GraphPath<Node> cityPath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode,tileHeuristic,cityPath);
        return cityPath;
    }

    public ArrayList<Arch> getConnectionsLines(){
        return Connections;
    }

    public ArrayList<Node> getTiles(){ return this.tiles; }

    public boolean hasNode(Node node){
        return tiles.contains(node);
    }
}
