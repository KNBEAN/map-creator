package database.model;

/**
 * This interface represents directional edge on the graph. Edge is a object that make connection
 * between nodes.
 */
public interface IEdge {

    /**
     * Calculate length between node with from_id to node with
     * to_id
     * @return
     */
    int calculateLength(INode from, INode to);


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

    IEdge swapEnds();

}