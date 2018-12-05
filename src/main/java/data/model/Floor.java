package data.model;

/**
 *  This interface represents a table of floor in given building
 */
public interface Floor {

    /**
     * Show how many floors is in building
     * @return number of floors.
     */
    int getFloors();


    /**
     * Allows to get name which represents floor, such as "ground floor"
     * @param floor
     * @return name of floor.
     */
    String floorName(int floor);

    /**
     * @return Path to image that represent floor in app
     */
    String getImagePath();

}
