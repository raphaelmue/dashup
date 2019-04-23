package de.dashup.shared.models;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("background_image")
    private String backgroundImage;
    private String theme;
    private String language;


    public DatabaseUser(Integer id, String email, String username, String password, String salt, String name, 
                        String surname, Date birthdate, String company, String biography, String backgroundImage, 
                        String theme, String language) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.company = company;
        this.biography = biography;
        this.backgroundImage = backgroundImage;
        this.theme = theme;
        this.language = language;
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

    public String getPassword() {
        return this.password;
    }

    public String getSalt() {
        return this.salt;
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

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getLanguage() {
        return this.language;
    }
    
}