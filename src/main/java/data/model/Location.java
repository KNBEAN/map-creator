package data.model;

import com.sun.istack.internal.Nullable;

/**
 * This interface represents object of location. Location on the map represents real existing place,
 * for ex. room, lobby or coffee shop ect.
 */

public interface Location {



        /**
         * ID of the place on the map.
         * @return ID
         */
        int getId();

        /**
         * The short name of the place on the map. For example "Room 404".
         * @return location name
         */
        String getName();

        /**
         * The description of the place. This can be Null.
         * @return location description.
         */
        @Nullable
        String getDescription();
}
