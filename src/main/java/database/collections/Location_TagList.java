package database.collections;

import database.model.Location_Tag;
import java.util.ArrayList;

public class Location_TagList {
    private ArrayList<Location_Tag> tags;

    public Location_TagList(){
        tags = new ArrayList<>();}

    public boolean add(Location_Tag location_tag){
        if (tags.contains(location_tag)) return false;
        for (Location_Tag location_tagTemp : tags)
            if (location_tag.getTag()==location_tagTemp.getTag()) return false;
       return tags.add(location_tag);
    }

    public void remove(Location_Tag location_tag){
        tags.remove(location_tag);
    }

    public void update(Location_Tag location_tag,Location_Tag upLocation_tag){
        remove(location_tag);
        add(upLocation_tag);
    }

    public Location_Tag get(String name){
        for (Location_Tag location_tag: tags){
            if (location_tag.getTag().equals(name)) return location_tag;
        }
        return null;
    }


}
