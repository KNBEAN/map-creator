package database.implementations;

import database.Id_Generator;

public class Node implements database.model.Node {

    private int ID;
    private int floor;
    private int x;
    private int y;
    private int locationId;

    public Node (int x,int y,int floor,int locationId){
        ID = Id_Generator.getId();
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.locationId = locationId;
    }
    public Node (int x,int y,int floor){
        ID = Id_Generator.getId();
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    @Override
    public int getID() {
        return ID;
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
        return locationId;
    }
}
