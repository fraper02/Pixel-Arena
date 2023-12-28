package application.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class TileHeuristic implements Heuristic<Node> {
    /**
     * Estimate the cost of reaching the end node from the start node
     * @param node the start node
     * @param endNode the end node
     * @return returns the estimated cost, in this case the distance between the start node and the end node
     */
    @Override
    public float estimate(Node node, Node endNode) {
        return Vector2.dst(node.getX(),node.getY(),endNode.getX(),endNode.getY());
    }
}
