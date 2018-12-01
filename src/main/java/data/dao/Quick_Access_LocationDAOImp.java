package data.dao;

import data.dao.interfaces.Quick_Access_LocationDAO;
import data.database.DatabaseManager;
import data.implementations.Quick_Access_Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Quick_Access_LocationDAOImp implements Quick_Access_LocationDAO {
    @Override
    public Quick_Access_Location getQuickAccessLocation(int id) {
        String sql = "SELECT * FROM quick_access_locations WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInQuickAccessLocation(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Quick_Access_Location wrapInQuickAccessLocation(ResultSet resultSet) throws SQLException{
        Quick_Access_Location quick_access_location = null;
        while (resultSet.next()) {
            quick_access_location = new Quick_Access_Location(
                    resultSet.getInt("id")
                    , resultSet.getInt("location_id")
                    , resultSet.getInt("quick_access_type"));
        }
        resultSet.close();
        return quick_access_location;
    }

    private List<Quick_Access_Location> wrapInQuickAccessLocations(ResultSet resultSet) throws SQLException{
        Quick_Access_Location quick_access_location = null;
        List<Quick_Access_Location> quick_access_locations = new ArrayList<>();
        while (resultSet.next()) {
            quick_access_location = new Quick_Access_Location(
                    resultSet.getInt("id")
                    , resultSet.getInt("location_id")
                    , resultSet.getInt("quick_access_type"));
            quick_access_locations.add(quick_access_location);
        }
        resultSet.close();
        return quick_access_locations;
    }


    @Override
    public void insert(Quick_Access_Location quick_access_location) {
        String sql = "INSERT INTO quick_access_locations(id,location_id,quick_access_type) VALUES(?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quick_access_location.getID());
            preparedStatement.setInt(2, quick_access_location.getLocationID());
            preparedStatement.setInt(3, quick_access_location.getQuickAccessType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insert(List<Quick_Access_Location> quick_access_locations) {
        String sql = "INSERT INTO quick_access_locations(id,location_id,quick_access_type) VALUES(?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Quick_Access_Location quick_access_location:quick_access_locations) {
                preparedStatement.setInt(1, quick_access_location.getID());
                preparedStatement.setInt(2, quick_access_location.getLocationID());
                preparedStatement.setInt(3, quick_access_location.getQuickAccessType());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM quick_access_locations WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Quick_Access_Location quick_access_location) {
        String sql = "UPDATE quick_access_locations SET location_id = ? , quick_access_type =? WHERE id=?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quick_access_location.getLocationID());
            preparedStatement.setInt(2, quick_access_location.getQuickAccessType());
            preparedStatement.setInt(3,quick_access_location.getID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Quick_Access_Location> getAllQuick_Access_Locations() {
        String sql = "SELECT * FROM quick_access_locations WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return wrapInQuickAccessLocations(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Quick_Access_Location> getAllQuick_Access_LocationsOnFloor(int floor) {
        String sql = "SELECT * FROM quick_access_locations WHERE location_id in (select location_id FROM nodes WHERE floor=?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            return executeQuickAccessLocationsQuery(preparedStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Quick_Access_Location> getAllQuick_Access_LocationsType(int type) {
        String sql = "SELECT * FROM quick_access_locations WHERE quick_access_type = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,type);
            return executeQuickAccessLocationsQuery(preparedStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<Quick_Access_Location> executeQuickAccessLocationsQuery(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        return wrapInQuickAccessLocations(resultSet);
    }
}
