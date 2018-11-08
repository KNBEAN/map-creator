package database.collections;

import database.model.INode;

import java.util.ArrayList;

public class NodeList {
    static private ArrayList<INode> INodes;

    public NodeList() { INodes = new ArrayList<>();
    }

    public boolean add(INode INode){
        if (INodes.contains(INode)) return false;
        return INodes.add(INode);
    }

    public void remove(INode INode){
        INodes.remove(INode);
    }

    public void update(INode INode, INode upINode){
        remove(INode);
        add(upINode);
    }

    public INode get(int id){
        for (INode INode : INodes){
            if (INode.getId()==id) return INode;
        }
        return null;
    }

}
