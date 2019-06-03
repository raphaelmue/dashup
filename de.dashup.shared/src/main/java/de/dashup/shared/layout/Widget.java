package de.dashup.shared.layout;

import com.google.gson.annotations.SerializedName;
import de.dashup.shared.DatabaseObject;
import de.dashup.shared.Property;
import de.dashup.shared.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.util.*;

public class Widget extends DatabaseWidget implements Comparable<Widget> {

    public enum Size {
        SMALL("small", "m3 s6"),
        MEDIUM("medium", "m4 s12"),
        LARGE("large", "m6 s12");

        private final String name;
        private final String styleClass;

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

    private final Map<String, Property> properties = new HashMap<>();

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
        StringBuilder html = new StringBuilder()
                .append("<div class=\"col ")
                .append(this.size.getStyleClass())
                .append("\">")
                .append("<div class=\"card widget\">")
                .append("<div class=\"card-content\">");
        if (this.getProperties().size() > 0) {
            Document document = Jsoup.parse(this.getCode());
            for (Map.Entry<String, Property> propertyEntry : this.getProperties().entrySet()) {
                String[] propertyName = propertyEntry.getValue().getProperty().split("[.]");
                Element element = document.getElementsByAttributeValue("name", propertyName[0]).first();
                if (element != null) {
                    element.attr(propertyName.length == 2 ? propertyName[1] : "value", propertyEntry.getValue().getValue());
                }
            }
            html.append(document.html());
        } else {
            html.append(this.getCode());
        }
        html.append("</div>")
                .append("</div>")
                .append("</div>");

        return html.toString();
    }

    public Map<String, Property> getProperties() {
        return properties;
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Widget && this.compareTo((Widget) obj) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }
}