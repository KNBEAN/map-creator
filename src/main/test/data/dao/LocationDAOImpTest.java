package data.dao;

import data.dao.interfaces.FloorDAO;
import data.dao.interfaces.LocationDAO;
import data.dao.interfaces.NodeDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;
import data.implementations.Location;
import data.implementations.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationDAOImpTest {

    private NodeDAO nodeDAO;
    private LocationDAO locationDAO;
    private FloorDAO floorDAO;
    private Location location1;
    private Location location2;

    @BeforeAll
    static void init(){
        DatabaseManager.createNewDatabase();
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        nodeDAO = new NodeDAOImp();
        locationDAO = new LocationDAOImp();
        floorDAO = new FloorDAOImp();
        location1 = new Location("2");
        location2 = new Location("1");
        locationDAO.insert(location1);
        locationDAO.insert(location2);
    }


    @Test
    void shouldGetLocation(){
        assertEquals(location1,locationDAO.getLocation(location1.getId()));
        assertEquals(location2,locationDAO.getLocation(location2.getId()));
    }

    @Test
    void shouldDeleteLocation(){

        locationDAO.delete(location1.getId());
        locationDAO.delete(location2.getId());
        assertNull(locationDAO.getLocation(location1.getId()));
        assertNull(locationDAO.getLocation(location2.getId()));
    }

    @Test
    void shouldGetAllLocations() {
        List<Location> locations = locationDAO.getAllLocations();
        assertEquals(location1,locations.get(0));
        assertEquals(location2,locations.get(1));
    }

    @Test
    void shouldGetAllLocationsOnFloor(){
        Location location1 = new Location("location1");
        Location location2 = new Location("location2");
        Location location3 = new Location("location3");
        Floor floor1 = new Floor(1,"floor1",null);
        Floor floor2 = new Floor(2,"floor2",null);
        locationDAO.insert(location1);
        locationDAO.insert(location2);
        locationDAO.insert(location3);
        floorDAO.insert(floor1);
        floorDAO.insert(floor2);
        Node node1 = new Node(3,1,1,location1.getId());
        Node node2 = new Node(4,1,2,location2.getId());
        Node node3 = new Node(5,1,2,location3.getId());
        nodeDAO.insert(node1);
        nodeDAO.insert(node2);
        nodeDAO.insert(node3);
        List<Location> locations1 = locationDAO.getAllLocationsOnFloor(floor1.getFloors());
        List<Location> locations2 = locationDAO.getAllLocationsOnFloor(floor2.getFloors());
        assertEquals(1,locations1.size());
        assertEquals(2,locations2.size());
    }

    @Test
    void shouldInsertAll() {
        List<Location> locations = new ArrayList<>();
        Location location1 = new Location("location5");
        Location location2 = new Location("location6");
        Location location3 = new Location("location7");
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        locationDAO.insert(locations);
        assertTrue(locationDAO.getAllLocations().contains(location1));
        assertTrue(locationDAO.getAllLocations().contains(location2));
        assertTrue(locationDAO.getAllLocations().contains(location3));
    }
}