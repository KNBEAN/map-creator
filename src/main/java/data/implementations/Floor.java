package data.implementations;


public class Floor implements data.model.Floor {
    private int floorNumber;
    private String floorName;

    public Floor(int floorNumber, String floorName) {
        this.floorNumber = floorNumber;
        this.floorName = floorName;
        this.imagePath = null;
    }



    @Override
    public String getImagePath() {
        return imagePath;
    }

    private String imagePath;

    public Floor(int floorNumber, String floorName, String imagePath) {
        this.floorNumber = floorNumber;
        this.floorName = floorName;
        this.imagePath = imagePath;
    }


    @Override
    public int getFloors() {
        return floorNumber;
    }

    @Override
    public String floorName(int floor) {
        return floorName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Floor.class){
            obj = (Floor) obj;
            if (((Floor) obj).getFloors()==floorNumber && ((Floor) obj).getFloorName().equals(floorName)) return true;
        }
        return false;
    }

    public String getFloorName() {
        return floorName;
    }

    @Override
    public String toString() {
        return floorName;

    }

    public String getFloorInfo(){
        return "Floor: " + getFloors() + "\n" +
                "Floor name: " + getFloorName() + "\n" +
                "Image path: " + getImagePath();
    }
}
