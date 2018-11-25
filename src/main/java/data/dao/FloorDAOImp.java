package data.dao;

import data.dao.interfaces.FloorDAO;
import data.database.DatabaseManager;
import data.implementations.Floor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FloorDAOImp implements FloorDAO {

    @Override
    public Floor getFloor(int floor) {
        String sql = "SELECT floor,name,imagePath FROM floors WHERE floor = ?";
        try( Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatment = connection.prepareStatement(sql)) {
            preparedStatment.setInt(1,floor);
            ResultSet resultSet = preparedStatment.executeQuery();
            return wrapInFloor(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Floor wrapInFloor(ResultSet resultSet) throws SQLException{
        Floor floor = null;
        while (resultSet.next()){
            floor = new Floor(
                    resultSet.getInt("floor")
                    ,resultSet.getString("name")
                    ,resultSet.getString("imagePath"));
        }
        resultSet.close();
        return floor;
    }

    @Override
    public void insert(Floor floor) {
        String sql = "INSERT INTO floors(floor,name,imagePath) VALUES(?,?,?)";
        try(Connection connection = DatabaseManager.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,floor.getFloors());
            preparedStatement.setString(2,floor.getFloorName());
            preparedStatement.setString(3,floor.getImagePath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int floor) {
        String sql = "DELETE FROM floors WHERE floor = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,floor);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Floor floor) {
        String sql = "UPDATE floors SET name = ?,imagePath=? WHERE floor=?";
        try (   Connection connection = DatabaseManager.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,floor.getFloorName());
            preparedStatement.setString(2,floor.getImagePath());
            preparedStatement.setInt(3,floor.getFloors());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Floor> getAllFloors() {
        String sql = "SELECT floor,name,imagePath FROM floors";
        try( Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            return wrapInFloors(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Floor> wrapInFloors(ResultSet resultSet) throws SQLException {
        List<Floor> floors = new ArrayList<>();
        Floor floor = null;
        while (resultSet.next()){
            floor = new Floor(
                    resultSet.getInt("floor")
                    ,resultSet.getString("name")
                    ,resultSet.getString("imagePath"));
            floors.add(floor);
        }
        resultSet.close();
        return floors;
    }
}
