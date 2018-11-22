package data.implementations;

public class Location_Tag implements data.model.ILocation_Tag {
    private int locationId;
    private String tag;

    public Location_Tag(String tag, int locationId){
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