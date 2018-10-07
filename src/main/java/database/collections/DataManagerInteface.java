package database.collections;

import database.implementations.*;

import java.util.ArrayList;

public interface DataManagerInteface {

    boolean addNode(Node node);
    void deleteNode(Node node);
    void updateNode(Node node);

    void addEdge(Edge edge);
    void deleteEdge(int id);
    void updateEdge(Edge edge);

    void addFloor(Floor floor);
    void deleteFloor(int floor);
    void updateFloor(Floor floor);

    void addLocation(Location location);
    void deleteLocation(int id);
    void updateLocation(Location location);

    void addLocation_Tag(Location_Tag location_tag);
    void deleteLocation_Tag(String tag);
    void updateLocation_Tag(Location_Tag location_tag);

    ArrayList<Node> getNodesArray();
    ArrayList<Edge> getEdgesArray();
    ArrayList<Location> getLocationArray();
    ArrayList<Location_Tag> getLocationTagArray();
    ArrayList<Floor> getFloorArray();

    ArrayList<Node> getNodesArrayOnFloor(int floor);
    ArrayList<Edge> getEdgesArrayOnFloor(int floor);

    Node getNode(int id);
    Edge getEdge(int id);
    Location getLocation(int id);
    Floor getFloor(int id);
    Location_Tag getLocation_Tag(String Tag);

}
