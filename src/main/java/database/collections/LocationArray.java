package database.collections;

import database.implementations.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class LocationArray extends HashSet {

    private LocationArray(Collection c) {
        super(c);
    }

    public LocationArray() {
        new LocationArray(new ArrayList<Location>());
    }

    @Override
    public boolean add(Object o) {
        if (o.getClass() != Location.class) return false;
        return super.add(o);
    }

    public boolean update(Object o){
        for (Object x:this){
            if (((Location) o).getId()== ((Location) x).getId()){
                remove(x);
                return add(o);
            }
        }
        return false;
    }
}
