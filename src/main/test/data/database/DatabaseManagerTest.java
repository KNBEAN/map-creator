package data.database;


import data.dao.*;
import data.dao.interfaces.*;
import data.implementations.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseManagerTest {
    private FloorDAO floorDAO;
    private LocationDAO locationDAO;
    private NodeDAO nodeDAO;
    private EdgeDAO edgeDAO;
    private Location_TagDAO location_tagDAO;
    private Node node1, node2, node3, node4;
    private Location location1, location2, location3;
    private Edge edge1, edge2, edge3;
    private Floor floor1, floor2;
    private Location_Tag location_tag1, location_tag2, location_tag3;


    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase();
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
        floorDAO.insert(floor1 = new Floor(1, "floor1"));
        floorDAO.insert(floor2 = new Floor(2, "floor2"));
        locationDAO.insert(location1 = new Location("1"));
        locationDAO.insert(location2 = new Location("2"));
        locationDAO.insert(location3 = new Location(-1, "none", null));
        location_tagDAO.insert(location_tag1 = new Location_Tag("tag1", location1.getId()));
        location_tagDAO.insert(location_tag2 = new Location_Tag("tag2", location1.getId()));
        location_tagDAO.insert(location_tag3 = new Location_Tag("tag3", location2.getId()));
        nodeDAO.insert(node1 = new Node(1, 1, 1, location1.getId()));
        nodeDAO.insert(node2 = new Node(2, 2, 1, location1.getId()));
        nodeDAO.insert(node3 = new Node(3, 3, 2, location2.getId()));
        nodeDAO.insert(node4 = new Node(4, 4, 2, location2.getId()));
        edgeDAO.insert(edge1 = new Edge(node1.getId(), node2.getId(), 100));
        edgeDAO.insert(edge2 = new Edge(node3.getId(), node4.getId(), 100));
        edgeDAO.insert(edge3 = new Edge(node1.getId(), node4.getId(), 100));
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
        locationDAO.delete(location1.getId());
        List<Location_Tag> tags = location_tagDAO.getLocation_Tags(location1.getId());
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
                new Location(location2.getId()
                        , "new location"
                        , "new description"));
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(2);
        for (Node node : nodes) {
            assertEquals(location2.getDescription()
                    , locationDAO.getLocation(node.getLocationID()).getDescription());
        }

    }
}