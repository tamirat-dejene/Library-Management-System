public class Member {
    private String full_name;
    private String gender;
    private String id;

    // Default constructor
    public Member() {
        full_name = "-";
        gender = "-";
        id = "-";
    }

    // Prametrized constructor
    public Member(String name, String gender, String idnum) {
        this.full_name = valid_name(name) ? name : "-";
        this.gender = valid_gender(gender) ? gender : "-";
        this.id = valid_id(idnum) ? idnum : "-";
    }

    // Copy constructor
    public Member(Member m) {
        this(m.getName(), m.getGender(), m.getId());
    }
    // Accessor methods
    public String getName() {
        return full_name;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return new Member(this);
    }

    public void print() {
        System.out.println(this.toString());
    }

    // Mutator method
    public void setName(String name) {
        this.full_name = valid_name(name) ? name : "-";
    }

    public void setId(String id) {
        this.id = valid_id(id) ? id : "-";
    }

    public void setGender(String gender) {
        this.gender = valid_gender(gender) ? gender : "-";
    }

    // Validation methods
    static boolean valid_name(String n) {
        return n.matches("[a-z A-Z]+");
    }

    static boolean valid_gender(String gen) {
        return "M".equals(gen.toUpperCase()) || "F".equals(gen.toUpperCase()) ||
                "male".equals(gen.toLowerCase()) || "female".equals(gen.toLowerCase());
    }

    static boolean valid_id(String idnum) {
        return idnum.matches("\\S{3}\\d{4}/\\d{2}");
    }

    // Compare two objects
    public boolean equals(Member anotherMember) {
        return full_name.equals(anotherMember.full_name) &&
                gender.equals(anotherMember.gender) &&
                id.equals(anotherMember.id);
    }

    // Print the object
    @Override
    public String toString() {
        return "Name: " + full_name + ", Gender: " + gender + ", ID: " + id;
    }


    public static void main(String[] args) {
        Member testMember;
        testMember = new Member("Tamirat Dejenie Wondimu", "Male", "ETS1518/14");
        testMember.print();
    }
}