package data.dao;

import data.database.DatabaseManager;
import data.implementations.Edge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EdgeDAOImp implements EdgeDAO {

    @Override
    public Edge getEdge(int id) {
        String sql = "SELECT id,[from],[to],[length] FROM edges WHERE id = ?";
        try(Connection connection = DatabaseManager.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            return wrapInEdge(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Edge wrapInEdge(ResultSet resultSet) throws SQLException{
        Edge edge = null;
        while (resultSet.next()){
            edge = new Edge(
                    resultSet.getInt("id")
                    ,resultSet.getInt("from")
                    ,resultSet.getInt("to")
                    ,resultSet.getInt("length"));
        }
        resultSet.close();
        return edge;
    }

    @Override
    public void insert(Edge edge) {
        String sql = "INSERT INTO edges(id,[from],[to],[length]) VALUES(?,?,?,?)";
        int row_count = 0;
        try( Connection connection = DatabaseManager.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setPrepStatementParams(edge, pstmt);
            row_count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (row_count!=0)
            try( Connection connection = DatabaseManager.connect();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {
                edge = edge.swapEnds();
                setPrepStatementParams(edge, pstmt);
                pstmt.executeUpdate();
            } catch (SQLException e){
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
    public void delete(int id) {

    }

    @Override
    public void update(Edge edge) {

    }

    @Override
    public List<Edge> getAllEdges() {
        return null;
    }

    @Override
    public List<Edge> getAllEdgesOnFloor(int floor) {
        return null;
    }
}
