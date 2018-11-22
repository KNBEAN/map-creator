package data.collections;

import data.implementations.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeListTest {
    EdgeList edgeList;
    Edge one;
    Edge two;

    @BeforeEach
    void setUp() {
        edgeList = new EdgeList();
        one = new Edge(1,1,1);
        two = new Edge(1,2,1);
    }

    @Test
    void testAdd() {
        edgeList.add(one);
        edgeList.add(two);
    }

    @Test
    void testGet() {
       testAdd();
       assertTrue((edgeList.get(one.getId()).equals(one)));
    }

    @Test
    void testUpdate() {
        testAdd();
        Edge three = new Edge(1,2,3);
        edgeList.update(one,three);
        assertEquals(edgeList.get(three.getId()),three);
        assertNull(edgeList.get(one.getId()));
    }
}