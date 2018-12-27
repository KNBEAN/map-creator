package data.database;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DatabaseManager {

    private static Connection connection = null;
    private static final String DATABASE_DEFAULT = "data";
    private static  SQLiteConfig sqliteConfig = null;
    private static final String DATABASE_ROOT = "jdbc:sqlite:";
    private static final String IN_MEMORY = "memory:";
    private static String connectedBase;

    /**
     * Connect to new database with foreign keys enhancement.
     * @param name if null, database name equals data.db
     * @param inMemory if true, database will use in-memory (usable only for test)
     */
    public static void createNewDatabase(String name,boolean inMemory) {
        sqliteConfig = new SQLiteConfig();
        sqliteConfig.enforceForeignKeys(true);
        if (name == null || name.isEmpty()) name = DATABASE_DEFAULT;
        if (inMemory) name = IN_MEMORY+ name;

        String url = DATABASE_ROOT+ name;
        try {
            connection = DriverManager.getConnection(url+".db",sqliteConfig.toProperties());
            connectedBase = name;
            createTables();
            System.out.println("Connection to SQLite has been established");
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("The driver name is" + meta.getDriverName());
            System.out.println("New data created");

            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use to swap between created databases
     * @param base if null, then switches connection to default
     */
    public static void swapDatabaseConnection(String base) {
        if (base == null) {
            connectedBase = DATABASE_DEFAULT;
        } else {
            connectedBase = base;
        }
    }

    /**
     * @return Name of base which will be accessed by connect() method
     */
    public static String getConnectedBase() {
        return connectedBase;
    }

    /**
     * Create new connection for DAO
     * @return connection
     */
    public static Connection connect() {
        if (sqliteConfig ==null){
            sqliteConfig = new SQLiteConfig();
            sqliteConfig.enforceForeignKeys(true);
        }
        String url=null;
        if (connectedBase == null){
            url = DATABASE_ROOT + "memory:null";
        }
        else url = DATABASE_ROOT + connectedBase;

        try {
            connection = DriverManager.getConnection(url+".db", sqliteConfig.toProperties());
            System.out.println("Connection to SQLite has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }


    /**
     * Creates tables if not exist. Tables are connected by foreign keys. For detailed info look on
     * https://github.com/KNBEAN/map-creator/wiki
     */
    public static void createTables(){

        String sqlNODES = "CREATE TABLE IF NOT EXISTS nodes (\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
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
                " id integer PRIMARY KEY AUTOINCREMENT,\n" +
                " name text NOT NULL ,\n" +
                " description text);";
        String sqlLOCATION_TAGS = "CREATE TABLE IF NOT EXISTS location_tags(\n" +
                "tag text PRIMARY KEY,\n" +
                "location_id integer NOT NULL, FOREIGN KEY(location_id) REFERENCES locations(id) ON UPDATE CASCADE ON DELETE CASCADE);";
        String sqlEDGES = "CREATE TABLE IF NOT EXISTS edges(\n" +
                "id integer NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "[from] integer NOT NULL, \n" +
                "[to] integer NOT NULL, \n" +
                "length integer NOT NULL, \n" +
                "FOREIGN KEY ([from]) REFERENCES nodes(id) ON UPDATE CASCADE ON DELETE CASCADE, \n" +
                "FOREIGN KEY ([to]) REFERENCES nodes(id) ON UPDATE CASCADE ON DELETE CASCADE);";
        String sqlQUICK_ACCESS_LOCATIONS = "CREATE TABLE IF NOT EXISTS quick_access_locations(\n" +
                "id integer NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "location_id integer NOT NULL, \n" +
                "quick_access_type integer NOT NULL, \n"+
                "FOREIGN KEY (location_id) REFERENCES locations(id) ON UPDATE CASCADE ON DELETE CASCADE);";
        executeTableStatements(sqlNODES, sqlFLOORS, sqlLOCATIONS, sqlLOCATION_TAGS, sqlEDGES,sqlQUICK_ACCESS_LOCATIONS);

    }

    /**
     * Deletes all tables. Use carefully.
     */
    public static void dropTables(){
        String sqlNODES = "DROP TABLE nodes;";
        String sqlFLOORS= "DROP TABLE floors;";
        String sqlQUICK_ACCESS_LOCATION = "DROP TABLE quick_access_locations;";
        String sqlLOCATIONS = "DROP TABLE locations;";
        String sqlLOCATION_TAGS = "DROP TABLE location_tags;";
        String sqlEDGES = "DROP TABLE edges;";
        executeTableStatements(sqlNODES, sqlFLOORS, sqlLOCATIONS, sqlLOCATION_TAGS, sqlEDGES,sqlQUICK_ACCESS_LOCATION);

    }

    private static void executeTableStatements(String sqlNODES, String sqlFLOORS, String sqlLOCATIONS, String sqlLOCATION_TAGS, String sqlEDGES,String  sqlQUICK_ACCESS_LOCATIONS) {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlEDGES);
            statement = connection.createStatement();
            statement.execute(sqlLOCATION_TAGS);
            statement = connection.createStatement();
            statement.execute(sqlNODES);
            statement = connection.createStatement();
            statement.execute(sqlLOCATIONS);
            statement = connection.createStatement();
            statement.execute(sqlFLOORS);
            statement = connection.createStatement();
            statement.execute(sqlQUICK_ACCESS_LOCATIONS);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Table operation Failed");
        }
    }
}
