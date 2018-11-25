package data.dao;

import data.dao.interfaces.LocationDAO;
import data.database.DatabaseManager;
import data.implementations.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImp implements LocationDAO {

    @Override
    public void insert(List<Location> locations) {
        String sql = "INSERT INTO locations(id,name,description) VALUES(?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Location location : locations) {
                preparedStatement.setInt(1, location.getId());
                preparedStatement.setString(2, location.getName());
                preparedStatement.setString(3, location.getDescription());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Location getLocation(int id) {
        String sql = "SELECT id,name,description FROM locations WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInLocation(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Location wrapInLocation(ResultSet resultSet) throws SQLException {
        Location location = null;
        while (resultSet.next()) {
            location = new Location(
                    resultSet.getInt("id")
                    , resultSet.getString("name")
                    , resultSet.getString("description"));
        }
        resultSet.close();
        return location;
    }

    private List<Location> wrapInLocations(ResultSet resultSet) throws SQLException {
        List<Location> locations = new ArrayList<>();
        Location location = null;
        while (resultSet.next()) {
            location = new Location(
                    resultSet.getInt("id")
                    , resultSet.getString("name")
                    , resultSet.getString("description"));
            locations.add(location);
        }
        resultSet.close();
        return locations;
    }

    @Override
    public void insert(Location location) {
        String sql = "INSERT INTO locations(id,name,description) VALUES(?,?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, location.getId());
            preparedStatement.setString(2, location.getName());
            preparedStatement.setString(3, location.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Location location) {
        String sql = "UPDATE locations SET name = ? , description =? WHERE id=?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getDescription());
            preparedStatement.setInt(3, location.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Location> getAllLocations() {
        String sql = "SELECT id, name,description FROM locations";
        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return wrapInLocations(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Location> getAllLocationsOnFloor(int floor) {
        String sql = "SELECT id,name,description FROM locations WHERE id in (select location_id FROM nodes WHERE floor=?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInLocations(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
