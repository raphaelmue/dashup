package de.dashup.shared.layout;

import com.google.gson.annotations.SerializedName;
import de.dashup.shared.DatabaseObject;
import de.dashup.shared.Tag;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Widget extends DatabaseWidget implements Comparable<Widget> {

    public enum Size {
        SMALL("small", "m3 s6"),
        MEDIUM("medium", "m4 s12"),
        LARGE("large", "m6 s12");

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

    public enum Category {
        PRODUCTIVITY("productivity"),
        LIFESTYLE("lifestyle"),
        TIME("time"),
        FINANCE("finance"),
        PLANNING("planning");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Category getCategoryByName(String name) {
            for (Category category : values()) {
                if (category.getName().equals(name)) {
                    return category;
                }
            }
            return null;
        }

    }

    private Size size;
    @SerializedName("widget_index")
    private int index;
    private final Set<Tag> tags = new HashSet<>();

    public Widget() {
    }

    public Widget(int id, String name, String description, int numberOfDownloads, int averageRating,
                  int index, int numberOfRatings, LocalDate publicationDate, String iconCode) {
        super(id, name, description, numberOfDownloads, averageRating, numberOfRatings, publicationDate, iconCode);
        this.index = index;
    }

    @Override
    public Widget fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabaseWidget) {
            this.setId(databaseObject.getId());
            this.setName(((DatabaseWidget) databaseObject).getName());
            this.setDescription(((DatabaseWidget) databaseObject).getDescription());
            this.setShortDescription(((DatabaseWidget) databaseObject).getShortDescription());
            this.setCodeSmall(((DatabaseWidget) databaseObject).getCodeSmall());
            this.setCodeMedium(((DatabaseWidget) databaseObject).getCodeMedium());
            this.setCodeLarge(((DatabaseWidget) databaseObject).getCodeLarge());
            this.setAverageRating(((DatabaseWidget) databaseObject).getAverageRating());
            this.setNumberOfRatings(((DatabaseWidget) databaseObject).getNumberOfRatings());
            this.setPublicationDate(((DatabaseWidget) databaseObject).getPublicationDate());
            this.setShortDescription(((DatabaseWidget) databaseObject).getShortDescription());
            this.setIconCode(((DatabaseWidget) databaseObject).getIconCode());
            this.setVisible(((DatabaseWidget) databaseObject).getIsVisible());
            this.setCategory(((DatabaseWidget) databaseObject).getCategory());
            this.setPublisherId((((DatabaseWidget) databaseObject).getPublisherId()));
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

    public void setCode(String code, Size size) {
        switch (size) {
            case SMALL:
                this.setCodeSmall(code);
                break;
            case MEDIUM:
                this.setCodeMedium(code);
                break;
            case LARGE:
                this.setCodeLarge(code);
                break;
            default:
                throw new IllegalArgumentException("No size is defined");
        }
    }

    public String getCodeWithWrapper() {
        return "<div class=\"col " + this.size.getStyleClass() + "\">" +
                "<div class=\"widget card\">" +
                "<div class=\"card-content\">" +
                this.getCode() +
                "</div>" +
                "</div>" +
                "</div>";
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Category getCategoryObject() {
        return Category.getCategoryByName(this.getCategory());
    }

    public void setCategory(Category category) {
        this.setCategory(category.getName());
    }

    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public int compareTo(Widget widgetToCompare) {
        return Integer.compare(this.getIndex(), widgetToCompare.getIndex());
    }
}