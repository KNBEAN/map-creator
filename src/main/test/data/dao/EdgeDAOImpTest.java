package data.dao;

import data.database.DatabaseManager;
import data.implementations.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDAOImpTest {
    EdgeDAO edgeDAO;
    NodeDAO nodeDAO;

    @BeforeEach
    void setUp() {
        DatabaseManager.dropTables();
        DatabaseManager.createTables();
        edgeDAO = new EdgeDAOImp();
        nodeDAO = new NodeDAOImp();
    }

    @Test
    void shouldInsertEdge(){
        Edge edge = new Edge(7,15,100);
        edgeDAO.insert(edge);
        edgeDAO.insert(edge);
    }

    @Test
    void shouldGetEdge() {
        Edge edge = new Edge(6,1,1,1);
        edgeDAO.insert(edge);
        assertEquals(edge,edgeDAO.getEdge(1));
    }
}