package de.dashup.shared;

public class User {
    private Integer id;
    private String token;
    private Settings settings;
    private Layout layout;

    public User(Integer id, String token, Settings settings, Layout layout) {
        this.id = id;
        this.token = token;
        this.settings = settings;
        this.layout = layout;
    }

    public Integer getID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Layout getLayout() {
        return this.layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

}