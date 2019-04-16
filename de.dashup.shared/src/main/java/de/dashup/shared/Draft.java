package de.dashup.shared;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Draft implements DatabaseObject {
    private int id;
    private String name;
    @SerializedName("short_description")
    private String shortDescription;
    private String description;
    @SerializedName("code_small")
    private String codeSmall;
    @SerializedName("code_medium")
    private String codeMedium;
    @SerializedName("code_large")
    private String codeLarge;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeSmall() {
        return codeSmall;
    }

    public void setCodeSmall(String codeSmall) {
        this.codeSmall = codeSmall;
    }

    public String getCodeMedium() {
        return codeMedium;
    }

    public void setCodeMedium(String codeMedium) {
        this.codeMedium = codeMedium;
    }

    public String getCodeLarge() {
        return codeLarge;
    }

    public void setCodeLarge(String codeLarge) {
        this.codeLarge = codeLarge;
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
