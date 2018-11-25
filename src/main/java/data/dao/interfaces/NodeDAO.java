package data.dao.interfaces;

import data.implementations.Node;

import java.util.List;

public interface NodeDAO {
        /**
         * @param id
         * @return node if not exists returns null
         */
        Node getNode(int id);

        /**
         * @param node
         */
        void insert(Node node);

        /**
         * @param nodes
         */
        void insert(List<Node> nodes);

        /**
         * @param id
         */
        void delete(int id);

        /**
         * Updates x,y,floor,location_id
         * @param node - updates by id
         */
        void update(Node node);

        /**
         * @return List<Node>
         */
        List<Node> getAllNodes();

        /**
         * @param floor
         * @return List of Nodes on given floor
         */
        List<Node> getAllNodesOnFloor(int floor);

}
