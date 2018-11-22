package data.collections;

import data.implementations.Location_Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Location_TagListTest {

    private Location_TagList tagList;
    private Location_Tag location_tag1;
    private Location_Tag location_tag2;
    private Location_Tag location_tag3;
    private Location_Tag location_tag4;
    @BeforeEach
    void setUp() {
        tagList = new Location_TagList();
        location_tag1 = new Location_Tag("one",1);
        location_tag2 = new Location_Tag("two", 1);
        location_tag3 = new Location_Tag("one",2);
        location_tag4 = new Location_Tag("three",2);
    }

    @Test
    void add() {
        assertTrue(tagList.add(location_tag1));
        assertTrue(tagList.add(location_tag2));
        assertFalse(tagList.add(location_tag3));
        assertTrue(tagList.add(location_tag4));
    }

    @Test
    void remove() {
        tagList.remove(location_tag4);
        assertNull(tagList.get("three"));
    }

    @Test
    void update() {
        Location_Tag location_tag = new Location_Tag("four",1);
        tagList.update(location_tag1,location_tag);
        assertTrue(tagList.get("four").equals(location_tag));
    }

    @Test
    void get() {
        add();
      assertNotNull(tagList.get("one"));
      assertEquals(location_tag1,tagList.get("one"));
       assertNull(tagList.get("typo"));
    }
}