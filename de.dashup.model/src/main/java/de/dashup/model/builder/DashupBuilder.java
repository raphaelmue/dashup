package de.dashup.model.builder;

import de.dashup.shared.Panel;
import de.dashup.shared.Section;
import de.dashup.shared.User;

import java.util.List;

public class DashupBuilder {

    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        for (Section s : user.getSections()) {
            content.append("<div> <h3 class=\"sectionHeading\">").append(s.getName()).append("</h3> <hr/> <div>");
            for (Panel p : s.getPanels()) {
                content.append(p.getHtmlContent());
            }
            content.append("<div class=\"clear-float\"></div></div></div>");
        }
        return content.toString();
    }

    public static String buildUsersPanelsLayoutMode(User user) {
        StringBuilder content = new StringBuilder();
        content.append("<div id=\"drag-container-section\">");
        List<Section> sectionList = user.getSections();
        for (Section s : user.getSections()) {
            content.append("<div class=\"dashup-section\" id=\"s").append(s.getId()).append("\">");
            content.append("<div><span id=\"h" + s.getId() + "\" class=\"handle\"><i class=\"fas fa-arrows-alt handle\"></i> </span><span><input id=\"i" + s.getId() + "\" type=\"text\" name=\"txt\" value=\"");
            content.append(s.getName());
            content.append("\" onchange=\"inputChanged(this.value,"+s.getId()+")\"><button type=\"button\"  id=\"b" + s.getId() + "\" onclick=\"onDelete('s");
            content.append(s.getId());
            content.append("')\" class=\"btn btn-danger\">Delete</button></span></div>");
            content.append("<hr>");
            content.append("<div class=\"drag-container\" id=\"drag-container");
            content.append(s.getId()).append("\">");

            for (Panel p : s.getPanels()) {

                content.append("<div id=\"");
                content.append(p.getId());
                content.append("\" class=\"dashup-panel\">");
                        //.append(p.getName());
                content.append(p.getHtmlContent());
                content.append("</div>");
            }

            content.append("<div class=\"clear-float\"></div>");

            content.append("</div>");

            content.append("</div>");
        }
        content.append("</div>");
        return content.toString();

    }

}
