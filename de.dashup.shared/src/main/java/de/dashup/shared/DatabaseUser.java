package de.dashup.shared;

import java.io.Serializable;

public class DatabaseUser implements Serializable, DatabaseObject {
    private static final long serialVersionUID = -8352045621135035810L;

    private int id;
    private String email, userName, name, surname, password, salt;

    public DatabaseUser() {
    }

    public DatabaseUser(int id, String email, String userName, String name, String surname, String password, String salt) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.salt = salt;
    }

    void setId(int id) {
        this.id = id;
    }

    void setEmail(String email) {
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

    String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return this.getFullName();
    }
}
