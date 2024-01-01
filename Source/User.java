import java.util.Objects;

public class User extends Person {
    private enum Type {
        STUDENT, EMPLOYEE, ANONYMOUS
    };

    private String email_id;
    private Type type;
    
    public User(String full_name, String id_number, String email_id, Type type) {
        super(full_name, id_number, email_id);
        this.email_id = email_id;
        this.type = type;
    }

    public String getUserEmail() {
        return this.email_id;
    }

    public Type getUserType() {
        return this.type;
    }

    public void updateUserEmail(String new_email) {
        this.email_id = new_email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User user = (User) obj;
        return this.getFullName().equalsIgnoreCase(user.getFullName()) &&
                this.getIdNumber().equals(user.getIdNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getFullName(), super.getIdNumber());
    }
}
