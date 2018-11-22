package data.collections;

import data.implementations.Node;
import data.model.INode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeListTest {

    NodeList nodeList;
    Node node1;
    Node node2;
    Node node3;
    Node node4;

    @BeforeEach
    void setUp() {
        nodeList = new NodeList();
        node1 = new Node(1,1,1,1);
        node2 = new Node(1,2,1,2);
        node3 = new Node(1,2,3);
        node4= new Node(2,2,1);
    }

    @Test
    void testAdd() {
        assertTrue(nodeList.add(node1));
        assertFalse(nodeList.add(node1));
        assertTrue(nodeList.add(node2));
        assertTrue( nodeList.add(node3));
        assertTrue(nodeList.add(node4));
    }

    @Test
    void testRemove() {
        testAdd();
        nodeList.remove(node4);
        assertNull(nodeList.get(node4.getId()));
    }

    @Test
    void testUpdate() {
        testAdd();
        nodeList.update(node1,node4);
        assertEquals(node4,nodeList.get(node4.getId()));
    }

    @Test
    void testGet() {
        testAdd();
        assertEquals(node2,nodeList.get(node2.getId()));

    }
}