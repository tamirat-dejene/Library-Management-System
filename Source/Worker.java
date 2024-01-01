public class Worker extends Person {
    private String role;
    private String email;

    public Worker(String full_name, String id_number, String country, String role, String email) {
        super(full_name, id_number, country);
        this.email = email;
        this.role = role;
    }
    
    public String getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setNewEmail(String new_email) {
        this.email = new_email;
    }

    @Override
    public String toString() {
        return "Worker{" + "Full Name='" + getFullName() + '\'' + ", ID Number=" + getIdNumber() +
                ", Country='" + getCountry() + '\'' + ", Role='" + role + '\'' + ", Email='" + email + '\'' + '}';
    }   
}
