package data.dao;

import data.dao.interfaces.Location_TagDAO;
import data.database.DatabaseManager;
import data.implementations.Location_Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Location_TagDAOImp implements Location_TagDAO {

    @Override
    public void insert(List<Location_Tag> location_tags) {
        String sql = "INSERT INTO location_tags(tag,location_id) VALUES(?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Location_Tag location_tag : location_tags) {
                preparedStatement.setString(1, location_tag.getTag());
                preparedStatement.setInt(2, location_tag.getLocation_id());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Location_Tag> getLocation_Tags(int location_id) {
        String sql = "SELECT * FROM location_tags WHERE location_id = ?";
        return getLocation_tags(location_id, sql);
    }

    private List<Location_Tag> wrapInLocation_Tags(ResultSet resultSet) throws SQLException {
        List<Location_Tag> location_tags = new ArrayList<>();
        Location_Tag location_tag = null;
        while (resultSet.next()) {
            location_tag = new Location_Tag(
                    resultSet.getString("tag")
                    , resultSet.getInt("location_id"));
            location_tags.add(location_tag);
        }
        resultSet.close();
        return location_tags;
    }

    @Override
    public void insert(Location_Tag location_Tag) {
        String sql = "INSERT INTO location_tags(tag,location_id) VALUES(?,?)";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, location_Tag.getTag());
            preparedStatement.setInt(2, location_Tag.getLocation_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String tag) {
        String sql = "DELETE FROM location_tags WHERE tag = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Location_Tag location_Tag) {
        String sql = "UPDATE location_tags SET location_id = ? WHERE tag = ?";
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, location_Tag.getLocation_id());
            preparedStatement.setString(2, location_Tag.getTag());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Location_Tag> getAllLocations_Tag() {
        String sql = "SELECT * FROM location_tags";
        try (Connection connection = DatabaseManager.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return wrapInLocation_Tags(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Location_Tag> getAllLocations_TagsOnFloor(int floor) {
        String sql = "SELECT * FROM location_tags WHERE location_id in (select location_id FROM nodes WHERE floor=?)";
        return getLocation_tags(floor, sql);
    }

    private List<Location_Tag> getLocation_tags(int parameter, String sql) {
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            return wrapInLocation_Tags(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
