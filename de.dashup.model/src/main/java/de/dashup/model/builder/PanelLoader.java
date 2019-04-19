package de.dashup.model.builder;

import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseWidget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class PanelLoader {

    private static PanelLoader INSTANCE;

    public static PanelLoader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PanelLoader();
        }
        return INSTANCE;
    }

    public DatabaseWidget loadPanel(int id) {
        DatabaseWidget widget;
        try {
            widget = DashupService.getInstance().getPanelById(id);
        } catch (SQLException e) {
            throw new IllegalArgumentException("There is no panel with id '" + id + "' in database!");
        }

        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"card col ")
                .append(panel.getSize().getStyleClass())
                .append("\">")
                .append("<div class=\"card-content\">");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                PanelLoader.class.getResourceAsStream(PANELS_LOCATION + id + "-" + panel.getSize().getName() + ".html")))) {
            while (fileReader.ready()) {
                htmlContent.append(fileReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlContent.append("</div></div>");
        panel.setHtmlContent(htmlContent.toString());
        return panel;
    }
}
