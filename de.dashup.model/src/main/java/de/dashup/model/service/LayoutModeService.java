package de.dashup.model.service;

import de.dashup.shared.*;

import java.sql.SQLException;
import java.util.List;

public class LayoutModeService extends DashupService {

    private static LayoutModeService INSTANCE;

    public static LayoutModeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LayoutModeService();
        }
        return INSTANCE;
    }

    public void processLayoutModeChanges(LayoutModeStructureDTO layoutModeStructureDTO, User user) throws SQLException {
        List<LayoutModeSectionDTO> sectionsToDelete = layoutModeStructureDTO.getSectionsToDelete();

        for (LayoutModeSectionDTO section : sectionsToDelete) {
            Section sectionToDelete = section.toDataTransferObject();
            deleteWidgetsOfSection(sectionToDelete);
            deleteSection(user, sectionToDelete);
        }

        List<LayoutModeSectionDTO> sectionAndWidgetIndex = layoutModeStructureDTO.getSectionWidgetOrder();
        int sectionIndex = 0;

        for (LayoutModeSectionDTO sectionDTO : sectionAndWidgetIndex) {
            sectionDTO.setIndex(sectionIndex);
            Section sectionToProcess = sectionDTO.toDataTransferObject();

            if (sectionDTO.isNewSection()) {
                int newSectionId = addNewSection(user, sectionToProcess);
                sectionToProcess.setId(newSectionId);
            } else {
                updateSection(user, sectionToProcess);
                deleteWidgetsOfSection(sectionToProcess);
            }

            List<LayoutModeWidgetDTO> layoutModeWidgetsDTO = sectionDTO.getLayoutModeWidgets();
            int widgetIndex = 0;
            for (LayoutModeWidgetDTO widgetDTO : layoutModeWidgetsDTO) {
                widgetDTO.setIndex(widgetIndex);
                Widget widget = widgetDTO.toDataTransferObject();
                addWidgetToSection(widget, sectionToProcess, widgetDTO.getWidgetSize());
                widgetIndex++;
            }
            sectionIndex++;
        }
    }



}
