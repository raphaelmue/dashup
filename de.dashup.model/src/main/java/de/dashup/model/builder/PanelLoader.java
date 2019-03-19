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
     * Loads a panel content from database by id
     *
     * @param id panel id
     * @return content of panel
     */
    public String loadPanelContent(int id) {
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
        return htmlContent.toString();
    }

    /**
     * Stores the html content of a panel into resources
     *
     * @param panel panel to store
     */
    public void storePanel(Panel panel) {

    }
}
