package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

public class DatabaseWidget implements DatabaseObject {

    private int id;
    private String name;
    private String description;
    @SerializedName("overview_text")
    private String overviewText;
    private String category;
    @SerializedName("publisher_id")
    private int publisherID;
    @SerializedName("publication_date")
    private int publicationDate;
    @SerializedName("html_small")
    private String htmlSmall;
    @SerializedName("html_medium")
    private String htmlMedium;
    @SerializedName("html_large")
    private String htmlLarge;

    public DatabaseWidget(int id, String name, String description, String overviewText, String category,
                          String htmlSmall, String htmlMedium, String htmlLarge) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.overviewText = overviewText;
        this.category = category;
        this.htmlSmall = htmlSmall;
        this.htmlMedium = htmlMedium;
        this.htmlLarge = htmlLarge;
    }

    public DatabaseWidget(int id, String name, String description, String overviewText, String category,
                          int publisherID, int publicationDate, String htmlSmall, String htmlMedium, String htmlLarge) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.overviewText = overviewText;
        this.category = category;
        this.publisherID = publisherID;
        this.publicationDate = publicationDate;
        this.htmlSmall = htmlSmall;
        this.htmlMedium = htmlMedium;
        this.htmlLarge = htmlLarge;
    }

    public int getId() {
        return id;
    }

}
