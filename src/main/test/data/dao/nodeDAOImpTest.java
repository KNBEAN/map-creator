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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class nodeDAOImpTest {
    NodeDAO nodeDAO;
    FloorDAO floorDAO;
    LocationDAO locationDAO;
    Node node;
    ArrayList<Node> nodes;

    @BeforeAll
    static void createBase() {
        DatabaseManager.createNewDatabase(null,false);
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        nodeDAO = new NodeDAOImp();
        floorDAO = new FloorDAOImp();
        locationDAO = new LocationDAOImp();
        floorDAO.insert(new Floor(1, "1"));
        locationDAO.insert(new Location(-1, "null", null));
        nodes = new ArrayList<Node>(){
            {
                add(new Node(1,1,1, 1,-1)) ;
                add(new Node(2,2,2, 1,-1));
                add(new Node(3,3,3, 1,-1));
                add(new Node(4,4,4, 1,-1));
            }
        };
        nodeDAO.insert(nodes);

    }


    @Test
    void shouldGetProperNode() {
        assertEquals(nodes.get(2), nodeDAO.getNode(3));
    }

    @Test
    void shouldDeleteNode() {
        nodeDAO.delete(0);
        assertNull(nodeDAO.getNode(0));
    }

    @Test
    void shouldUpdateNode() {
        Node updatedNode = new Node(1, 1, 1, 1, -1);
        nodeDAO.update(updatedNode);
        assertEquals(updatedNode, nodeDAO.getNode(updatedNode.getId()));
    }

    @Test
    void shouldGetAllNodes() {
        Node sampleNode = new Node(6, 6, 1);
        Node sampleNode2 = new Node(7, 7, 1);
        nodeDAO.insert(sampleNode);
        nodeDAO.insert(sampleNode2);
        List<Node> nodes = nodeDAO.getAllNodes();
        System.out.println(Arrays.toString(nodes.toArray()));
        assertTrue(nodes.contains(sampleNode));
        assertTrue(nodes.contains(sampleNode2));
    }

    @Test
    void shouldGetAllNodesOnFloor() {
        Node sampleNode = new Node(6, 6, 1);
        Node sampleNode2 = new Node(7, 7, 1);
        floorDAO.insert(new Floor(2,"2"));
        Node sampleNode3 = new Node(8, 7, 2);
        nodeDAO.insert(sampleNode);
        nodeDAO.insert(sampleNode2);
        nodeDAO.insert(sampleNode3);
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(1);
        for (Node node : nodes) {
            assertEquals(1, node.getFloor());
        }
        assertTrue(nodes.contains(sampleNode2));
        assertFalse(nodes.contains(sampleNode3));
        assertTrue(nodes.contains(sampleNode));
    }

   
}