public class Author extends Person {
    private String biography;
    private String email;

    public Author(String full_name, String ssn, String country, String biography, String email_id) {
        super(full_name, ssn, country);
        this.biography = biography;
        this.email = email_id;
    }

    public void setName(String n) {
        
    }

    public String getAuthorName() {
        return getFullName();
    }

    public String getEmailId() {
        return this.email;
    }

    public String getBiography() {
        return this.biography;
    }

    public void updateEmailId(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public String toString() {
        return ("Author Name: " + getAuthorName() + ", ID: " + getIdNumber() + ", Email: " + getEmailId()
                + "\n Biography: " + getBiography());
    }
}
