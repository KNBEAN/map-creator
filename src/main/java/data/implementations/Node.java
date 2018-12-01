package data.implementations;

import data.Id_Generator;

public class Node implements data.model.Node {

    private int id;
    private int floor;
    private int x;
    private int y;
    private int locationID;

    public Node(int x, int y, int floor, int locationId){
        id = Id_Generator.getId();
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.locationID = locationId;
    }
    public Node(int x, int y, int floor){
        id = Id_Generator.getId();
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.locationID = -1;
    }

    public Node(int id,  int x, int y,int floor, int locationID) {
        this.id = id;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.locationID = locationID;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getLocationID() {
        return locationID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Node.class) return false;
        Node objectNode = (Node) obj;
        if (objectNode.getX() == x
                && objectNode.getFloor() == floor
                && objectNode.getY() == y
                && objectNode.getLocationID() == locationID) return true;
        return false;
    }

    @Override
    public String toString() {
        return " ID: " + id + "\n" +
                " Floor: "+ floor + "\n" +
                " X: " + x + "\n" +
                " Y: " + y + "\n" +
                " Location ID: "+ locationID;
    }
}
