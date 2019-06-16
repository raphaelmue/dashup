package de.dashup.shared;

import de.dashup.shared.layout.Section;

import java.util.ArrayList;
import java.util.List;

public class User extends DatabaseUser {
    private static final long serialVersionUID = 6721259358362261977L;

    private String token;
    private Settings settings;
    private List<Section> sections;
    private List<Draft> drafts;

    public User() {
        this.sections = new ArrayList<>();
    }

    public User(int id, String email, String userName, String name, String surname, String password, String salt, Settings settings) {
        super(id, email, userName, name, surname, password, salt);

        this.settings = settings;
        this.sections = new ArrayList<>();
    }

    @Override
    public User fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabaseUser) {
            this.setId(databaseObject.getId());
            this.setName(((DatabaseUser) databaseObject).getName());
            this.setUserName(((DatabaseUser) databaseObject).getUserName());
            this.setSurname(((DatabaseUser) databaseObject).getSurname());
            this.setUserName(((DatabaseUser) databaseObject).getUserName());
            this.setEmail(((DatabaseUser) databaseObject).getEmail());
            this.setPassword(((DatabaseUser) databaseObject).getPassword());
            this.setSalt(((DatabaseUser) databaseObject).getSalt());
            this.setBirthDate(((DatabaseUser) databaseObject).getBirthDate());
            this.setCompany(((DatabaseUser) databaseObject).getCompany());
            this.setBio(((DatabaseUser) databaseObject).getBio());
        }
        return this;
    }

    public String getToken() {
        return token;
    }

    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void setDrafts(List<Draft> drafts) {
        this.drafts = drafts;
    }
}