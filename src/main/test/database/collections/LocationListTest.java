package database.collections;

import database.implementations.Location;
import database.implementations.Node;
import database.model.ILocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LocationListTest {

    private LocationList locationList;
    private NodeList nodeList;
    private Location location1;
    private Location location2;
    private Location location3;
    private Location location4 ;

    @BeforeEach
    void setUp() {
        nodeList = new NodeList();
        locationList = new LocationList();
        location1 = new Location("one", "");
        location2 = new Location("two", "");
        location3 = new Location("one", "two");
        location4 = new Location("one","desc");
    }

    @Test
    void testAdd() {
       assertTrue( locationList.add(location1));
       assertTrue( locationList.add(location2));
       assertFalse(locationList.add(location2));
       locationList.add(location4);
       assertFalse(locationList.add(location3));
    }

    @Test
    void testRemove() {
        locationList.remove(location2);
        assertNull(locationList.get(location2.getId()));
    }

    @Test
    void testUpdate() {

        locationList.update(location1,location4);
    }

    @Test
    void testGet() {
        testAdd();
        assertNull(locationList.get(location3.getId()));
        assertEquals(location1,locationList.get(location1.getId()));
    }

    @Test
    void testGetAllOnFloor() {
        locationList.add(location1);
        locationList.add(location2);
        locationList.add(location3 = new Location("three", "two"));
        locationList.add(location4 = new Location("four", "two"));
        assertFalse(locationList.add(location3));
        Node node1 = new Node(1,1,1,location1.getId());
        Node node2 = new Node(1,1,1,location2.getId());
        Node node3 = new Node(1,1,2,location3.getId());
        Node node4 = new Node(1,1,2,location4.getId());
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        ArrayList<ILocation> allOnFloor = locationList.getAllOnFloor(1);
        assertTrue(allOnFloor.size()==2);
        assertTrue(allOnFloor.get(0).getId() == location1.getId());
        assertTrue(allOnFloor.get(1).getId() == location2.getId());
    }
}