package de.dashup.model.builder;

import de.dashup.shared.Panel;

import java.io.*;

public class PanelLoader {

    private static PanelLoader INSTANCE;

    private static final String PANELS_LOCATION = "./panels/";

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
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"panel-container\">");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                PanelLoader.class.getResourceAsStream("panels/" + id + ".html")))) {
            while (fileReader.ready()) {
                htmlContent.append(fileReader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlContent.append("</div>");
        Panel panel = new Panel(0, "weather", "niccce", 0);
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
