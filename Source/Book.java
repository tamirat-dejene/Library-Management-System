import java.util.Objects;

public class Book {
    private String title;
    private String ISBN;
    private int year;
    private Author author;



    // Constructor
    public Book() {
        this("No Title", "000-000000000000", 0, new Author());
    }

    public Book(String t, String i, int y, Author a) {
        // Check inputs
        if (t == null || i == null || y < 0 || a == null)
            throw new IllegalArgumentException();
        this.title = t;
        this.ISBN = i;
        this.year = y;
        this.author = a;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Book))
            return false;
        Book book = (Book) o;

        return year == book.year && Objects.equals(title, book.title) && Objects.equals(ISBN, book.ISBN);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, ISBN, year);
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        // Check if valid title
        if (title == null)
            throw new IllegalArgumentException("Invalid Title");
        this.title = title;
    }

    public String getIsbn() {
        return ISBN;
    }

    public void setIsbn(String isbn) {
        // Check if valid ISBN
        if (isbn == null || !isValidIsbn(isbn))
            throw new IllegalArgumentException("Invalid ISBN");
        ISBN = isbn;
    }
        

    private static boolean isValidIsbn(String isbn) {
        // Remove any non-digit characters from the input string
        isbn = isbn.replaceAll("\\D", "");
        // The input string must be of length 10 or 9
        if (!(isbn.length() == 9 || isbn.length() == 10))
            return false;
        /* Compute check digit:
        * Multiply each digit in the number by its positional value (first digit has weight 10, second has weight 9,
        * Multiply every third character by 2, starting from index 0
        * Add all other characters to a running total
        * If the number of digits is 9, then subtract the product of the first two numbers from the sum
        */
        long sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            if (i % 3 == 0) {
                if (c < '0' || c > '9')
                    return false;
                else
                    sum += ((i + 1) % 2 == 0 ? 2 : 1) * (c - '0');
            } else if (c < '0' || c > '9') {
                return false;
            } else {
                sum += c - '0';
            }
        }
        int checkDigit = (int) (10 - (sum % 10));
        if (checkDigit == 10)
            checkDigit = 0;
        return isbn.endsWith(Integer.toString(checkDigit));
    }

    

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        // Check if valid Year
        if (year < 0)
            throw new IllegalArgumentException("Invalid Year");
        this.year = year;
    }

    public Author getAuthor() {
        return new Author(this.author);
    }
}