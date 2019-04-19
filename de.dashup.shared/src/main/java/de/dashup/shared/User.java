package de.dashup.shared;

public class User {
    private int id;
    private String token;
    private Settings settings;
    private Layout layout;

    public User(int id, String token, Settings settings, Layout layout) {
        this.id = id;
        this.token = token;
        this.settings = settings;
        this.layout = layout;
    }

    public int getId() {
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