package application.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Arch implements Connection<Node> {
    Node fromTile;
    Node toTile;
    float cost;

    /**
     * Initialize a connection between two nodes and calculates the connection cost
     * @param fromTile starting point of the connection
     * @param toTile end point of the connection
     * @Pre fromtTile e toTile devono essere due nodi non nulli
     */
    public Arch(Node fromTile,Node toTile){
        this.fromTile = fromTile;
        this.toTile = toTile;
        cost = Vector2.dst(fromTile.getX(),fromTile.getY(),toTile.getX(),toTile.getY());
    }

    /**
     * Render the connection between the nodes given a shape renderer
     * @param renderer the shape renderer that renders the connection
     */
    public void render(ShapeRenderer renderer){
        renderer.setColor(Color.WHITE);
        renderer.rectLine(fromTile.getX() * 16 +8, fromTile.getY() * 16 + 8,toTile.getX() * 16 +8 , toTile.getY() * 16 + 8,2);
    }
    @Override
    public float getCost() {
        return cost;
    }
    @Override
    public Node getFromNode() {
        return fromTile;
    }
    @Override
    public Node getToNode() {
        return toTile;
    }
}
