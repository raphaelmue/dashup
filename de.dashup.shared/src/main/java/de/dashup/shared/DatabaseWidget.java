package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public abstract class DatabaseWidget implements DatabaseObject {
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

    String getDescription() {
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

    public void setId(int id) {
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

}
