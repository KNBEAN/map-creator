package database.collections;

import database.implementations.Node;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class NodeArray extends HashSet {

    private NodeArray(Collection c) {
        super(c);
    }

    public NodeArray() {
        new NodeArray(new ArrayList<Node>());
    }

    @Override
    public boolean add(Object o) {
        if (o.getClass() != Node.class)  return false;
        return super.add(o);
    }

    public boolean update(Object o){
        o = (Node) o;
        for (Object x : this){
            if (x.equals(o)){
                super.remove(x);
                super.add(o);
            }
        }
        return false;
    }

    public ArrayList<Node> getArrayList() {
       return new ArrayList<Node> (this);
    }

}