package database.collections;


import database.model.Edge;

import java.util.ArrayList;

public class EdgeList {
    private ArrayList<Edge> edges;

    public EdgeList() {
        this.edges = new ArrayList<>();
    }

    public void add(Edge edge){
        if (edges.contains(edge)) return;
        edges.add(edge);
        edges.add(edge.swapEnds());
    }

    public Edge get(int id){
        for (Edge s:edges){
            if (s.getId() == id) return s;
        }
        return null;
    }

    public void update(Edge edge,Edge upEdge){
        remove(edge);
        add(upEdge);
    }

    public void remove(Edge edge){
        edges.remove(edge);
        edges.remove(edge.swapEnds());
    }
}
