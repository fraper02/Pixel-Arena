package application.ai;

public class Node {
    public int x;
    public int y;
    public int index;
    public Node(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Control if two nodes can be connected given a radius of maxDistance
     * @param node it is the explicit node to compare with the implicit node
     * @param maxDistance it's the maxDistance we want (in term of tiles) between nodes
     * @return true if the nodes can be connected otherwise returns false
     */
    public boolean isAdjacentTo(Node node, int maxDistance){
        int distanceX = Math.abs(this.x - node.x );
        int distanceY = Math.abs(this.y - node.y );
        return (distanceX <= maxDistance && distanceY == 0) || (distanceX == 0 && distanceY <= maxDistance);
    }
}
