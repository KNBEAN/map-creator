package data.database;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseManagerTest {

    @BeforeEach
    void setUp() {
        System.out.println("databaseOPS()");
        DatabaseManager.createNewDatabase();
    }

    @Test
    @DisplayName("Create Tables")
    void shouldCreateTables(){
        DatabaseManager.createTables();
    }
}