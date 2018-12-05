package data.model;

/**
 * This interface represents names that can specify one location. Tags represent commonly known name
 *  for places on map.
 */
public interface Location_Tag {

    /**
     * Get tag which object holds for it's location
     * @return string that specifies location.
     */
    String getTag();

    /**
     * Get location id connected to this Location_Tag
     * @return id of location.
     */
    int getLocation_id();

}
