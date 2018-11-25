package data.dao.interfaces;

import data.implementations.Floor;

import java.util.List;

public interface FloorDAO {

    /**
     * @param floor
     * @return Floor, if not exists - null
     */
    Floor getFloor(int floor);

    /**
     * @param floor
     */
    void insert(Floor floor);

    /**
     * Deletes floor if exists
     * @param floor
     */

    void delete(int floor);

    /**
     * Updates floor name and image path
     * @param floor - updates by id
     */
    void update(Floor floor);

    /**
     * @return List<Floor>
     */
    List<Floor> getAllFloors();
}
