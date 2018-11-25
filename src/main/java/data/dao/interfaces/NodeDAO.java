package data.dao.interfaces;

import data.implementations.Node;

import java.util.List;

public interface NodeDAO {
        Node getNode(int id);
        void insert(Node node);
        void insert(List<Node> nodes);
        void delete(int id);
        void update(Node node);
        List<Node> getAllNodes();
        List<Node> getAllNodesOnFloor(int floor);

}
