package data.dao;

import data.implementations.Edge;

import java.util.List;

public interface EdgeDAO {
    Edge getEdge(int id);
    void insert(Edge edge);
    void delete(int id);
    void update(Edge edge);
    List<Edge> getAllEdges();
    List<Edge> getAllEdgesOnFloor(int floor);
}
