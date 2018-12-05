package data.model;

public interface Quick_Access_Location {

    /**
     * @return id
     */
    int getID();

    /**
     * @return id of location paired with quick access
     */
    int getLocationID();

    /**
     * @return types: 1- Toilets 2- Gastronomy 3- Patient Assistant
     */
    int getQuickAccessType();


}
