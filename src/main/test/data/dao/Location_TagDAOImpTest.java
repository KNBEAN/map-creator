package data.dao;

import data.dao.interfaces.FloorDAO;
import data.dao.interfaces.LocationDAO;
import data.dao.interfaces.Location_TagDAO;
import data.dao.interfaces.NodeDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;
import data.implementations.Location;
import data.implementations.Location_Tag;
import data.implementations.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Location_TagDAOImpTest {

    private LocationDAO locationDAO;
    private Location_TagDAO location_tagDAO;
    private FloorDAO floorDAO;
    private NodeDAO nodeDAO;
    private Location location1;
    private Location location2;
    private Location_Tag location_tag1;
    private Location_Tag location_tag2;

    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase(null,true);
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        location_tagDAO = new Location_TagDAOImp();
        locationDAO = new LocationDAOImp();
        floorDAO = new FloorDAOImp();
        location1 = new Location("1");
        location2 = new Location("2");
        locationDAO.insert(location1);
        locationDAO.insert(location2);
        location_tag1 = new Location_Tag("tag1", 1);
        location_tag2 = new Location_Tag("tag2", 2);
        location_tagDAO.insert(location_tag1);
        location_tagDAO.insert(location_tag2);
    }


    @Test
    void shouldGetLocation_Tag() {

        List<Location_Tag> location_tags1 = location_tagDAO.getLocation_Tags(1);
        List<Location_Tag> location_tags2 = location_tagDAO.getLocation_Tags(2);
        assertTrue(location_tags1.contains(location_tag1));
        assertTrue(location_tags2.contains(location_tag2));
    }

    @Test
    void shouldDeleteAllLocation_Tags() {
        location_tagDAO.delete(location_tag1.getTag());
        location_tagDAO.delete(location_tag2.getTag());
        assertTrue(location_tagDAO.getAllLocations_Tag().isEmpty());
    }

    @Test
    void shouldGetAllLocation_Tag() {
        assertEquals(2, location_tagDAO.getAllLocations_Tag().size());
    }

    @Test
    void shouldGetAllLocation_TagsOnFloor() {
        floorDAO.insert(new Floor(1, "1", null));
        floorDAO.insert(new Floor(2, "2", null));
        Location_Tag location_tag3 = new Location_Tag("3", 2);
        location_tagDAO.insert(location_tag3);
        nodeDAO = new NodeDAOImp();
        Node node1 = new Node(1, 1, 1, 1);
        Node node2 = new Node(1, 1, 2, 2);
        Node node3 = new Node(1, 2, 2, 2);
        nodeDAO.insert(node1);
        nodeDAO.insert(node2);
        nodeDAO.insert(node3);
        List<Location_Tag> location_tags1 = location_tagDAO.getAllLocations_TagsOnFloor(1);
        List<Location_Tag> location_tags2 = location_tagDAO.getAllLocations_TagsOnFloor(2);
        assertEquals(1, location_tags1.size());
        assertEquals(2, location_tags2.size());

    }


}