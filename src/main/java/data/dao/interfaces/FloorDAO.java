package data.dao.interfaces;

import data.implementations.Floor;

import java.util.List;

public interface FloorDAO {
    Floor getFloor(int floor);
    void insert(Floor floor);
    void delete(int floor);
    void update(Floor floor);
    List<Floor> getAllFloors();
}
