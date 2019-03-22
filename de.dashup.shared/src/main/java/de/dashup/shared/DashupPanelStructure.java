package de.dashup.shared;

import java.util.List;

public class DashupPanelStructure {

    private List<DashupSectionStructure> sections;

    public List<DashupSectionStructure> getSections() {
        return sections;
    }

    public void setSections(List<DashupSectionStructure> sections) {
        this.sections = sections;
    }

    public  String ret()
    {
        if(sections !=null) return sections.get(0).getSection_id() + " && " + sections.get(0).getPanels();

        return "is null";

    }

}
