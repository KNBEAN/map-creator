package database.collections;

import database.implementations.Edge;
import database.implementations.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    DataManager dataManager;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        dataManager = new DataManager();
    }

    @Test
    public void shouldHaveOnlyOne(){
        dataManager.addNode(new Node(1,1,0));
        assertFalse(dataManager.addNode(new Node(1,1,0)));
    }

    @Test
    void swapEndsWorks() {
        Edge edge = new Edge(0,1,2);
        dataManager.addEdge(edge);
        Edge edge2 = dataManager.getEdgesArray().get(1);
        assertTrue(edge.getFrom() == edge2.getTo());
        assertTrue(edge.getTo() == edge2.getFrom());
    }
}