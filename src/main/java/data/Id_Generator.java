package data;

public abstract class Id_Generator {
    static int id = 1200;

    static public int getId() {
        id++;
        return id;
    }

    /**TODO:
     * Change this method, it has to set id greater than the biggest id in database
    */
    static public void setStartingID(){
        id = 1200;
    }
}
