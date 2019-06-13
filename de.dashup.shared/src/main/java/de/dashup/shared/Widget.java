package de.dashup.shared;

import com.google.gson.annotations.SerializedName;
import de.dashup.shared.layout.DatabaseWidget;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Widget extends DatabaseWidget implements Comparable<Widget>{

    public enum Size {
        SMALL("small", "m2 s6"),
        MEDIUM("medium", "m4 s12"),
        LARGE("large", "m5 s12");

        private final String name, styleClass;

        Size(String name, String styleClass) {
            this.name = name;
            this.styleClass = styleClass;
        }

        public String getName() {
            return name;
        }

        public String getStyleClass() {
            return styleClass;
        }

        public static Size getSizeByName(String name) {
            for (Size size : values()) {
                if (size.getName().equals(name)) {
                    return size;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Size size;
    @SerializedName("widget_index")
    private int index;
    private List<String> component = new ArrayList<>();

    public Widget() {
    }

    public Widget(int id, String name, String description, int numberOfDownloads, int averageRating, int numberOfRatings, LocalDate publicationDate, String iconCode, int index) {
        super(id, name, description, numberOfDownloads, averageRating, numberOfRatings, publicationDate, iconCode);
        this.index = index;
    }

    @Override
    public DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabaseWidget) {
            this.setId(databaseObject.getId());
            this.setName(((DatabaseWidget) databaseObject).getName());
            this.setDescription(((DatabaseWidget) databaseObject).getDescription());
            this.setCodeSmall(((DatabaseWidget) databaseObject).getCodeSmall());
            this.setCodeMedium(((DatabaseWidget) databaseObject).getCodeMedium());
            this.setCodeLarge(((DatabaseWidget) databaseObject).getCodeLarge());
        }
        return this;
    }

    public Size getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public String getCode() {
        return this.getCode(this.size);
    }

    public String getCode(Size size) {
        switch (size) {
            case SMALL:
                return this.getCodeSmall();
            case MEDIUM:
                return this.getCodeMedium();
            case LARGE:
                return this.getCodeLarge();
            default:
                throw new IllegalArgumentException("No size is defined");
        }
    }

    public String getCodeWithWrapper() {
        return "<div class=\"card col " + this.size.getStyleClass() + "\">" +
                    "<div class=\"card-content\">" +
                        this.getCode() +
                    "</div>" +
                "</div>";
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(Widget widgetToCompare) {
        return Integer.compare(this.getIndex(), widgetToCompare.getIndex());
    }

    public void addComponent(String component){
        if(!this.component.contains(component)){
            this.component.add(component);
        }
    }

    public List<String> getComponents(){
        return this.component;
    }

}
