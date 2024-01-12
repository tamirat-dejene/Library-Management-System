package com.lms.backend;

import java.util.Objects;

public class User extends Person {

    private String type;

    public User(String type, String full_name, String id_number, String email_id) {
        super(full_name, id_number, email_id);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } 
}
