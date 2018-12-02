package data.implementations;

public class Location_Tag implements data.model.Location_Tag {
    private int location_id;
    private String tag;

    public Location_Tag(String tag, int locationId){
        this.tag = tag;
        this.location_id = locationId;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public int getLocation_id() {
        return location_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Location_Tag.class){
            obj = (Location_Tag) obj;
            if (((Location_Tag) obj).getTag().equals(tag) && ((Location_Tag) obj).getLocation_id()==(location_id)) return true;
        }
        return false;
    }
}
