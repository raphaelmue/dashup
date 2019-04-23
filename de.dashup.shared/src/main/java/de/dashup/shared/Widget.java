package de.dashup.shared;

import de.dashup.shared.enums.Size;

public class Widget{

    private Integer id;
    private Size size;
    private String htmlContent;
    private Widget predecessor;

    public Widget(Integer id, Size size, String htmlContent, Widget predecessor) {
        this.id = id;
        this.size = size;
        this.htmlContent = htmlContent;
        this.predecessor = predecessor;
    }

    public Integer getID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Widget getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Widget predecessor) {
        this.predecessor = predecessor;
    }

}