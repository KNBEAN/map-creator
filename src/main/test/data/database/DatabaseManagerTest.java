package data.database;


import data.dao.*;
import data.dao.interfaces.*;
import data.implementations.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseManagerTest {
    private FloorDAO floorDAO;
    private LocationDAO locationDAO;
    private NodeDAO nodeDAO;
    private EdgeDAO edgeDAO;
    private Location_TagDAO location_tagDAO;
    private Quick_Access_LocationDAO quick_access_locationDAO;
    private Location location1;
    private Location location2;
    private Floor floor1;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase(null,false);
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        floorDAO = new FloorDAOImp();
        location_tagDAO = new Location_TagDAOImp();
        locationDAO = new LocationDAOImp();
        nodeDAO = new NodeDAOImp();
        edgeDAO = new EdgeDAOImp();
        floor1 = new Floor(1, "floor1");
        floorDAO.insert(floor1);
        Floor floor2 = new Floor(2, "floor2");
        floorDAO.insert(floor2);
        locationDAO.insert(location1 = new Location("1"));
        locationDAO.insert(location2 = new Location("2"));
        Location location3= new Location(-1, "none", null);
        locationDAO.insert(location3);
        Location_Tag location_tag1= new Location_Tag("tag1", 1);
        location_tagDAO.insert(location_tag1 );
        Location_Tag location_tag2= new Location_Tag("tag2", 2);
        location_tagDAO.insert(location_tag2 );
        Location_Tag location_tag3 = new Location_Tag("tag3", 3);
        location_tagDAO.insert(location_tag3);
        nodes = new ArrayList<Node>(){
            {
            add(new Node(1,1, 1, 1, 1)) ;
            add(new Node(2,2, 2, 1, 1));
            add(new Node(3,3, 3, 2, 2));
            add(new Node(4,4, 4, 2, 3));
            }
        };

        nodeDAO.insert(nodes);
        edges = new ArrayList<Edge>(){
            {
                add( new Edge(1, 2, 100));
                add( new Edge(2, 3, 100));
                add( new Edge(4, 3, 100));
            }
        };
        edgeDAO.insert(edges);
        quick_access_locationDAO = new Quick_Access_LocationDAOImp();
        Quick_Access_Location qaLocation1 = new Quick_Access_Location(1,1);
        Quick_Access_Location qaLocation2 = new Quick_Access_Location(2,2);
        Quick_Access_Location qaLocation3 = new Quick_Access_Location(2,2);
        quick_access_locationDAO.insert(qaLocation1);
        quick_access_locationDAO.insert(qaLocation2);
        quick_access_locationDAO.insert(qaLocation3);

    }


    @Test
    void shouldDeleteAllNodesAndEdgesOnFloor() {
        floorDAO.delete(1);
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(1);
        List<Edge> edges = edgeDAO.getAllEdgesOnFloor(1);
        assertTrue(nodes.isEmpty());
        assertTrue(edges.isEmpty());
    }

    @Test
    void shouldDeleteAllTagsOnLocationAndUpdateNodesToDefaultLocation() {
        locationDAO.delete(1);
        List<Location_Tag> tags = location_tagDAO.getLocation_Tags(1);
        assertTrue(tags.isEmpty());
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(1);
        for (Node node : nodes) {
            assertEquals(-1, node.getLocationID());
        }
    }

    @Test
    void shouldUpdateAllNodesOnFloor() {
        floor1 = new Floor(floor1.getFloors(), "UpdateFloor", "some path");
        floorDAO.update(floor1);
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(1);
        for (Node node : nodes) {
            assertEquals(floor1.getImagePath(), floorDAO.getFloor(node.getFloor()).getImagePath());
        }
    }

    @Test
    void shouldUpdateAllNodesLocationDescription() {
        locationDAO.update(location2 =
                new Location(2
                        , "new location"
                        , "new description"));
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(2);
        for (Node node : nodes) {
            assertEquals(location2.getDescription()
                    , locationDAO.getLocation(node.getLocationID()).getDescription());
        }

    }

    @Test
    void shouldSwapBase(){
        DatabaseManager.createNewDatabase("test2",false);
        assertTrue(floorDAO.getAllFloors().isEmpty());
        DatabaseManager.swapDatabaseConnection(null);
        assertTrue(!floorDAO.getAllFloors().isEmpty());
    }

    @Test
    void shouldDeleteAllQALsOnFloor() {
        floorDAO.delete(2);
        List<Quick_Access_Location> qals = quick_access_locationDAO.getAllQuick_Access_LocationsOnFloor(2);
        assertTrue(qals.isEmpty());
    }
}