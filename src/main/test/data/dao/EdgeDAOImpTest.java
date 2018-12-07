package data.dao;

import data.dao.interfaces.EdgeDAO;
import data.dao.interfaces.FloorDAO;
import data.dao.interfaces.LocationDAO;
import data.dao.interfaces.NodeDAO;
import data.database.DatabaseManager;
import data.implementations.Edge;
import data.implementations.Floor;
import data.implementations.Location;
import data.implementations.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDAOImpTest {
    private EdgeDAO edgeDAO;
    private NodeDAO nodeDAO;
    private FloorDAO floorDAO;
    private LocationDAO locationDAO;
    private Node node1;
    private Node node2;
    private Floor floor1;
    private Floor floor2;
    private Location location1;
    private Edge edge1;

    @BeforeAll
    static void init() {
        DatabaseManager.createNewDatabase(null,false);
    }

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        edgeDAO = new EdgeDAOImp();
        nodeDAO = new NodeDAOImp();
        floorDAO = new FloorDAOImp();
        locationDAO = new LocationDAOImp();
        floorDAO.insert(floor1 = new Floor(1, "1"));
        locationDAO.insert(location1 = new Location(-1, "1", null));
        nodeDAO.insert(node1 = new Node(1, 1, 1));
        nodeDAO.insert(node2 = new Node(2, 2, 1));
        edge1 = new Edge(1,1, 2, 100);
        edgeDAO.insert(edge1);
    }


    @Test
    void shouldGetEdgeAndSwapCopy() {
        assertEquals(edge1, edgeDAO.getEdge(edge1.getId()));

    }

    @Test
    void shouldDeleteEdgeAndHisSwapCopy() {
        edgeDAO.delete(1, 2);
        assertNull(edgeDAO.getEdge(edge1.getId()));
        assertTrue(edgeDAO.getAllEdges().isEmpty());
    }

    @Test
    void shouldUpdateEdge() {
        nodeDAO.insert(new Node(3, 3, 1));
        edge1 = new Edge(1, 3, 2, 200);
        edgeDAO.update(edge1);
        assertEquals(edge1, edgeDAO.getEdge(1));
    }

    @Test
    void shouldReturnAllEdgesList() {
        nodeDAO.insert(new Node(3, 3, 1));
        nodeDAO.insert(new Node(4, 4, 1));
        Edge edge1 = new Edge(7,3, 4, 300);
        Edge edge2 = new Edge(3,4, 1, 400);
        edgeDAO.insert(edge1);
        edgeDAO.insert(edge2);
        List<Edge> edges = edgeDAO.getAllEdges();
        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
        assertEquals(6, edges.size());
    }

    @Test
    void shouldReturnAllEdgesOnFloor() {
        floorDAO.insert(new Floor(2, "2"));
        nodeDAO.insert( new Node(3, 3, 2));
        nodeDAO.insert(new Node(4, 4, 2));
        Edge edge1 = new Edge(3, 4, 300);
        Edge edge2 = new Edge(4, 1, 400);
        edgeDAO.insert(edge1);
        edgeDAO.insert(edge2);
        assertEquals(4, edgeDAO.getAllEdgesOnFloor(1).size());
        assertEquals(4, edgeDAO.getAllEdgesOnFloor(2).size());
    }


}