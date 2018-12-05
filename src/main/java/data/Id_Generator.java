package data;

public abstract class Id_Generator {
    static int id = 0;

    static public int getId() {
        id++;
        return id;
    }
}
