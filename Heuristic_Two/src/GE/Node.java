package GE;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected List<Node> children;
    protected String value = null;

    public Node(String value){
        this.children = new ArrayList<Node>();
        this.value = value;
    }

    /**
     * Adds a node to the list of children
     * @param node
     */
    public void addChild(Node node){
        children.add(node);
    }

    /**
     * Getter method
     * @return list of children
     */
    public List<Node> getChildren(){
        return this.children;
    }

    public int getSizeOfChildren(){
        return this.children.size();
    }
    public abstract void execute();
}
