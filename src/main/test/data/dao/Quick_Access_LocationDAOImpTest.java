package data.dao;

import data.dao.interfaces.FloorDAO;
import data.dao.interfaces.LocationDAO;
import data.dao.interfaces.NodeDAO;
import data.dao.interfaces.Quick_Access_LocationDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;
import data.implementations.Location;
import data.implementations.Node;
import data.implementations.Quick_Access_Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Quick_Access_LocationDAOImpTest {

    private LocationDAO locationDAO;
    private NodeDAO nodeDAO;
    private FloorDAO floorDAO;
    private Quick_Access_LocationDAO quick_access_locationDAO;
    private Location location1;
    private Location location2;
    private Quick_Access_Location qaLocation1;
    private Quick_Access_Location qaLocation2;
    private Quick_Access_Location qaLocation4;

    @BeforeAll
    static void init(){
        DatabaseManager.createNewDatabase(null,true);
    }


    @BeforeEach
    void setUp() {

        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        quick_access_locationDAO = new Quick_Access_LocationDAOImp();
        floorDAO = new FloorDAOImp();
        Floor floor1 = new Floor(1,"1");
        Floor floor2 = new Floor(2,"2");
        floorDAO.insert(floor1);
        floorDAO.insert(floor2);
        locationDAO = new LocationDAOImp();
        location1 = new Location("1");
        location2 = new Location("2");
        locationDAO.insert(location1);
        locationDAO.insert(location2);
        nodeDAO = new NodeDAOImp();
        Node node1 = new Node(1,1,1,location1.getId());
        Node node2 = new Node(1,1,1,location1.getId());
        Node node3 = new Node(1,1,2,location2.getId());
        Node node4 = new Node(1,1,2,location2.getId());
        nodeDAO.insert(node1);
        nodeDAO.insert(node2);
        nodeDAO.insert(node3);
        nodeDAO.insert(node4);
        qaLocation1 = new Quick_Access_Location(location1.getId(),1);
        qaLocation4 = new Quick_Access_Location(location1.getId(),1);
        qaLocation2 = new Quick_Access_Location(location2.getId(),2);
        quick_access_locationDAO.insert(qaLocation1);
        quick_access_locationDAO.insert(qaLocation2);
        quick_access_locationDAO.insert(qaLocation4);
    }

    @Test
    void shouldGetOneQAL(){
        assertEquals(qaLocation1
                ,quick_access_locationDAO.getQuickAccessLocation(qaLocation1.getID()));
    }

    @Test
    void shouldDeleteQAL() {
        quick_access_locationDAO.delete(qaLocation1.getID());
        assertFalse(quick_access_locationDAO.getAllQuick_Access_Locations().contains(qaLocation1));
    }

    @Test
    void shouldGetAllQALOnFloor(){
        List<Quick_Access_Location> qals = quick_access_locationDAO.getAllQuick_Access_LocationsOnFloor(1);
        assertEquals(2,qals.size());
        assertTrue(qals.contains(qaLocation1));
    }

    @Test
    void shouldUpdate(){
        Quick_Access_Location qaLocation3 = new Quick_Access_Location(qaLocation2.getID()
                ,location2.getId()
                ,2);
        quick_access_locationDAO.update(qaLocation3);
        assertEquals(qaLocation2.getID(),quick_access_locationDAO.getQuickAccessLocation(qaLocation2.getID()).getID());
    }

    @Test
    void shouldGetAllQALsWithGivenType() {
        List<Quick_Access_Location> qals = quick_access_locationDAO.getAllQuick_Access_LocationsType(1);
        assertEquals(2,qals.size());
        assertTrue(qals.contains(qaLocation1));
        assertTrue(qals.contains(qaLocation4));

    }
}