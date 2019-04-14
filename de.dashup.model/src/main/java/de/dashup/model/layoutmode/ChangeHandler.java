package de.dashup.model.layoutmode;

import de.dashup.shared.LayoutModeStructure;

import java.util.List;

public class ChangeHandler {

    private LayoutModeStructure layoutModeStructure;
    private static ChangeHandler INSTANCE;

    public ChangeHandler(LayoutModeStructure layoutModeStructure) {
        this.layoutModeStructure = layoutModeStructure;
    }

    public static ChangeHandler getInstance(LayoutModeStructure layoutModeStructure) {
        if (INSTANCE == null) {
            INSTANCE = new ChangeHandler(layoutModeStructure);
        }
        return INSTANCE;
    }

    public void processLayoutModeChanges() {

    }

    public void deletePanels(List<String> panelsToDelete) {

    }


}
