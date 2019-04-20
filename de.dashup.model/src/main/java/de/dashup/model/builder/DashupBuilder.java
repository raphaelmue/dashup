package de.dashup.model.builder;

import de.dashup.shared.Section;
import de.dashup.shared.User;
import de.dashup.shared.Widget;

import java.util.List;

public class DashupBuilder {

    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        List<Section> sections = user.getLayout().getSections();
        if (sections.size() > 0) {
            for (Section section : sections) {
                List<Widget> widgets = section.getWidgets();
                content.append("<div class=\"row\"><h3 class=\"sectionHeading\">")
                        .append(section.getName())
                        .append("</h3><hr/><div>");
                if (widgets.size() > 0) {
                    for (Widget widget: widgets) {
                        content.append(widget.getHtmlContent());
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
        List<Section> sections = user.getLayout().getSections();
        if (sections.size() >= 0) {
            for (Section section: sections) {
                content.append("<div class=\"dashup-section\" id=\"s").append(section.getID()).append("\">")
                        .append("<div><span id=\"h").append(section.getID())
                        .append("\" class=\"handle\"><i class=\"fas fa-arrows-alt handle\"></i> </span><span><input id=\"i")
                        .append(section.getID())
                        .append("\" type=\"text\" name=\"txt\" value=\"")
                        .append(section.getName())
                        .append("\" onchange=\"inputChanged(this.value,")
                        .append(section.getID())
                        .append(")\"><button type=\"button\"  id=\"b")
                        .append(section.getID())
                        .append("\" onclick=\"onDelete('s")
                        .append(section.getID())
                        .append("')\" class=\"btn btn-danger\">Delete</button></span></div>")
                        .append("<hr>")
                        .append("<div class=\"drag-container\" id=\"drag-container")
                        .append(section.getID()).append("\">");
                List<Widget> widgets = section.getWidgets();
                if (widgets.size() >= 0) {
                    for (Widget widget : widgets) {
                        content.append("<div id=\"");
                        content.append(widget.getID());
                        content.append("\" class=\"dashup-panel\">");
                        content.append(widget.getHtmlContent());
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