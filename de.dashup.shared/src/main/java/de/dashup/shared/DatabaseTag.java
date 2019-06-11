package de.dashup.shared;

public class DatabaseTag implements DatabaseObject {
    private int id;
    private String text;

    public DatabaseTag(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
