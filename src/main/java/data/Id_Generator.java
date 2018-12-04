package data;

public abstract class Id_Generator {
    static int id = 1200;

    static public int getId() {
        id++;
        return id;
    }
}
