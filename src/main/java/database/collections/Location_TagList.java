package database.collections;

import database.model.ILocation_Tag;

import java.util.ArrayList;

public class Location_TagList {
   static private ArrayList<ILocation_Tag> tags;

    public Location_TagList(){
        tags = new ArrayList<>();}

    public boolean add(ILocation_Tag ILocation_tag){
        if (tags.contains(ILocation_tag)) return false;
        for (ILocation_Tag ILocation_tagTemp : tags)
            if (ILocation_tag.getTag()== ILocation_tagTemp.getTag()) return false;
       return tags.add(ILocation_tag);
    }

    public void remove(ILocation_Tag ILocation_tag){
        tags.remove(ILocation_tag);
    }

    public void update(ILocation_Tag ILocation_tag, ILocation_Tag upILocation_tag){
        remove(ILocation_tag);
        add(upILocation_tag);
    }

    public ILocation_Tag get(String name){
        for (ILocation_Tag ILocation_tag : tags){
            if (ILocation_tag.getTag().equals(name)) return ILocation_tag;
        }
        return null;
    }


}
