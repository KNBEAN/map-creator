package database.implementations;

import database.Id_Generator;

public class Edge implements database.model.Edge {

    private int ID;
    private int from_id;
    private int to_id;
    private int length;


    public Edge (Node from_Node,Node to_Node){
        ID = Id_Generator.getId();
        this.from_id = from_Node.getID();
        this.to_id = to_Node.getID();
        this.length = calculateLength(from_Node,to_Node);
    }

    public Edge (int from_id,int to_id,int length){
        ID = Id_Generator.getId();
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
    public int getID() {
        return ID;
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
}
