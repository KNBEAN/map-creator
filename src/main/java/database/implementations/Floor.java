package database.implementations;



public class Floor implements database.model.Floor {
    private int floor;
    private String floorName;

    public Floor(int floor,String floorName){
        this.floor = floor;
        this.floorName = floorName;
    }

    @Override
    public int getFloors() {
        return floor;
    }

    @Override
    public String floorName(int floor) {
        return floorName;
    }


}
