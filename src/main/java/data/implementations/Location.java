package data.implementations;

import data.Id_Generator;

public class Location implements data.model.Location {

    private int id;
    private String name;
    private String description;

    public Location(String name, String description) {
        this.id = Id_Generator.getId();
        this.name = name;
        this.description = description;
    }

    public Location(String name) {
        this.id = Id_Generator.getId();
        this.name = name;
    }

    public Location(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return " ID: " + id + "\n" +
                " Name: " + name + "\n" +
                " Description: " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Location.class){
            obj = (Location) obj;
            if (((Location) obj).getId()==id && ((Location) obj).getName().equals(name)) return true;
        }
        return false;
    }
}
