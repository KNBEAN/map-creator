package database.collections;

import database.implementations.Edge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class EdgeArray extends HashSet {

    private EdgeArray(Collection c) {
        super(c);
    }

    public EdgeArray() {
        new EdgeArray(new ArrayList<Edge>());
    }

    @Override
    public boolean add(Object o) {
        if (o.getClass() != Edge.class) return false;
        super.add(o);
        return super.add(((Edge)o).swapEnds());
    }

    @Override
    public boolean remove(Object o) {
        super.remove(o);
        return super.remove(((Edge)o).swapEnds());
    }

    public boolean update(Object o){
        for (Object s :this){
            if (((Edge)s).getId() == ((Edge)o).getId()) {
                Edge g = ((Edge) s).swapEnds();
                for (Object t :this){
                    if (((Edge)t).equals((Edge)g))
                    {
                        super.remove(t);
                        super.remove(s);
                        add(o);
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Edge> getArrayList() {
        return new ArrayList<Edge> (this);
    }
}
