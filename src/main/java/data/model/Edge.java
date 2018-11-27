package data.model;

/**
 * This interface represents directional edge on the graph. Edge is a object that make connection
 * between nodes.
 */
public interface Edge {

    /**
     * Calculate length between node with from_id to node with
     * to_id
     * @return
     */
    int calculateLength(Node from, Node to);


    /**
     * ID of edge in base
     * @return
     */
    int getId();

    /**
     * ID of start point of edge
     * @return
     */
    int getFrom();

    /**
     * ID of end point of edge
     * @return
     */
    int getTo();

    /**
     * Length of edge.
     * @return
     */
    int getLength();

    Edge swapEnds();

}