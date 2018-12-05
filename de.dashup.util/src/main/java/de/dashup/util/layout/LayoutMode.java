package de.dashup.util.layout;

import de.dashup.shared.DashupPanelStructure;
import de.dashup.shared.DashupSectionStructure;

import java.util.List;

public class LayoutMode {

    public static boolean updateLayoutStructure(DashupPanelStructure dps)
    {
        List<DashupSectionStructure> sections = dps.getSections();
        for (DashupSectionStructure dss: sections) {
            if(dss.getSection_id().contains("sn"))
            {
                addNewSection(dss);
            }
            else
            {
                updateSection(dss);
            }
        }
        return true;
    }

    public static boolean addNewSection(DashupSectionStructure section)
    {
        int sectionIdNew = Integer.valueOf(section.getSection_id().substring(2));
        return true;
    }

    public static boolean updateSection(DashupSectionStructure section)
    {
        int sectionId = Integer.valueOf(section.getSection_id().substring(1));
        return true;
    }
}
