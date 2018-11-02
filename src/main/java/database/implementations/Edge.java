package database.implementations;

import database.Id_Generator;

public class Edge implements database.model.Edge {

    private int id;
    private int from_id;
    private int to_id;
    private int length;

    public Edge (Node from_Node,Node to_Node){
        id = Id_Generator.getId();
        this.from_id = from_Node.getId();
        this.to_id = to_Node.getId();
        this.length = calculateLength(from_Node,to_Node);
    }

    public Edge (int from_id,int to_id,int length){
        id = Id_Generator.getId();
        this.from_id = from_id;
        this.to_id = to_id;
        this.length = length;
    }

    @Override
    public int calculateLength(database.model.Node from, database.model.Node to) {
        return (int) Math.sqrt(Math.pow((double)from.getX()-to.getX(),2)
                + Math.pow((double)from.getY()-to.getY(),2));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getFrom() {
        return from_id;
    }

    @Override
    public int getTo() {
        return to_id;
    }

    @Override
    public int getLength() {
        return length;
    }

    public Edge swapEnds(){
        return new Edge(this.to_id,this.from_id,length);
    }

    @Override
    public boolean equals(Object obj) {
        Edge objectEdge = null;
        if (obj.getClass() == Edge.class){
            objectEdge = (Edge) obj;
        }
        if (objectEdge.getTo() == to_id && objectEdge.getFrom() == from_id
                && objectEdge.length == length) return true;
        return false;
    }

    @Override
    public String toString() {
        return " ID: " + id + "\n" +
                " From ID: " + from_id + "\n" +
                " To ID: "+ to_id + "\n" +
                " Length: " + length;
    }
}
