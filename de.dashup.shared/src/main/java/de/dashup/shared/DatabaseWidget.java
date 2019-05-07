package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class DatabaseWidget implements DatabaseObject {
    private int id;
    private String name;
    @SerializedName("descriptions")
    private String description;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("publication_date")
    private String publicationDate;
    @SerializedName("code_small")
    private String codeSmall;
    @SerializedName("code_medium")
    private String codeMedium;
    @SerializedName("code_large")
    private String codeLarge;
    @SerializedName("visibility")
    private boolean isVisible;
    private String category;
    @SerializedName("number_of_downloads")
    private int numberOfDownloads;
    @SerializedName("average_rating")
    private int averageRating;

    DatabaseWidget() {
    }

    DatabaseWidget(int id, String name, String description, int numberOfDownloads, int average_rating) {
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

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public LocalDate getPublicationDate() {
        if (this.publicationDate != null) {
            return LocalDate.parse(publicationDate);
        }
        return null;
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

    public boolean getIsVisible() {
        return isVisible;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public int getAverageRating() {
        return averageRating;
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

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate != null) {
            this.publicationDate = publicationDate.toString();
        }
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setCodeSmall(String codeSmall) {
        this.codeSmall = codeSmall;
    }

    public void setCodeMedium(String codeMedium) {
        this.codeMedium = codeMedium;
    }

    public void setCodeLarge(String codeLarge) {
        this.codeLarge = codeLarge;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}
