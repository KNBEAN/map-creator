package data.implementations;


public class Floor implements data.model.IFloor {
    private int floorNumber;
    private String floorName;

    public Floor(int floor, String floorName){
        this.floorNumber = floor;
        this.floorName = floorName;
    }

    @Override
    public int getFloors() {
        return floorNumber;
    }

    @Override
    public String floorName(int floor) {
        return floorName;
    }


}