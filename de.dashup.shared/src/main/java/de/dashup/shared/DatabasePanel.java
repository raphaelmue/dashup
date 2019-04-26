package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class DatabasePanel implements DatabaseObject {
    @SerializedName("number_of_downloads")
    private int numberOfDownloads;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("avg_of_ratings")
    private int averageRating;
    @SerializedName("number_of_ratings")
    private int numberOfRatings;
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

    public String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}
