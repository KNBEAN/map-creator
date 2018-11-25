package data.database;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DatabaseManager {

    private static Connection connection = null;
    private static final String DATABASE_NAME = "data.db";
    private static  SQLiteConfig sqliteConfig = null;

    public static void createNewDatabase() {
        sqliteConfig = new SQLiteConfig();
        sqliteConfig.enforceForeignKeys(true);
        String url = "jdbc:sqlite:" + DATABASE_NAME;
        try {
            connection = DriverManager.getConnection(url,sqliteConfig.toProperties());
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
        createTables();
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:data.db";
        try {
            connection = DriverManager.getConnection(url, sqliteConfig.toProperties());
            System.out.println("Connection to SQLite has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            createNewDatabase();
        } finally {
            try {
                connection = DriverManager.getConnection(url, sqliteConfig.toProperties());
                System.out.println("Connection to SQLite has been established");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return connection;
        }
    }
    public static void createTables(){

        String sqlNODES = "CREATE TABLE IF NOT EXISTS nodes (\n" +
                " id integer PRIMARY KEY, \n" +
                " x integer NOT NULL, \n" +
                " y integer NOT NULL,  \n" +
                " floor integer NOT NULL, \n" +
                " location_id integer DEFAULT -1 NOT NULL , \n" +
                "FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET DEFAULT ON UPDATE CASCADE,\n" +
                "FOREIGN KEY(floor) REFERENCES floors(floor) ON UPDATE CASCADE ON DELETE CASCADE);";
        String sqlFLOORS = "CREATE  TABLE IF NOT EXISTS floors(\n" +
                "floor integer PRIMARY KEY, \n" +
                "name text NOT NULL, \n" +
                "imagePath text);";
        String sqlLOCATIONS = "CREATE TABLE IF NOT EXISTS locations (" +
                " id integer PRIMARY KEY ,\n" +
                " name text NOT NULL ,\n" +
                " description text);";
        String sqlLOCATION_TAGS = "CREATE TABLE IF NOT EXISTS location_tags(\n" +
                "tag text PRIMARY KEY,\n" +
                "location_id integer NOT NULL, FOREIGN KEY(location_id) REFERENCES locations(id) ON UPDATE CASCADE ON DELETE CASCADE);";
        String sqlEDGES = "CREATE TABLE IF NOT EXISTS edges(\n" +
                "id integer NOT NULL PRIMARY KEY, \n" +
                "[from] integer NOT NULL, \n" +
                "[to] integer NOT NULL, \n" +
                "length integer NOT NULL, \n" +
                "FOREIGN KEY ([from]) REFERENCES nodes(id) ON UPDATE CASCADE ON DELETE CASCADE, \n" +
                "FOREIGN KEY ([to]) REFERENCES nodes(id) ON UPDATE CASCADE ON DELETE CASCADE);";
        executeTableStatements(sqlNODES, sqlFLOORS, sqlLOCATIONS, sqlLOCATION_TAGS, sqlEDGES);

    }

    public static void dropTables(){
        String sqlNODES = "DROP TABLE nodes;";
        String sqlFLOORS= "DROP TABLE floors;";
        String sqlLOCATIONS = "DROP TABLE locations;";
        String sqlLOCATION_TAGS = "DROP TABLE location_tags;";
        String sqlEDGES = "DROP TABLE edges;";
        executeTableStatements(sqlNODES, sqlFLOORS, sqlLOCATIONS, sqlLOCATION_TAGS, sqlEDGES);

    }

    private static void executeTableStatements(String sqlNODES, String sqlFLOORS, String sqlLOCATIONS, String sqlLOCATION_TAGS, String sqlEDGES) {
        try (Connection connection = connect()) {
            Statement stmt = connection.createStatement();
            stmt.execute(sqlEDGES);
            stmt = connection.createStatement();
            stmt.execute(sqlLOCATION_TAGS);
            stmt = connection.createStatement();
            stmt.execute(sqlNODES);
            stmt = connection.createStatement();
            stmt.execute(sqlLOCATIONS);
            stmt = connection.createStatement();
            stmt.execute(sqlFLOORS);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Table operation Failed");
        }
    }
}
