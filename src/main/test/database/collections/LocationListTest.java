package database.collections;

import database.implementations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationListTest {

    private LocationList locationList;
    private Location location1;
    private Location location2;
    private Location location3;
    private Location location4 ;

    @BeforeEach
    void setUp() {
        locationList = new LocationList();
        location1 = new Location("one", "");
        location2 = new Location("two", "");
        location3 = new Location("one", "two");
        location4 = new Location("one","desc");
    }

    @Test
    void add() {
       assertTrue( locationList.add(location1));
       assertTrue( locationList.add(location2));
       assertFalse(locationList.add(location2));
       assertFalse(locationList.add(location3));
    }

    @Test
    void remove() {
        locationList.remove(location2);
        assertNull(locationList.get(location2.getId()));
    }

    @Test
    void update() {

        locationList.update(location1,location4);
    }

    @Test
    void get() {
        add();
        assertNull(locationList.get(location3.getId()));
        assertEquals(location1,locationList.get(location1.getId()));
    }
}