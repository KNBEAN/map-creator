package data.collections;

import data.implementations.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeList {
    static private List<Node> INodes;

    public NodeList() { INodes = new ArrayList<>();
    }

    public boolean add(Node node){
        if (INodes.contains(node)) return false;

        return INodes.add(node);
    }

    public void remove(Node node){
        INodes.remove(node);
    }

    public void update(Node node, Node upNode){
        remove(node);
        add(upNode);
    }

    public Node get(int id){
        for (Node node : INodes){
            if (node.getId()==id) return node;
        }
        return null;
    }

}
