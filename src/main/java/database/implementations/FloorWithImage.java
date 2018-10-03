package database.implementations;

import javax.swing.*;

public class FloorWithImage extends Floor {

    private ImageIcon image;
    private String path;

    public FloorWithImage(int floor, String floorName, String path) {
        super(floor, floorName);
        this.path = path;
        image = new ImageIcon(path);
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {

        String text = " Floor: "+getFloors()+"\n" +
                " Floor Tag: "+floorName(getFloors())+"\n" +
                " Floor image path:\n"
                +getPath();

        return text;
    }
}
