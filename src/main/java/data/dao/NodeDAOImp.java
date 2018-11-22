package data.dao;

import data.database.DatabaseManager;
import data.implementations.Node;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDAOImp implements NodeDAO {

    @Override
    public Node getNode(int id) {
        String sql = "SELECT id,x,y,floor,location_id FROM nodes WHERE id == ?";
        try( Connection connection = DatabaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            return wrapInNode(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Node wrapInNode(ResultSet resultSet) throws SQLException {
        Node node = null;
        while (resultSet.next()){
            node = new Node(
                     resultSet.getInt("id")
                    ,resultSet.getInt("x")
                    ,resultSet.getInt("y")
                    ,resultSet.getInt("floor")
                    ,resultSet.getInt("location_id"));
        }
        resultSet.close();
        return node;
    }

    private List<Node> wrapInNodes(ResultSet resultSet) throws SQLException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        while (resultSet.next()){
            node = new Node(
                    resultSet.getInt("id")
                    ,resultSet.getInt("x")
                    ,resultSet.getInt("y")
                    ,resultSet.getInt("floor")
                    ,resultSet.getInt("location_id"));
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public void insert(Node node) {
            String sql = "INSERT INTO nodes(id,x,y,floor,location_id) VALUES(?,?,?,?,?)";
            try( Connection connection = DatabaseManager.connect();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1,node.getId());
                pstmt.setInt(2,node.getX());
                pstmt.setInt(3,node.getY());
                pstmt.setInt(4,node.getFloor());
                pstmt.setInt(5,node.getLocationID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM nodes WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Node node) {
        String sql = "UPDATE nodes SET x = ? , y =?,floor=?,location_id=? WHERE id=?";
        try (   Connection connection = DatabaseManager.connect();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,node.getX());
            pstmt.setInt(2,node.getY());
            pstmt.setInt(3,node.getFloor());
            pstmt.setInt(4,node.getLocationID());
            pstmt.setInt(5,node.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Node> getAllNodes() {
        String sql = "SELECT id, x, y,floor,location_id FROM nodes";
        try(Connection connection = DatabaseManager.connect();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql)){
            return wrapInNodes(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Node> getAllNodesOnFloor(int floor) {
        String sql = "SELECT id, x, y,floor,location_id FROM nodes WHERE floor = ?";
        try(Connection connection = DatabaseManager.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,floor);
            ResultSet resultSet = pstmt.executeQuery();
            return wrapInNodes(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
