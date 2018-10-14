package database.collections;

import database.implementations.Floor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class FloorArray extends HashSet {

    public FloorArray(Collection c) {
        super(c);
    }

    public FloorArray() {
        new FloorArray(new ArrayList<Floor>());
    }

    @Override
    public boolean add(Object o) {
        if (o.getClass() != Floor.class) return false;
        return super.add(o);
    }

    public boolean update(Object o){
        for (Object x:this){
            if (((Floor) o).getFloors()== ((Floor) x).getFloors()){
                remove(x);
                return add(o);
            }
        }
        return false;
    }
}
