package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Draft implements DatabaseObject {
    private int id;
    private String name;
    @SerializedName("short_description")
    private String shortDescription;
    private String Description;
    private String code;
    @SerializedName("creation_date")
    private String creationDate;

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreationDate() {
        try {
            return LocalDate.parse(creationDate);
        } catch (DateTimeParseException ignored) { }
        return null;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate != null) {
            this.creationDate = creationDate.toString();
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
