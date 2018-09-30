package database.implementations;

public class Location_Tag implements database.model.Location_Tag {
    private int locationId;
    private String tag;

    public Location_Tag (String tag, int locationId){
        this.tag = tag;
        this.locationId = locationId;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public int getLocationId() {
        return locationId;
    }
}
