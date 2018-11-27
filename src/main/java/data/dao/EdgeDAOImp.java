package data.dao;

import data.dao.interfaces.EdgeDAO;
import data.database.DatabaseManager;
import data.implementations.Edge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeDAOImp implements EdgeDAO {

    @Override
    public Edge getEdge(int id) {
        String sql = "SELECT * FROM edges WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInEdge(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Edge wrapInEdge(ResultSet resultSet) throws SQLException {
        Edge edge = null;
        while (resultSet.next()) {
            edge = new Edge(
                    resultSet.getInt("id")
                    , resultSet.getInt("from")
                    , resultSet.getInt("to")
                    , resultSet.getInt("length"));
        }
        resultSet.close();
        return edge;
    }

    private List<Edge> wrapInEdges(ResultSet resultSet) throws SQLException {
        List<Edge> edges = new ArrayList<>();
        while (resultSet.next()) {
            edges.add(new Edge(
                    resultSet.getInt("id")
                    , resultSet.getInt("from")
                    , resultSet.getInt("to")
                    , resultSet.getInt("length")));
        }
        return edges;
    }

    @Override
    public void insert(List<Edge> edges) {
        String sql = "INSERT INTO edges(id,[from],[to],[length]) VALUES(?,?,?,?)";
        int row_count = 0;
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Edge edge : edges) {
                setPrepStatementParams(edge, preparedStatement);
                row_count = preparedStatement.executeUpdate();
                if (row_count != 0) {
                    edge = edge.swapEnds();
                    setPrepStatementParams(edge, preparedStatement);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insert(Edge edge) {
        String sql = "INSERT INTO edges(id,[from],[to],[length]) VALUES(?,?,?,?)";
        int row_count = 0;
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setPrepStatementParams(edge, preparedStatement);
            row_count = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (row_count != 0)
                try (Connection connection = DatabaseManager.connect();
                     PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    edge = edge.swapEnds();
                    setPrepStatementParams(edge, pstmt);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
        }
    }

    private void setPrepStatementParams(Edge edge, PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, edge.getId());
        pstmt.setInt(2, edge.getFrom());
        pstmt.setInt(3, edge.getTo());
        pstmt.setInt(4, edge.getLength());
    }

    @Override
    public void delete(int from, int to) {
        String sql = "DELETE FROM edges WHERE [from] = ? AND [to] = ?";
        int row_count = 0;
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to);
            row_count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (row_count != 0) {
                try (Connection connection = DatabaseManager.connect();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, to);
                    preparedStatement.setInt(2, from);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Edge edge) {
        String sql = "UPDATE edges SET [from] = ? , [to] =?,[length]=? WHERE id=?";
        int row_count = 0;
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, edge.getFrom());
            preparedStatement.setInt(2, edge.getTo());
            preparedStatement.setInt(3, edge.getLength());
            preparedStatement.setInt(4, edge.getId());
            row_count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Edge> getAllEdges() {
        String sql = "SELECT * FROM edges";
        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return wrapInEdges(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Edge> getAllEdgesOnFloor(int floor) {
        String sql = "SELECT * FROM edges WHERE [from] IN (SELECT id FROM nodes WHERE floor =?)" +
                " or [to] IN (SELECT id FROM nodes WHERE floor =?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, floor);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInEdges(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
