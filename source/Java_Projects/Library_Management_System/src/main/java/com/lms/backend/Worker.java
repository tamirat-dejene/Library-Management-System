package com.lms.backend;

public class Worker extends Person {
    private final String role;

    public Worker(String full_name, String id_number, String role, String email) {
        super(full_name, id_number, email);
        this.role = role;
    }
    
    public String getRole() {
        return this.role;
    }
}
