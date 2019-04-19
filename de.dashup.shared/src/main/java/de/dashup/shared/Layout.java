package de.dashup.shared;

import java.util.List;

public class Layout {

    private List<Section> sections;

    public Layout(List<Section> sections){
        this.sections = sections;
    }

    public List<Section> getSections() {
        return this.sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}