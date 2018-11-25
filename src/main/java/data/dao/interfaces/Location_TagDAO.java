package data.dao.interfaces;

import data.implementations.Location_Tag;

import java.util.List;

public interface Location_TagDAO {

    /**
     * @param location_id
     * @return List of tags connected to location, if not exists returns empty List
     */
    List<Location_Tag> getLocation_Tags(int location_id);

    /**
     * @param location_Tag
     */
    void insert(Location_Tag location_Tag);

    /**
     * @param location_tags
     */
    void insert(List<Location_Tag> location_tags);

    /**
     * @param tag
     */
    void delete(String tag);

    /**
     * Update location_id of given tag
     * @param location_Tag - updates by id
     */
    void update(Location_Tag location_Tag);

    /**
     * @return List<Location_Tag>
     */
    List<Location_Tag> getAllLocations_Tag();

    /**
     * @param floor
     * @return List of Location_Tags on given floor
     */
    List<Location_Tag> getAllLocations_TagsOnFloor(int floor);
}
