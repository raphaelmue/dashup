package de.dashup.shared.models;

import com.google.gson.annotations.SerializedName;

public class DatabaseWidget extends DatabaseObject{

    private Integer id;
    private String name;
    private String description;
    @SerializedName("overview_text")
    private String overviewText;
    private String category;
    @SerializedName("publisher_id")
    private Integer publisherID;
    @SerializedName("publication_date")
    private Integer publicationDate;
    @SerializedName("html_small")
    private String htmlSmall;
    @SerializedName("html_medium")
    private String htmlMedium;
    @SerializedName("html_large")
    private String htmlLarge;

    public DatabaseWidget(Integer id, String name, String description, String overviewText, String category,
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

    public DatabaseWidget(Integer id, String name, String description, String overviewText, String category,
                          Integer publisherID, Integer publicationDate, String htmlSmall, String htmlMedium, String htmlLarge) {
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

    public Integer getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOverviewText() {
        return this.overviewText;
    }

    public String getCategory() {
        return this.category;
    }

    public Integer getPublisherID() {
        return this.publisherID;
    }

    public Integer getPublicationDate() {
        return this.publicationDate;
    }

    public String getHtmlSmall() {
        return this.htmlSmall;
    }

    public String getHtmlMedium() {
        return this.htmlMedium;
    }

    public String getHtmlLarge() {
        return this.htmlLarge;
    }

}