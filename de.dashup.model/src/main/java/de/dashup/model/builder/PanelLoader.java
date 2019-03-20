package de.dashup.model.builder;

import de.dashup.model.service.DashupService;
import de.dashup.shared.Panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class PanelLoader {

    private static PanelLoader INSTANCE;

    private static final String PANELS_LOCATION = "panels/";

    public static PanelLoader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PanelLoader();
        }
        return INSTANCE;
    }

    /**
     * Loads a panel content from database by id
     *
     * @param id panel id
     * @return content of panel
     */
    public Panel loadPanel(int id) {
        Panel panel;
        try {
            panel = DashupService.getInstance().getPanelById(id);
        } catch (SQLException e) {
            throw new IllegalArgumentException("There is no panel with id '" + id + "' in database!");
        }

        // TODO: fetch size from database
        panel.setSize(Panel.Size.MEDIUM);

        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"col ")
                .append(panel.getSize().getStyleClass())
                .append("\">");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                PanelLoader.class.getResourceAsStream(PANELS_LOCATION + id + "-" + panel.getSize().getName() + ".html")))) {
            while (fileReader.ready()) {
                htmlContent.append(fileReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlContent.append("</div>");
        panel.setHtmlContent(htmlContent.toString());
        return panel;
    }

    /**
     * Stores the html content of a panel into resources
     *
     * @param panel panel to store
     */
    public void storePanel(Panel panel) {

    }
}
