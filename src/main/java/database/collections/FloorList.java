package database.collections;

import database.implementations.Floor;

import java.util.ArrayList;

public class FloorList {
    private ArrayList<Floor> floors;

    public FloorList() {
        this.floors = new ArrayList<>();
    }

    public void add(Floor floor){
        if (floors.contains(floor)) return;
    }
}
