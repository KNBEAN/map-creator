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

    @BeforeAll
    static void createBase() {
        DatabaseManager.createNewDatabase();
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
        node = new Node(1, 1, 1);
        nodeDAO.insert(node);
    }


    @Test
    void shouldGetProperNode() {
        assertEquals(node, nodeDAO.getNode(node.getId()));
    }

    @Test
    void shouldDeleteNode() {
        nodeDAO.delete(node.getId());
        assertNull(nodeDAO.getNode(node.getId()));
    }

    @Test
    void shouldUpdateNode() {
        Node updatedNode = new Node(node.getId(), 1, 1, 1, -1);
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
        Node sampleNode3 = new Node(8, 7, 1);
        nodeDAO.insert(sampleNode);
        nodeDAO.insert(sampleNode2);
        nodeDAO.insert(sampleNode3);
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(1);
        for (Node node : nodes) {
            assertEquals(1, node.getFloor());
        }
        assertTrue(nodes.contains(sampleNode2));
        assertTrue(nodes.contains(sampleNode3));
        assertTrue(nodes.contains(sampleNode));
    }

    @Test
    void shouldInsertAllNodes() {
        Node sampleNode = new Node(6, 6, 1);
        Node sampleNode2 = new Node(7, 7, 1);
        Node sampleNode3 = new Node(8, 7, 1);
        List<Node> nodes1 = new ArrayList<Node>() {{
            add(sampleNode);
            add(sampleNode2);
            add(sampleNode3);
        }};
        nodeDAO.insert(nodes1);
        nodes1 = nodeDAO.getAllNodes();
        assertTrue(nodes1.contains(sampleNode));
        assertTrue(nodes1.contains(sampleNode2));
        assertTrue(nodes1.contains(sampleNode3));
    }
}