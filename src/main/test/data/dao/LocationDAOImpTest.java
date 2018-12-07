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
    private Location location3;
    private ArrayList<Location> locations;
    private ArrayList<Location> locationsPrep;

    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase(null,false);
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        nodeDAO = new NodeDAOImp();
        locationDAO = new LocationDAOImp();
        floorDAO = new FloorDAOImp();
        locationsPrep = new ArrayList<Location>(){
            {
                add(new Location(1,"1",null));
                add(new Location(2,"2",null));
                add(new Location(3,"3",null));
                add(new Location(-1,"null",null));
            }
        };
        locationDAO.insert(locationsPrep);
    }


    @Test
    void shouldGetLocation() {
        assertEquals(locationsPrep.get(0), locationDAO.getLocation(locationsPrep.get(0).getId()));
        assertEquals(locationsPrep.get(1), locationDAO.getLocation(locationsPrep.get(1).getId()));
    }

    @Test
    void shouldDeleteLocation() {

        locationDAO.delete(1);
        locationDAO.delete(2);
        assertNull(locationDAO.getLocation(locationsPrep.get(0).getId()));
        assertNull(locationDAO.getLocation(locationsPrep.get(1).getId()));
    }

    @Test
    void shouldGetAllLocations() {
        List<Location> locations = locationDAO.getAllLocations();
        assertEquals(locationsPrep.size(),locations.size());
    }

    @Test
    void shouldGetAllLocationsOnFloor() {
        Location location1 = new Location(4,"location1",null);
        Location location2 = new Location(5,"location2",null);
        Location location3 = new Location(6,"location3",null);
        Floor floor1 = new Floor(1, "floor1", null);
        Floor floor2 = new Floor(2, "floor2", null);
        locationDAO.insert(location1);
        locationDAO.insert(location2);
        locationDAO.insert(location3);
        floorDAO.insert(floor1);
        floorDAO.insert(floor2);
        Node node1 = new Node(3, 1, 1, location1.getId());
        Node node2 = new Node(4, 1, 2, location2.getId());
        Node node3 = new Node(5, 1, 2, location3.getId());
        nodeDAO.insert(node1);
        nodeDAO.insert(node2);
        nodeDAO.insert(node3);
        List<Location> locations1 = locationDAO.getAllLocationsOnFloor(floor1.getFloors());
        List<Location> locations2 = locationDAO.getAllLocationsOnFloor(floor2.getFloors());
        assertEquals(1, locations1.size());
        assertEquals(2, locations2.size());
    }

}