package de.dashup.model.builder;

import de.dashup.shared.Panel;
import de.dashup.shared.Section;
import de.dashup.shared.User;

public class DashupBuilder {

    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        for (Section s : user.getSections()) {
            content.append("<div> <h3>").append(s.getName()).append("</h3> <hr/> <div>");
            for (Panel p : s.getPanels()) {
                content.append(p.getHtmlContent());
            }
            content.append("<div class=\"clear-float\"></div></div></div>");
        }
        return content.toString();
    }

    public static String buildUsersPanelsLayoutMode(User user) {
        StringBuilder content = new StringBuilder();
        //content.append("<div id=\"drag_container_section\">");
        for (Section s : user.getSections()) {

            content.append("<div class=\"dashup_section\" id=\"s").append(s.getId()).append("\">");
            content.append("<p>test</p>");
            content.append("</div");
        }
        //content.append("</div>");
        System.out.println(content.toString());
        return content.toString();

    }

}
