package data.database;

import java.sql.*;

public class DatabaseManager {

    private static Connection connection = null;
    private static final String DATABASE_NAME = "data.db";

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:" + DATABASE_NAME;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    DatabaseMetaData meta = connection.getMetaData();
                    System.out.println("The driver name is" + meta.getDriverName());
                    System.out.println("New data created");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:data.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void createTables() {

        String sqlNODES = "CREATE TABLE IF NOT EXISTS nodes (\n" +
                " id integer PRIMARY KEY, \n" +
                " x integer NOT NULL, \n" +
                " y integer NOT NULL,  \n" +
                " floor integer NOT NULL, \n" +
                " location_id integer NOT NULL, \n" +
                "FOREIGN KEY (location_id) REFERENCES locations(id),\n" +
                "FOREIGN KEY(floor) REFERENCES floors(floor));";
        String sqlFLOORS = "CREATE  TABLE IF NOT EXISTS floors(\n" +
                "floor integer PRIMARY KEY, \n" +
                "name text NOT NULL );";
        String sqlLOCATIONS = "CREATE TABLE IF NOT EXISTS locations (" +
                " id integer PRIMARY KEY ,\n" +
                " name text NOT NULL ,\n" +
                " description text);";
        String sqlLOCATION_TAGS = "CREATE TABLE IF NOT EXISTS location_tags(\n" +
                "tag text PRIMARY KEY,\n" +
                "location_id integer NOT NULL, FOREIGN KEY(location_id) REFERENCES locations(id));";
        String sqlEDGES = "CREATE TABLE IF NOT EXISTS edges(\n" +
                "id integer NOT NULL PRIMARY KEY, \n" +
                "'from' integer NOT NULL, \n" +
                "'to' integer NOT NULL, \n" +
                "length integer NOT NULL, \n" +
                "FOREIGN KEY ('from') REFERENCES nodes(id), \n" +
                "FOREIGN KEY ('to') REFERENCES nodes(id));";
        try (Connection connection = connect()) {
            Statement stmt = connection.createStatement();
            stmt.execute(sqlNODES);
            stmt = connection.createStatement();
            stmt.execute(sqlFLOORS);
            stmt = connection.createStatement();
            stmt.execute(sqlLOCATIONS);
            stmt = connection.createStatement();
            stmt.execute(sqlLOCATION_TAGS);
            stmt = connection.createStatement();
            stmt.execute(sqlEDGES);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
