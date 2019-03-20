package de.dashup.model.builder;

import de.dashup.shared.Panel;
import de.dashup.shared.Section;
import de.dashup.shared.User;

public class DashupBuilder {

    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        if (user.getSections() != null) {
            for (Section section : user.getSections()) {
                content.append("<div class=\"row\"><h3 class=\"sectionHeading\">")
                        .append(section.getName())
                        .append("</h3><hr/><div>");
                if (section.getPanels() != null) {
                    for (Panel panel : section.getPanels()) {
                        content.append(panel.getHtmlContent());
                    }
                }
                content.append("<div class=\"clear-float\"></div></div></div>");
            }
        }
        return content.toString();
    }

    public static String buildUsersPanelsLayoutMode(User user) {
        StringBuilder content = new StringBuilder();
        content.append("<div id=\"drag-container-section\">");
        if (user.getSections() != null) {
            for (Section s : user.getSections()) {
                content.append("<div class=\"dashup-section\" id=\"s").append(s.getId()).append("\">")
                        .append("<div><span id=\"h").append(s.getId())
                        .append("\" class=\"handle\"><i class=\"fas fa-arrows-alt handle\"></i> </span><span><input id=\"i")
                        .append(s.getId())
                        .append("\" type=\"text\" name=\"txt\" value=\"")
                        .append(s.getName())
                        .append("\" onchange=\"inputChanged(this.value,")
                        .append(s.getId())
                        .append(")\"><button type=\"button\"  id=\"b")
                        .append(s.getId())
                        .append("\" onclick=\"onDelete('s")
                        .append(s.getId())
                        .append("')\" class=\"btn btn-danger\">Delete</button></span></div>")
                        .append("<hr>")
                        .append("<div class=\"drag-container\" id=\"drag-container")
                        .append(s.getId()).append("\">");

                if (s.getPanels() != null) {
                    for (Panel p : s.getPanels()) {
                        content.append("<div id=\"");
                        content.append(p.getId());
                        content.append("\" class=\"dashup-panel\">");
                        //.append(p.getName());
                        content.append(p.getHtmlContent());
                        content.append("</div>");
                    }
                }

                content.append("<div class=\"clear-float\"></div>");

                content.append("</div>");

                content.append("</div>");
            }
        }
        content.append("</div>");
        return content.toString();
    }
}
