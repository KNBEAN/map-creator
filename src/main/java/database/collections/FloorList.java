package database.collections;

import database.model.Floor;

import java.util.ArrayList;

public class FloorList {
    private ArrayList<Floor> floors;

    public FloorList() {
        this.floors = new ArrayList<>();
    }

    public void add(Floor floor){
        if (floors.contains(floor)) return;
        floors.add(floor);
    }

    public void remove(Floor floor){
        floors.remove(floor);
    }

    public void update(Floor floor,Floor upFloor){
        floors.remove(floor);
        floors.add(upFloor);
    }

    public Floor get(int floor){
        for (Floor f:floors){
            if (f.getFloors() == floor) return f;
        }
        return null;
    }
}
