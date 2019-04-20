package de.dashup.shared.DatabaseModels;

import java.util.Date;

public class DatabaseUser extends DatabaseObject {

    private Integer id;
    private String email;
    private String username;
    private String name;
    private String surname;
    private Date birthdate;
    private String company;
    private String biography;
    private String password;
    private String salt;


    public DatabaseUser(Integer id, String email, String username, String name, String surname, Date birthdate,
                        String company, String biography, String password, String salt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.company = company;
        this.biography = biography;
        this.password = password;
        this.salt = salt;
    }

    public Integer getID() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public String getCompany() {
        return this.company;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt() {
        return this.salt;
    }

}