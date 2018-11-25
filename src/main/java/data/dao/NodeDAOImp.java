package data.dao;

import data.dao.interfaces.NodeDAO;
import data.database.DatabaseManager;
import data.implementations.Node;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDAOImp implements NodeDAO {

    @Override
    public void insert(List<Node> nodes) {
        String sql = "INSERT INTO nodes(id,x,y,floor,location_id) VALUES(?,?,?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Node node : nodes) {
                wrapNodeInPreparedStatement(preparedStatement, node);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void wrapNodeInPreparedStatement(PreparedStatement pstmt, Node node) throws SQLException {
        pstmt.setInt(1, node.getId());
        pstmt.setInt(2, node.getX());
        pstmt.setInt(3, node.getY());
        pstmt.setInt(4, node.getFloor());
        pstmt.setInt(5, node.getLocationID());
        pstmt.executeUpdate();
    }

    @Override
    public Node getNode(int id) {
        String sql = "SELECT id,x,y,floor,location_id FROM nodes WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInNode(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Node wrapInNode(ResultSet resultSet) throws SQLException {
        Node node = null;
        while (resultSet.next()) {
            node = new Node(
                    resultSet.getInt("id")
                    , resultSet.getInt("x")
                    , resultSet.getInt("y")
                    , resultSet.getInt("floor")
                    , resultSet.getInt("location_id"));
        }
        resultSet.close();
        return node;
    }

    private List<Node> wrapInNodes(ResultSet resultSet) throws SQLException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        while (resultSet.next()) {
            node = new Node(
                    resultSet.getInt("id")
                    , resultSet.getInt("x")
                    , resultSet.getInt("y")
                    , resultSet.getInt("floor")
                    , resultSet.getInt("location_id"));
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public void insert(Node node) {
        String sql = "INSERT INTO nodes(id,x,y,floor,location_id) VALUES(?,?,?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            wrapNodeInPreparedStatement(preparedStatement, node);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM nodes WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Node node) {
        String sql = "UPDATE nodes SET x = ? , y =?,floor=?,location_id=? WHERE id=?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, node.getX());
            preparedStatement.setInt(2, node.getY());
            preparedStatement.setInt(3, node.getFloor());
            preparedStatement.setInt(4, node.getLocationID());
            preparedStatement.setInt(5, node.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Node> getAllNodes() {
        String sql = "SELECT id, x, y,floor,location_id FROM nodes";
        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return wrapInNodes(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Node> getAllNodesOnFloor(int floor) {
        String sql = "SELECT id, x, y,floor,location_id FROM nodes WHERE floor = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInNodes(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
