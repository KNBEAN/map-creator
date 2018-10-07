package database.collections;

import database.implementations.*;

import java.util.ArrayList;

public class DataManager implements DataManagerInteface {
    private ArrayList<Node> nodeArrayList;
    private ArrayList<Edge> edgeArrayList;
    private ArrayList<Floor> floorArrayList;
    private ArrayList<Location> locationArrayList;
    private ArrayList<Location_Tag> location_tagArrayList;

    public DataManager(){
        nodeArrayList = new ArrayList<>();
        edgeArrayList = new ArrayList<>();
        floorArrayList = new ArrayList<>();
        locationArrayList = new ArrayList<>();
        location_tagArrayList = new ArrayList<>();
    }


    @Override
    public boolean addNode(Node node) {
        if (nodeArrayList.contains(node)) return false;
        nodeArrayList.add(node);
        return true;
    }

    @Override
    public void deleteNode(Node node) {
        nodeArrayList.remove(node);
    }

    @Override
    public void updateNode(Node node) {
        for (Node nodeIn : nodeArrayList){
            if (node.getID() == nodeIn.getID()){
                deleteNode(nodeIn);
                addNode(node);
                return;
            }
        }
    }

    @Override
    public void addEdge(Edge edge) {
        if (edgeArrayList.contains(edge)) return;
        edgeArrayList.add(edge);
        edgeArrayList.add(new Edge(edge.getTo(),edge.getFrom(),edge.getLength()));
    }

    @Override
    public void deleteEdge(int id) {
        for (Edge edgeIn : edgeArrayList){
            if (edgeIn.getId() == id) {
                Edge copy = new Edge(edgeIn.getTo(),edgeIn.getFrom(),edgeIn.getLength());
                edgeArrayList.remove(edgeIn);
                for (Edge edgeInCopy :edgeArrayList){
                    if (edgeInCopy.getLength()==copy.getLength()
                            && edgeInCopy.getFrom() == copy.getFrom()
                            && edgeInCopy.getTo() == copy.getTo()){
                        edgeArrayList.remove(edgeInCopy);
                        return;
                    }
                }
            }
            }
    }

    @Override
    public void updateEdge(Edge edge) {
        for (Edge edgeIn : edgeArrayList){
            if (edgeIn.getId() == edge.getId()) {
                Edge edgeInCopy = edgeIn;
                edgeArrayList.remove(edgeIn);
                edgeInCopy.swapEnds();
                for (Edge edgeIn2 : edgeArrayList){
                    if (edgeIn2.getTo() == edgeInCopy.getFrom()
                            && edgeIn2.getFrom() == edgeInCopy.getTo()){
                        edgeArrayList.remove(edgeIn2);
                        edgeArrayList.add(edge);
                        edge.swapEnds();
                        edgeArrayList.add(edge);
                    }
                }
            }
        }
    }

    @Override
    public void addFloor(Floor floor) {
        if (floorArrayList.contains(floor)) return;
        floorArrayList.add(floor);
    }

    @Override
    public void deleteFloor(int floor) {
        for (Floor floorIn : floorArrayList){
            if (floorIn.getFloors() == floor){
                floorArrayList.remove(floorIn);
                return;
            }
        }

    }

    @Override
    public void updateFloor(Floor floor) {
        for (Floor floorIn : floorArrayList){
            if (floorIn.getFloors() == floor.getFloors() ){
                deleteFloor(floorIn.getFloors());
                addFloor(floorIn);
            }
        }
    }

    @Override
    public void addLocation(Location location) {
        if (locationArrayList.contains(location)) return;
        locationArrayList.add(location);
    }

    @Override
    public void deleteLocation(int id) {
        for (Location location : locationArrayList){
            if (location.getId() == id) {
                locationArrayList.remove(location);
                return;
            }
        }
    }

    @Override
    public void updateLocation(Location location) {
            for (Location locationIn : locationArrayList){
                if (locationIn.getId() == location.getId()){
                    locationArrayList.remove(locationIn);
                    locationArrayList.add(location);
                    return;
                }
            }
    }

    @Override
    public void addLocation_Tag(Location_Tag location_tag) {
        if (location_tagArrayList.contains(location_tag)) return;
        location_tagArrayList.add(location_tag);
    }

    @Override
    public void deleteLocation_Tag(String tag) {
        for (Location_Tag location_tag : location_tagArrayList){
            if (location_tag.getTag() == tag){
                location_tagArrayList.remove(location_tag);
            }
        }
    }

    @Override
    public void updateLocation_Tag(Location_Tag location_tag) {
        for (Location_Tag location_tagIn : location_tagArrayList){
            if (location_tagIn.getTag() == location_tag.getTag()){
                location_tagArrayList.remove(location_tagIn);
                location_tagArrayList.add(location_tag);
            }
        }
    }

    @Override
    public ArrayList<Node> getNodesArray() {
        return nodeArrayList;
    }

    @Override
    public ArrayList<Edge> getEdgesArray() {
        return edgeArrayList;
    }

    @Override
    public ArrayList<Location> getLocationArray() {
        return locationArrayList;
    }

    @Override
    public ArrayList<Location_Tag> getLocationTagArray() {
        return location_tagArrayList;
    }

    @Override
    public ArrayList<Floor> getFloorArray() {
        return floorArrayList;
    }

    @Override
    public ArrayList<Node> getNodesArrayOnFloor(int floor) {
        ArrayList<Node> nodesOnFloor = new ArrayList<>();
        for (Node node: nodeArrayList){
            if (node.getFloor() == floor){
                nodesOnFloor.add(node);
            }
        }
        return nodesOnFloor;
    }

    @Override
    public ArrayList<Edge> getEdgesArrayOnFloor(int floor) {
        ArrayList<Edge> edgesOnFloor = new ArrayList<>();
        ArrayList<Node> nodes = getNodesArrayOnFloor(floor);
        for (Node node : nodes){
            for (Edge edge : edgeArrayList){
                if (edge.getFrom() == node.getID() && edge.getTo()== node.getID()){
                    edgesOnFloor.add(edge);
                }
            }
        }
        return edgesOnFloor;
    }

    @Override
    public Node getNode(int id) {
        for (Node node: nodeArrayList){
            if (node.getID() == id){
                return node;
            }
        }
        return null;
    }

    @Override
    public Edge getEdge(int id) {
        for (Edge edge: edgeArrayList){
            if (edge.getId() == id) return edge;
        }
        return null;
    }

    @Override
    public Location getLocation(int id) {
        for (Location location: locationArrayList){
            if (location.getId() == id) return location;
        }
        return null;
    }

    @Override
    public Floor getFloor(int id) {
        for (Floor floor:floorArrayList){
            if (floor.getFloors()==id) return floor;
        }
        return null;
    }

    @Override
    public Location_Tag getLocation_Tag(String tag) {
        for (Location_Tag location_tag:location_tagArrayList){
            if (location_tag.getTag().equals(tag)) return location_tag;
        }
        return null;
    }

}
