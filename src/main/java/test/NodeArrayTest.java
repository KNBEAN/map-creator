package test;

import database.collections.NodeArray;
import database.implementations.Edge;
import database.implementations.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeArrayTest {

    NodeArray nodeArray;

    @BeforeEach
    void setUp() {
        nodeArray = new NodeArray();
    }

    @Test
    public void testForAddingDuplicates(){
        Node x = new Node(1,1,1);
        nodeArray.add(x);
        assertFalse(nodeArray.add(x));
    }
    @Test
    public void testForGettingArray(){
        Node x1 = new Node(1,1,1);
        Node x2 = new Node(2,2,2);
        nodeArray.add(x1);
        nodeArray.add(x2);
        ArrayList<Node> nodeArrayList = new ArrayList<Node> (nodeArray);
        assertEquals(nodeArrayList.get(0), x2);
        assertEquals(nodeArrayList.get(1), x1);
    }
    @Test
    public void testAddingOtherTypes(){
        Node x1 = new Node(1,1,1);
        assertTrue(nodeArray.add(x1));
        Edge x2 = new Edge(1,1,20);
        assertFalse(nodeArray.add(x2));
    }

}