package data.dao.interfaces;

import data.implementations.Quick_Access_Location;

import java.util.List;

public interface Quick_Access_LocationDAO {

    /**
     * @param id
     * @return if not exists returns null
     */
    Quick_Access_Location getQuickAccessLocation(int id);

    /**
     * @param quick_access_location
     */
    void insert(Quick_Access_Location quick_access_location);

    /**
     * @param quick_access_locations
     */
    void insert(List<Quick_Access_Location> quick_access_locations);

    /**
     * @param id
     */
    void delete(int id);

    /**
     * Updates location_id and type
     * @param quick_access_location
     */
    void update(Quick_Access_Location quick_access_location);

    /**
     * @return List<Quick_Access_Location>
     */
    List<Quick_Access_Location> getAllQuick_Access_Locations();

    /**
     * @param floor
     * @return List of quick_access_locations on floor. Looks by location_id in nodes on given floor
     */
    List<Quick_Access_Location> getAllQuick_Access_LocationsOnFloor(int floor);

    /**
     * @param type
     * @return All quick access locations with given type
     */
    List<Quick_Access_Location>  getAllQuick_Access_LocationsType(int type);

}
