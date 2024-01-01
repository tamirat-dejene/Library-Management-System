public class Person {

    private String full_name;
    private String id_number;
    private String country;

    public Person(String full_name, String id_number, String country) {
        this.full_name = full_name;
        this.id_number = id_number;
        this.country = country;
    }

    public String getFullName() {
        return this.full_name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getIdNumber() {
        return this.id_number;
    }
}
