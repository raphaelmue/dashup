package de.dashup.model.layoutmode;

import de.dashup.model.service.DashupService;
import de.dashup.shared.*;

import java.sql.SQLException;
import java.util.List;

public class ChangeHandler {

    private LayoutModeStructure layoutModeStructure;
    private User user;
    private static ChangeHandler INSTANCE;

    private ChangeHandler(LayoutModeStructure layoutModeStructure, User user) {
        this.layoutModeStructure = layoutModeStructure;
        this.user = user;
    }

    public static ChangeHandler getInstance(LayoutModeStructure layoutModeStructure, User user) {
        if (INSTANCE == null) {
            INSTANCE = new ChangeHandler(layoutModeStructure, user);
        }
        return INSTANCE;
    }

    public void processLayoutModeChanges() throws SQLException {
        List<LayoutModeSection> sectionAndPanelOrder = layoutModeStructure.getSectionPanelOrder();
        saveNewSectionAndPanelStructure(sectionAndPanelOrder);

        List<LayoutModeSection> sectionsToDelete = layoutModeStructure.getSectionsToDelete();
        deleteSections(sectionsToDelete);

        List<String> panelsToDelete = layoutModeStructure.getPanelsToDelete();
        deletePanels(panelsToDelete);
    }

    private void saveNewSectionAndPanelStructure(List<LayoutModeSection> sections) throws NumberFormatException, SQLException {
        for (int i = 0; i < sections.size(); i++) {

            String sectionIdFrontend = sections.get(i).getSectionId();
            String sectionName = sections.get(i).getSectionName();

            int sectionId = convertId(sectionIdFrontend);

            if (sectionId == -1) {
                DashupService.getInstance().addSection(user, sectionName, i, i);
            } else if (sectionId>-1) {
                DashupService.getInstance().updateSection(user, sectionName, sectionId, i, i);
            }
//            for (LayoutModePanel panel : sections.get(i).getPanelStructure()) {
//                int panelId = Integer.valueOf(panel.getPanelId());
//                //TODO update Panel operation (order+size)
//            }
        }
    }

    private void deleteSections(List<LayoutModeSection> sectionsToDelete) throws SQLException {
        for (LayoutModeSection section : sectionsToDelete) {

            int sectionId = convertId(section.getSectionId());
            DashupService.getInstance().deleteSection(user,sectionId);
        }
    }

    private void deletePanels(List<String> panelsToDelete) {
        for (String panelId : panelsToDelete) {

//            int panelIdInt = convertId(panelId);
//            TODO delete Panel operation
        }
    }

    private int convertId(String id) {
        String technicalId = id.substring(1);
        String idPrefix = id.substring(0, 0);

        if (idPrefix.equals("s")) {
            return Integer.valueOf(technicalId);
        }

        return -1;
    }


}
