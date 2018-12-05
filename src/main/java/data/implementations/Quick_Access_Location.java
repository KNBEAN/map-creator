package data.implementations;

import data.Id_Generator;

public class Quick_Access_Location implements data.model.Quick_Access_Location {
    private int id;
    private int location_id;
    private int quick_access_type;

    public Quick_Access_Location(int location_id, int quick_access_type) {
        this.id = Id_Generator.getId();
        this.location_id = location_id;
        this.quick_access_type = quick_access_type;
    }

    public Quick_Access_Location(int id, int location_id, int quick_access_type) {
        this.id = id;
        this.location_id = location_id;
        this.quick_access_type = quick_access_type;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getLocationID() {
        return location_id;
    }

    @Override
    public int getQuickAccessType() {
        return quick_access_type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Quick_Access_Location.class){
            if (((Quick_Access_Location) obj).getID()==id && ((Quick_Access_Location) obj).getLocationID()==(location_id)
                    && ((Quick_Access_Location) obj).getQuickAccessType()==(quick_access_type)) return true;
        }
        return false;
    }
}
