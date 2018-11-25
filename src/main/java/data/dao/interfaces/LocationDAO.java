package data.dao.interfaces;

import data.implementations.Location;

import java.time.LocalDate;
import java.util.List;

public interface LocationDAO {
    Location getLocation(int id);
    void insert(Location location);
    void insert(List<Location> locations);
    void delete(int id);
    void update(Location location);
    List<Location> getAllLocations();
    List<Location> getAllLocationsOnFloor(int floor);
}
