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
//        content.append("<div class=\"drag-drop-container\">");
        if (user.getSections() != null) {
            for (Section section : user.getSections()) {
                content.append("<div class=\"wrapper  col s12\" id=\"s")
                        .append(section.getId())
                        .append("\">")
                        .append("<div class=\"row\">")
                        .append("<div class=\"drag-drop-btn col s6 valign-wrapper\"><i class=\"drag-drop-btn fas fa-grip-lines col s1\" style=\"margin:0\"></i>")
                        .append(" <input class=\"col s4\" type=\"text\" style=\"margin:0\" value=\"")
                        .append(section.getName())
                        .append("\" />")
                        .append("<i class=\"section-minus fas fa-minus col s1\" style=\"margin:0\"></i></div>")
                        .append("</div>")
                        .append("<div class=\"bloc col s12\">");

//


                if (section.getPanels() != null) {
                    for (Panel panel : section.getPanels()) {
//                        content.append("<div id=\"");
//                        content.append(p.getId());
//                        content.append("\" class=\"dashup-panel\">");
//                        //.append(p.getName());
//                        content.append(p.getHtmlContent());
//                        content.append("</div>");
                        String sizeStyleClass = panel.getSize().getStyleClass();
                        String size = panel.getSize().getName();
                        String panelId = String.valueOf(panel.getId());


                        content.append("<div class=\"bloc--inner col ")
                                .append(sizeStyleClass)
                                .append(" z-depth-1\"")
                                .append("id=\"p")
                                .append(panelId)
                                .append("\"")
                                .append("size=\"")
                                .append(size)
                                .append("\" state=\"default\">")
                                .append("<div class=\"row\">")
                                .append("<div class=\"col s11\">")
                                .append("<h6>")
                                .append(panel.getSize())
                                .append("</h6>")
                                .append("</div>")
                                .append("<div class=\"col s1\">")
                                .append("<a class='dropdown-trigger' href='#' data-target='dropdown1'><i class=\"fas fa-ellipsis-v\" style=\"padding-top:10px\"></i></a>")
                                .append("</div>")
                                .append("</div>")
                                .append("</div>");
                    }
                }



//                content.append("</div>");

                content.append("</div>")
                        .append("</div>");
            }
        }
//        content.append("</div>");
        String htmlReturn = content.toString();
        return htmlReturn;
    }
}
