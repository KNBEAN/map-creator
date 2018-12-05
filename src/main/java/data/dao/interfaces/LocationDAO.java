package data.dao.interfaces;

import data.implementations.Location;

import java.time.LocalDate;
import java.util.List;

public interface LocationDAO {

    /**
     * @param id
     * @return location if not exists returns null
     */
    Location getLocation(int id);

    /**
     * @param location
     */
    void insert(Location location);

    /**
     * @param locations
     */
    void insert(List<Location> locations);

    /**
     * @param id
     */
    void delete(int id);

    /**
     * Updates location name and description
     * @param location
     */
    void update(Location location);

    /**
     * @return List<Location>
     */
    List<Location> getAllLocations();

    /**
     * @param floor
     * @return List of Locations on floor. Looks by location_id in nodes on given floor
     */
    List<Location> getAllLocationsOnFloor(int floor);
}
