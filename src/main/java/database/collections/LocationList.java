package database.collections;
import database.model.ILocation;

import java.util.ArrayList;

public class LocationList {
    static private ArrayList<ILocation> ILocations;

    public LocationList() {
        ILocations = new ArrayList<>();
    }

    public boolean add(ILocation ILocation){
        if (ILocations.contains(ILocation)) return false;
        for (ILocation temp : ILocations){
            if (temp.getName() .equals( ILocation.getName())) return false;
        }
        return ILocations.add(ILocation);
    }

    public void remove(ILocation ILocation){
        ILocations.remove(ILocation);
    }

    public void update(ILocation ILocation, ILocation upILocation){
        remove(ILocation);
        add(upILocation);
    }

    public ILocation get(int id){
        for(ILocation ILocation : ILocations) if (ILocation.getId()==id) return ILocation;
        return null;
    }

}
