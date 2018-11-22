package data.collections;

import data.model.IFloor;

import java.util.ArrayList;
import java.util.List;

public class FloorList {
   static private List<IFloor> IFloors;

    public FloorList() {
        this.IFloors = new ArrayList<>();
    }

    public void add(IFloor IFloor){
        if (IFloors.contains(IFloor)) return;
        IFloors.add(IFloor);
    }

    public void remove(IFloor IFloor){
        IFloors.remove(IFloor);
    }

    public void update(IFloor IFloor, IFloor upIFloor){
        IFloors.remove(IFloor);
        IFloors.add(upIFloor);
    }

    public IFloor get(int floor){
        for (IFloor f: IFloors){
            if (f.getFloors() == floor) return f;
        }
        return null;
    }

}
