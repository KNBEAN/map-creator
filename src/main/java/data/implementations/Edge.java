package data.implementations;

import data.Id_Generator;
import data.model.Node;

public class Edge implements data.model.Edge {

    private int id;
    private int from;
    private int to;
    private int length;

    public Edge(data.implementations.Node from_Node, data.implementations.Node to_Node){
        id = Id_Generator.getId();
        this.from = from_Node.getId();
        this.to = to_Node.getId();
        this.length = calculateLength(from_Node,to_Node);
    }

    public Edge(int from_id, int to_id, int length){
        id = Id_Generator.getId();
        this.from = from_id;
        this.to = to_id;
        this.length = length;
    }

    public Edge(int id, int from, int to, int length) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.length = length;
    }

    @Override
    public int calculateLength(Node from, Node to) {
        return (int) Math.sqrt(Math.pow((double)from.getX()-to.getX(),2)
                + Math.pow((double)from.getY()-to.getY(),2));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getFrom() {
        return from;
    }

    @Override
    public int getTo() {
        return to;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public Edge swapEnds(){
        return new Edge(this.to,this.from,length);
    }

    @Override
    public boolean equals(Object obj) {
        Edge objectEdge = null;
        if (obj.getClass() == Edge.class){
            objectEdge = (Edge) obj;
        }
        if (objectEdge.getTo() == to && objectEdge.getFrom() == from
                && objectEdge.length == length) return true;
        return false;
    }

    @Override
    public String toString() {
        return " ID: " + id + "\n" +
                " From ID: " + from + "\n" +
                " To ID: "+ to + "\n" +
                " Length: " + length;
    }
}
