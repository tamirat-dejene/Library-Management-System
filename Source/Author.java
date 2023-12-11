import java.util.Objects;
public class Author {
    private String name;
    private String country;

    // Constructors
    public Author() {
        this.name = "";
        this.country = "";
    }

    public Author(String n, String c) {
        if (n == null || c == null)
            throw new NullPointerException("Name and Country cannot be null");
        name = n;
        country = c;
    }

    // Copy constructor
    public Author(Author a) {
        Objects.requireNonNull(a);
        this.name = a.getName();
        this.country = a.getCountry();
    }
    // Getters and Setters
    public void setName(String n) {
        if (n == null)
            throw new NullPointerException("Name cannot be null");
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setCountry(String c) {
        if (c == null)
            throw new NullPointerException("Country cannot be null");
        country = c;
    }

    public String getCountry() {
        return country;
    }
                
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Author))return false;
        Author other=(Author)obj;
        return name.equals(other.name) && country.equals(other.country);
    }
}
