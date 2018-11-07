package database.collections;

import database.model.Location;

import java.util.ArrayList;
import java.util.Objects;

public class LocationList {
    private ArrayList<Location> locations;

    public LocationList() {
        locations = new ArrayList<>();
    }

    public boolean add(Location location){
        if (locations.contains(location)) return false;
        for (Location temp : locations){
            if (temp.getName() .equals( location.getName())) return false;
        }
        return locations.add(location);
    }

    public void remove(Location location){
        locations.remove(location);
    }

    public void update(Location location, Location upLocation){
        remove(location);
        add(upLocation);
    }

    public Location get(int id){
        for(Location location : locations) if (location.getId()==id) return location;
        return null;
    }
}
