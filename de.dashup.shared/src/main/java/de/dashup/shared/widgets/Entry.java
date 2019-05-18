package de.dashup.shared.widgets;

import de.dashup.shared.DatabaseObject;

public class Entry {

    private String content;
    private boolean selected;

    public Entry() {
        super();
    }

    public Entry(String content, boolean selected) {
        this.content = content;
        this.selected = selected;
    }

    public String getContent() {
        return content;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
