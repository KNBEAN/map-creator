package database.collections;

import database.implementations.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorListTest {

    Floor one;
    Floor two;

    @Test
    void testGet() {
    }

    private FloorList floorList;

    @BeforeEach
    void setUp() {
        floorList = new FloorList();
    }

    @Test
    void testAdd() {
        one = new Floor(1,"1");
        two = new Floor(2,"2");

    }

    @Test
    void testUpdate() {
        testAdd();
        Floor three = new Floor(1,"parter");
        floorList.update(one,three);
        assertTrue (floorList.get(three.getFloors())==three);
    }

    @Test
    void testRemove() {
        testAdd();
        floorList.remove(one);
        assertNull(floorList.get(one.getFloors()));
    }

}