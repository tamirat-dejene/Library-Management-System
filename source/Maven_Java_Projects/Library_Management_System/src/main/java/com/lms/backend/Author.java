package com.lms.backend;

public class Author extends Person {
    private String biography;
    private String country;

    public Author(String biography, String country, String full_name, String id_number, String email_id) {
        super(full_name, id_number, email_id);
        this.biography = biography;
        this.country = country;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
