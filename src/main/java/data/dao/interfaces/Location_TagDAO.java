package data.dao.interfaces;

import data.implementations.Location_Tag;

import java.util.List;

public interface Location_TagDAO {
    List<Location_Tag> getLocation_Tags(int location_id);
    void insert(Location_Tag location_Tag);
    void insert(List<Location_Tag> location_tags);
    void delete(String tag);
    void update(Location_Tag location_Tag);
    List<Location_Tag> getAllLocations_Tag();
    List<Location_Tag> getAllLocations_TagsOnFloor(int floor);
}
