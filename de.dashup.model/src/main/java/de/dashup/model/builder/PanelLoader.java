package de.dashup.model.builder;

import de.dashup.shared.Panel;

public class PanelLoader {

    private static PanelLoader INSTANCE;

    private static final String PANELS_LOCATION = "/panels/";

    public static PanelLoader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PanelLoader();
        }
        return INSTANCE;
    }

    /**
     * Loads a panel from database by id and sets its html content
     *
     * @param id panel id
     * @return panel object
     */
    public Panel loadPanel(int id) {
        return null;
    }

    /**
     * Stores the html content of a panel into resources
     *
     * @param panel panel to store
     */
    public void storePanel(Panel panel) {

    }
}
