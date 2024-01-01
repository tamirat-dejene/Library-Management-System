import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private String title;
    private ArrayList<Author> author_s; 
    private String ISBN; 
    private int year;
    private int edition;
    private String genre;

    private String language;
    private ArrayList<Rating> ratings;
    private int numberOfPages;
    private String synopsis;
    private boolean available;
    private double price;
    

    /** Construct a book with the given fields */
    public Book(String title, ArrayList<Author> author_s, String isbn, int pub_year, int edition, String genre,
            String language, String synopsis, ArrayList<Rating> ratings, int numOfPages, double price, boolean available) {
        this.title = title;
        this.author_s = author_s;
        this.ISBN = isbn;
        this.year = pub_year;
        this.edition = edition;
        this.genre = genre;
        this.language = language;
        this.synopsis = synopsis;
        this.ratings = ratings;
        this.numberOfPages = numOfPages;
        this.price = price;
        this.available = available;   
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Author> getAuthors() {
        return author_s;
    }

    public String getIsbn() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }

    public int getEdition() {
        return edition;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public double getPrice() {
        return price;
    }

    public boolean getAvailable() {
        return available;
    }

    public void updateAvailablity(boolean newStatus) {
        this.available = newStatus;
    }

    public boolean isAvailable() {
        return available;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book otherBook = (Book) obj;
        return year == otherBook.year &&
                edition == otherBook.edition &&
                numberOfPages == otherBook.numberOfPages &&
                available == otherBook.available &&
                Double.compare(otherBook.price, price) == 0 &&
                Objects.equals(title, otherBook.title) &&
                Objects.equals(author_s, otherBook.author_s) &&
                Objects.equals(ISBN, otherBook.ISBN) &&
                Objects.equals(genre, otherBook.genre) &&
                Objects.equals(language, otherBook.language) &&
                Objects.equals(ratings, otherBook.ratings) &&
                Objects.equals(synopsis, otherBook.synopsis);
    }

    public boolean hasSameDetails(Book otherBook) {
        return year == otherBook.year &&
                edition == otherBook.edition &&
                numberOfPages == otherBook.numberOfPages &&
                available == otherBook.available &&
                Double.compare(otherBook.price, price) == 0 &&
                Objects.equals(title, otherBook.title) &&
                Objects.equals(author_s, otherBook.author_s) &&
                Objects.equals(ISBN, otherBook.ISBN) &&
                Objects.equals(genre, otherBook.genre) &&
                Objects.equals(language, otherBook.language) &&
                Objects.equals(synopsis, otherBook.synopsis);
    }



    

        /*private static boolean isValidIsbn(String isbn) {
        // Remove any non-digit characters from the input string
        isbn = isbn.replaceAll("\\D", "");
        // The input string must be of length 10 or 9
        if (!(isbn.length() == 9 || isbn.length() == 10))
            return false;
        /*
        * Compute check digit:
        * Multiply each digit in the number by its positional value (first digit has weight 10, second has weight 9,
        * Multiply every third character by 2, starting from index 0
        * Add all other characters to a running total
        * If the number of digits is 9, then subtract the product of the first two numbers from the sum
        
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
        } */
}

class Rating {
    String comment;
    int value;

    public Rating(String comment, int value) {
        this.comment = comment;
        this.value = value;
    }
}