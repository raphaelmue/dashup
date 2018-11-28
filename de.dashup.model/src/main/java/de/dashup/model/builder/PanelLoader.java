package de.dashup.model.builder;

import de.dashup.shared.Panel;

import java.io.*;
import java.util.ArrayList;

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
        StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("WeatherPanel.html")))){
            while (reader.ready()){
                htmlContent.append(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Panel panel = new Panel(0,"weather","niccce");
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
