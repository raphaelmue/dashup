package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class DatabaseWidget implements DatabaseObject {
    private int id;
    private String name;
    private String description;
    @SerializedName("code_small")
    private String codeSmall;
    @SerializedName("code_medium")
    private String codeMedium;
    @SerializedName("code_large")
    private String codeLarge;
    @SerializedName("visibility")
    private boolean isVisible;
    @SerializedName("number_of_downloads")
    private int numberOfDownloads;
    @SerializedName("avg_of_ratings")
    private int averageRating;
    @SerializedName("number_of_ratings")
    private int numberOfRatings;
    @SerializedName("publication_date")
    private String publicationDate;
    @SerializedName("short_description")
    private String shortDescription;

    public DatabaseWidget() {
    }

    DatabaseWidget(int id, String name, String description, int numberOfDownloads, int averageRating, int numberOfRatings, LocalDate publicationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfDownloads = numberOfDownloads;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;
        if (publicationDate != null) {
            this.publicationDate = publicationDate.toString();
        }
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

    public String getCodeSmall() {
        return codeSmall;
    }

    public String getCodeMedium() {
        return codeMedium;
    }

    public String getCodeLarge() {
        return codeLarge;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public int getAverageRating() {
        return averageRating;
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

    void setCodeSmall(String codeSmall) {
        this.codeSmall = codeSmall;
    }

    void setCodeMedium(String codeMedium) {
        this.codeMedium = codeMedium;
    }

    void setCodeLarge(String codeLarge) {
        this.codeLarge = codeLarge;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public LocalDate getPublicationDate() {
        if (publicationDate != null && !publicationDate.isBlank()) {
            return LocalDate.parse(publicationDate);
        }
        return null;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate != null) {
            this.publicationDate = publicationDate.toString();
        }
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
