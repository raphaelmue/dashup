package de.dashup.shared;

public class User extends DatabaseUser {
    private String token;
    private Settings settings;

    public User() { }

    public User(int id, String email, String name, String surname, String password, String salt) {
        super(id, email, name, surname, password, salt);
    }

    @Override
    public DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof DatabaseUser) {
            this.setId(databaseObject.getId());
            this.setName(((DatabaseUser) databaseObject).getName());
            this.setSurname(((DatabaseUser) databaseObject).getSurname());
            this.setEmail(((DatabaseUser) databaseObject).getEmail());
            this.setPassword(((DatabaseUser) databaseObject).getPassword());
            this.setSalt(((DatabaseUser) databaseObject).getSalt());
        }
        return this;
    }

    public String getToken() {
        return token;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
