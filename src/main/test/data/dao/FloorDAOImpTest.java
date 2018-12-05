package data.dao;

import data.dao.interfaces.FloorDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorDAOImpTest {
    FloorDAO floorDAO;
    Floor floor1;

    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase(null,true);
    }

    @BeforeEach
    void setUp() {
        floorDAO = new FloorDAOImp();
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        floor1 = new Floor(1, "1", null);
        floorDAO.insert(floor1);
    }

    @Test
    void shouldGetInsertedFloor() {
        assertEquals(floor1, floorDAO.getFloor(1));
    }

    @Test
    void shouldDeleteFloor() {
        floorDAO.delete(1);
        assertNull(floorDAO.getFloor(1));
    }

    @Test
    void shouldUpdateFloor() {
        floor1 = new Floor(1, "2", "new image path");
        floorDAO.update(floor1);
        assertEquals(floor1, floorDAO.getFloor(1));
    }

    @Test
    void shouldGetAllFloors() {
        Floor floor2 = new Floor(2, "2", null);
        floorDAO.insert(floor2);
        List<Floor> floors = floorDAO.getAllFloors();
        assertTrue(floors.contains(floor2));
        assertTrue(floors.contains(floor1));
    }
}