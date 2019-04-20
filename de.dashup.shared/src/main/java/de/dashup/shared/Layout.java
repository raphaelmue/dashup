package de.dashup.shared;

import de.dashup.shared.DatabaseModels.DatabaseUser;

import java.util.List;

public class Layout {

    private DatabaseUser user;
    private List<Section> sections;

    public Layout(DatabaseUser user, List<Section> sections){
        this.user = user;
        this.sections = sections;
    }

    public DatabaseUser getUser() {
        return user;
    }

    public void setUser(DatabaseUser user) {
        this.user = user;
    }

    public List<Section> getSections() {
        return this.sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}