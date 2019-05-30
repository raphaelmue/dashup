package de.dashup.shared.widgets;

public class Task {

    private String content;
    private boolean selected;

    public Task() {
        super();
    }

    public Task(String content, boolean selected) {
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
