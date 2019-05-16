package de.dashup.shared;

import com.google.gson.annotations.SerializedName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Widget extends DatabaseWidget implements Comparable<Widget> {

    public enum Size {
        SMALL("small", "m2 s6"),
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

    private final Map<String, Property> properties = new HashMap<>();

    public Widget() {
    }

    public Widget(int id, String name, String description, int numberOfDownloads, int averageRating,
                  int index) {
        super(id, name, description, numberOfDownloads, averageRating);
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
            this.setVisible(((DatabaseWidget) databaseObject).getIsVisible());
            this.setCategory(((DatabaseWidget) databaseObject).getCategory());
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
                .append("<div class=\"card col ")
                .append(this.size.getStyleClass())
                .append("\">")
                .append("<div class=\"card-content\">");
        if (this.getProperties().size() > 0) {
            Document document = Jsoup.parse(this.getCode());
            for (Map.Entry<String, Property> propertyEntry : this.getProperties().entrySet()) {
                String[] propertyName = propertyEntry.getValue().getProperty().split("[.]");
                Element element = document.getElementsByAttributeValue("name", propertyName[0]).first();
                element.attr(propertyName[1], propertyEntry.getValue().getValue());
            }
            html.append(document.html());
        } else {
            html.append(this.getCode());
        }
        html.append("</div>")
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
}