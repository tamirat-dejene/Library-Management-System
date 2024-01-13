package com.lms.backend;

public class Person {

    private String full_name;
    private String id_number;
    private String email_id;

    public Person(String full_name, String id_number, String email_id) {
        this.full_name = full_name;
        this.id_number = id_number;
        this.email_id = email_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }    
}
