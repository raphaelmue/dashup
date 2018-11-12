package de.dashup.shared;

import java.io.Serializable;

public class User implements Serializable, DatabaseObject {
    private static final long serialVersionUID = -8352045621135035810L;

    private int id;
    private String email, name, surname, password, salt;

    public User() {}

    public User(int id, String email, String name, String surname, String password, String salt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.salt = salt;
    }

    @Override
    public DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject instanceof User) {
            this.setId(databaseObject.getId());
            this.setName(((User) databaseObject).getName());
            this.setSurname(((User) databaseObject).getSurname());
            this.setEmail(((User) databaseObject).getEmail());
            this.setPassword(((User) databaseObject).getPassword());
            this.setSalt(((User) databaseObject).getSalt());
        }
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }
}
