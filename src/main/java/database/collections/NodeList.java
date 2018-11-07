package database.collections;

import database.model.Node;

import java.util.ArrayList;

public class NodeList {
    private ArrayList<Node> nodes;

    public NodeList() { nodes = new ArrayList<>();
    }

    public boolean add(Node node){
        if (nodes.contains(node)) return false;
        return nodes.add(node);
    }

    public void remove(Node node){
        nodes.remove(node);
    }

    public void update(Node node, Node upNode){
        remove(node);
        add(upNode);
    }

    public Node get(int id){
        for (Node node:nodes){
            if (node.getId()==id) return node;
        }
        return null;
    }
}
