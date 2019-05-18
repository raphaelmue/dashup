package de.dashup.model.builder;

import de.dashup.shared.layout.Section;
import de.dashup.shared.User;
import de.dashup.shared.layout.Widget;

public class DashupBuilder {

    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        if (user.getSections() != null) {
            for (Section section : user.getSections()) {
                content.append("<div class=\"row\"><h3 class=\"sectionHeading\">")
                        .append(section.getName())
                        .append("</h3><hr/><div>");
                if (section.getWidgets() != null) {
                    for (Widget widget : section.getWidgets()) {
                        content.append(widget.getCodeWithWrapper());
                    }
                }
                content.append("<div class=\"clear-float\"></div></div></div>");
            }
        }
        return content.toString();
    }

    public static String buildUsersPanelsLayoutMode(User user) {
        StringBuilder content = new StringBuilder();
        if (user.getSections() != null) {
            for (Section section : user.getSections()) {
                content.append("<div class=\"wrapper  col s12\" id=\"s")
                        .append(section.getId())
                        .append("\">")
                        .append("<div class=\"row\">")
                        .append("<div class=\"drag-drop-btn col s6 valign-wrapper input-field\"><i class=\"drag-drop-btn fas fa-grip-lines col s1\" style=\"margin:0\"></i>")
                        .append(" <input class=\"col s4\" type=\"text\" style=\"margin:0\" value=\"")
                        .append(section.getName())
                        .append("\" />")
                        .append("<i class=\"section-minus fas fa-minus col s1\" style=\"margin:0\"></i></div>")
                        .append("</div>")
                        .append("<div class=\"bloc col s12\">");

                if (section.getWidgets() != null) {
                    for (Widget widget : section.getWidgets()) {
                        String sizeStyleClass = widget.getSize().getStyleClass();
                        String size = widget.getSize().getName();
                        String panelId = String.valueOf(widget.getId());

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
                                .append("<div class=\"col s12 widget-content\">")
                                .append("<a class='dropdown-trigger' href='#' data-target='dropdown1'><i class=\"colored-text fas fa-ellipsis-v\"></i></a>")
                                .append("<h6 class=\"center-align\">")
                                .append(widget.getName())
                                .append("</h6>")
                                .append("</div>")
                                .append("</div>")
                                .append("</div>");
                    }
                }
                content.append("</div>")
                        .append("</div>");
            }
        }
        return content.toString();
    }
}
