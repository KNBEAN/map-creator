package data.collections;


import data.model.IEdge;

import java.util.ArrayList;
import java.util.List;

public class EdgeList {
   static private List<IEdge> IEdges;

    public EdgeList() {
        this.IEdges = new ArrayList<>();
    }

    public void add(IEdge IEdge){
        if (IEdges.contains(IEdge)) return;
        IEdges.add(IEdge);
        IEdges.add(IEdge.swapEnds());
    }

    public IEdge get(int id){
        for (IEdge s: IEdges){
            if (s.getId() == id) return s;
        }
        return null;
    }

    public void update(IEdge IEdge, IEdge upIEdge){
        remove(IEdge);
        add(upIEdge);
    }

    public void remove(IEdge IEdge){
        IEdges.remove(IEdge);
        IEdges.remove(IEdge.swapEnds());
    }

}
