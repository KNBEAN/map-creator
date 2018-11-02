package database.implementations;

import database.Id_Generator;

public class Location implements database.model.Location {

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
}
