package data.dao.interfaces;

import data.implementations.Edge;

import java.util.List;

public interface EdgeDAO {
    Edge getEdge(int id);
    void insert(Edge edge);
    void insert(List<Edge> edges);
    void delete(int from, int to);
    void update(Edge edge);
    List<Edge> getAllEdges();
    List<Edge> getAllEdgesOnFloor(int floor);
}
