package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class DatabasePanel implements DatabaseObject {
    @SerializedName("number_of_downloads")
    private int numberOfDownloads;
    @SerializedName("average_rating")
    private int averageRating;
    private int id;
    private String name, description;

    DatabasePanel() {
    }

    DatabasePanel(int id, String name, String description, int numberOfDownloads, int average_rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfDownloads = numberOfDownloads;
        this.averageRating = average_rating;
    }


    @Override
    public int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    void setId(int id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }
}