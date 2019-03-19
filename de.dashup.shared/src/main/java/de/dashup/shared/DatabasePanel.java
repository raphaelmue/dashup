package de.dashup.shared;

public class DatabasePanel implements DatabaseObject {

    private int id, number_of_downloads, average_rating;
    private String name, description;

    public DatabasePanel(int id, String name, String description, int number_of_downloads, int average_rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number_of_downloads = number_of_downloads;
        this.average_rating = average_rating;
    }


    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
