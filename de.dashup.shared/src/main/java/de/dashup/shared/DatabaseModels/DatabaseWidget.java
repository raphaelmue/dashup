package de.dashup.shared.DatabaseModels;

import com.google.gson.annotations.SerializedName;
import de.dashup.shared.Enums.Size;

public class DatabaseWidget extends DatabaseObject{

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

    public int getID() {
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

    public int getPublisherID() {
        return this.publisherID;
    }

    public int getPublicationDate() {
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