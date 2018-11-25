package data.dao.interfaces;

import data.implementations.Edge;

import java.util.List;

public interface EdgeDAO {
    /**
     * @param id
     * @return only one edge, if not exists returns null
     */
    Edge getEdge(int id);

    /**
     * Insert edge AND insert it's swapped copy
     * @param edge
     */
    void insert(Edge edge);

    /**
     * @param edges
     */
    void insert(List<Edge> edges);

    /**
     * Deletes also swapped copy of edge
     * @param from
     * @param to
     */
    void delete(int from, int to);

    /**
     * Updates edge parameters by id in given edge. Changes parameters like from,to,length.
     * @param edge
     */
    void update(Edge edge);

    /**
     * @return List<Edge>
     */
    List<Edge> getAllEdges();

    /**
     * @param floor
     * @return Edges and edges that one end ends on given floor.
     */
    List<Edge> getAllEdgesOnFloor(int floor);
}
