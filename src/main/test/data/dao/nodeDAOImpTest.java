package data.dao;

import data.database.DatabaseManager;
import data.implementations.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class nodeDAOImpTest {
    NodeDAOImp nodeDAO;


    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        nodeDAO = new NodeDAOImp();
    }

    @Test
    void shouldInsertNode(){
        Node node = new Node(1,1,1,0,-1);
        Node node2 = new Node(2,2,3,4,5);
        nodeDAO.insert(node);
        nodeDAO.insert(node2);
    }

    @Test
    void shouldGetProperNode(){
        Node node = new Node(3,1,1,1,1);
        nodeDAO.insert(node);
        assertEquals(node,nodeDAO.getNode(3));
    }

    @Test
    void shouldDeleteNode() {
        Node node = new Node(4,1,1,1,1);
        nodeDAO.insert(node);
        nodeDAO.delete(4);
        assertNull(nodeDAO.getNode(4));
    }

    @Test
    void shouldUpdateNode(){
        Node node = new Node(5,1,1,10,-1);
        Node updatedNode = new Node(5,1,1,1,10);
        nodeDAO.insert(node);
        nodeDAO.update(updatedNode);
        assertEquals(updatedNode,nodeDAO.getNode(5));
    }

    @Test
    void shouldGetAllNodes(){
        Node sampleNode = new Node(6,6,6,6,6);
        Node sampleNode2 = new Node(7,7,7,7,7);
        nodeDAO.insert(sampleNode);
        nodeDAO.insert(sampleNode2);
        List<Node> nodes = nodeDAO.getAllNodes();
        System.out.println(Arrays.toString(nodes.toArray()));
        assertTrue(nodes.contains(sampleNode));
        assertTrue(nodes.contains(sampleNode2));
    }

    @Test
    void shouldGetAllNodesOnFloor() {
        int floor = 7;
        Node sampleNode = new Node(6,6,6,6,6);
        Node sampleNode2 = new Node(7,7,7,7,7);
        Node sampleNode3 = new Node(8,7,7,7,7);
        nodeDAO.insert(sampleNode);
        nodeDAO.insert(sampleNode2);
        nodeDAO.insert(sampleNode3);
        List<Node> nodes = nodeDAO.getAllNodesOnFloor(floor);
        for (Node node:nodes){
            assertEquals(floor,node.getFloor());
        }
        assertTrue(nodes.contains(sampleNode2));
        assertTrue(nodes.contains(sampleNode3));
    }
}