package database.collections;

import database.model.Location_Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Location_TagArray extends HashSet {

    private Location_TagArray(Collection c) {
        super(c);
    }

    public Location_TagArray() {
        new Location_TagArray(new ArrayList<Location_Tag>());
    }

    @Override
    public boolean add(Object o) {
        if (o.getClass()!= Location_Tag.class) return false;
        return super.add(o);
    }

}
