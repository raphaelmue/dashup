package de.dashup.model.builder;

import de.dashup.shared.Panel;
import de.dashup.shared.Section;
import de.dashup.shared.User;

public class DashupBuilder {

    private final PanelLoader panelLoader = PanelLoader.getInstance();


    public static String buildUsersPanels(User user) {
        StringBuilder content = new StringBuilder();
        for (Section s :user.getSections()) {
            content.append("<div> <h1>"+s.getName()+"</h1> <hr/> <div>");
            for (Panel p: s.getPanels()) {
                content.append(p.getHtmlContent());
            }
            content.append("</div> </div>");
        }
        return content.toString();
    }

}
