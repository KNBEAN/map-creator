package database.collections;

import database.implementations.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void add() {
        assertTrue(nodeList.add(node1));
        assertFalse(nodeList.add(node1));
        assertTrue(nodeList.add(node2));
        assertTrue( nodeList.add(node3));
        assertTrue(nodeList.add(node4));
    }

    @Test
    void remove() {
        add();
        nodeList.remove(node4);
        assertNull(nodeList.get(node4.getId()));
    }

    @Test
    void update() {
        add();
        nodeList.update(node1,node4);
        assertEquals(node4,nodeList.get(node4.getId()));
    }

    @Test
    void get() {
        add();
        assertEquals(node2,nodeList.get(node2.getId()));

    }
}