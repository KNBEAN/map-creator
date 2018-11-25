package data.dao;

import data.dao.interfaces.FloorDAO;
import data.dao.interfaces.LocationDAO;
import data.dao.interfaces.Location_TagDAO;
import data.dao.interfaces.NodeDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;
import data.implementations.Node;
import data.implementations.Location;
import data.implementations.Location_Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    static void init(){
        DatabaseManager.createNewDatabase();
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
        location_tag1 = new Location_Tag("tag1",location1.getId());
        location_tag2 = new Location_Tag("tag2",location2.getId());
        location_tagDAO.insert(location_tag1);
        location_tagDAO.insert(location_tag2);
    }


    @Test
    void shouldGetLocation_Tag() {

        List<Location_Tag> location_tags1 = location_tagDAO.getLocation_Tags(location1.getId());
        List<Location_Tag> location_tags2 = location_tagDAO.getLocation_Tags(location2.getId());
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
        floorDAO.insert(new Floor(1,"1",null));
        floorDAO.insert(new Floor(2,"2",null));
        Location_Tag location_tag3= new Location_Tag("3",location2.getId());
        location_tagDAO.insert(location_tag3);
        nodeDAO = new NodeDAOImp();
        Node node1 = new Node(1,1,1,location1.getId());
        Node node2 = new Node(1,1,2,location2.getId());
        Node node3 = new Node(1,2,2,location2.getId());
        nodeDAO.insert(node1);
        nodeDAO.insert(node2);
        nodeDAO.insert(node3);
        List<Location_Tag> location_tags1 = location_tagDAO.getAllLocations_TagsOnFloor(1);
        List<Location_Tag> location_tags2 = location_tagDAO.getAllLocations_TagsOnFloor(2);
        assertEquals(1, location_tags1.size());
        assertEquals(2, location_tags2.size());

    }

    @Test
    void shouldInsertAllLocationTags(){
        Location_Tag location_tag3= new Location_Tag("4",location2.getId());
        Location_Tag location_tag4= new Location_Tag("5",location2.getId());
        Location_Tag location_tag5= new Location_Tag("6",location1.getId());
        List<Location_Tag> location_tags1 = new ArrayList<Location_Tag>(){{
            add(location_tag3);
            add(location_tag4);
            add(location_tag5);}};
        location_tagDAO.insert(location_tags1);
        List<Location_Tag> location_tags2 = location_tagDAO.getAllLocations_Tag();
        assertTrue(location_tags2.contains(location_tag3));
        assertTrue(location_tags2.contains(location_tag4));
        assertTrue(location_tags2.contains(location_tag5));

    }
}