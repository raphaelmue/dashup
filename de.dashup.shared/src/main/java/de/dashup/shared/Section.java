package de.dashup.shared;

import java.util.List;

public class Section{

    private int id;
    private String name;
    private Section predecessor;
    private List<Widget> widgets;

    public Section(int id, String name, Section predecessor, List<Widget> widgets) {
        this.id = id;
        this.name = name;
        this.predecessor = predecessor;
        this.widgets = widgets;
    }

    public int getID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getPredecessor() {
        return this.predecessor;
    }

    public void setPredecessor(Section predecessor) {
        this.predecessor = predecessor;
    }

    public List<Widget> getWidgets() {
        return this.widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

}