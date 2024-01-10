package com.lms.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Library {
    private ArrayList<Worker> workers;
    private ArrayList<User> users;
    private ArrayList<Book> books;
    private HashMap<User, List<Book>> borrowedBooks;
    
    // Database
    private static String url;
    private static String user;
    private static String password;
    
    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;
    private static PreparedStatement prprd_statement;
    

    public Library() {
        this.users = new ArrayList<User>();
        this.workers = new ArrayList<Worker>();
        this.borrowedBooks = new HashMap<User, List<Book>>();
        this.books = new ArrayList<Book>();
        
        
        // Database variables
        connection = null;
        statement = null;
        prprd_statement = null; // prepared statement
        result = null;
        
        url = "jdbc:mysql://localhost:3306/Library_Management_System";
        user = "root";
        password = "tamirat.mySQL";
    }
    
    private static Connection connect_to_database(){
        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch(SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
            System.out.println("SQLState     : " + e.getSQLState());
            System.out.println("VendorError  : " + e.getErrorCode());
            return null;
        }
    }
    
    private static void close_connection(){
        if(result != null){
                try{
                result.close();
                } catch (SQLException e){}
                result = null;
            }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e){}
            statement = null;
        }
        if(prprd_statement != null){
            try {
                prprd_statement.close();
            } catch (SQLException e){}
            prprd_statement = null;
        }
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e){}
            connection = null;
        }
    }

    public boolean admin_login(String username, String password) {
        String user_name = "", pass_word = "";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM Systemadmin");
            
            if(result.next()){
                user_name = result.getString("Username");
                pass_word = result.getString("Password");
            }
            return user_name.equals(username) && pass_word.equals(password); //System.out.println("SUCCESFULLY  LOGGEDIN");
            // System.out.println("INCORRECT USERNAME OR PASSWORD");
        } catch(SQLException e) {  
            return false;
           // System.out.println("SQLException : " + e.getMessage());
        } finally {
            close_connection();
        }
    }
    
   public boolean admin_signup(String worker_id, String user_name, String password) {
    String queryCheckWorker = "SELECT Full_name FROM Worker WHERE IdNumber = ?";
    String queryInsertAdmin = "INSERT INTO Systemadmin(Idnumber, Username, Password) VALUES (?, ?, ?)";

    try {
        // Check if the Worker exists
            connection = connect_to_database();
            prprd_statement = connection.prepareStatement(queryCheckWorker);
            prprd_statement.setString(1, worker_id);
            result = prprd_statement.executeQuery();

            if (result.next()) {
                // Worker exists, proceed to insert into Systemadmin
                    prprd_statement = connection.prepareStatement(queryInsertAdmin);
                    prprd_statement.setString(1, worker_id);
                    prprd_statement.setString(2, user_name);
                    prprd_statement.setString(3, password);

                    int rowsAffected = prprd_statement.executeUpdate();
                    return rowsAffected > 0;
            } else return false;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            close_connection();
        }
        return false;
    }
   
    public void resetPassword(String id_number, String new_password){
        String query = "UPDATE Systemadmin SET Password = ? WHERE  Idnumber = ?";
        try {
            connection = connect_to_database();
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, new_password);
                preparedStatement.setString(2, id_number);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Cell value updated successfully.");
                } else {
                    System.out.println("No matching records found for the given condition.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
    }

    public void hire_new_worker(Worker newWorker) {
        String name = newWorker.getFull_name();
        String pos = newWorker.getRole();
        String email = newWorker.getEmail_id();
        String worker_id = newWorker.getId_number();
        
        String query1 = "INSERT INTO Worker (Full_name, IdNumber, Position, Email) VALUES ";
        String query2 = "('" + name + "', '"  + worker_id + "', '" + pos + "', '"+ email +"')";
        
        try {
            connection = connect_to_database(); 
            statement = connection.createStatement();
            statement.executeUpdate(query1 + query2);
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally{
            close_connection();
        }
    }

    public void fire_worker(String worker_id) {
        connection = connect_to_database();
        try {
            statement = connection.createStatement();
            String query = "DELETE FROM Worker WHERE IdNumber = '" + worker_id +"'";
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
    }

    public void register_new_user(User new_user) {
        String user_id = new_user.getId_number();
        String user_name = new_user.getFull_name();
        String user_email = new_user.getEmail_id();
        String type = new_user.getType();
        
        String query1 = "INSERT INTO User (Fullname, Idnumber, Email, Type) VALUES ";
        String query2 = "('" + user_name + "', '" + user_id + "', '" + user_email + "', '" + type +"')";
        
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            statement.execute(query1 + query2);
        } catch( SQLException e){
            System.out.println(e.getMessage());
        } finally {
            close_connection();
        }
    }

    public boolean verify_user(String full_name, String id_number) {
        String query = "SELECT * FROM User WHERE Idnumber = '" + id_number + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while(result.next()){
                return full_name.equals(result.getString("Fullname")) && id_number.equals(result.getString("Idnumber"));
            }
        } catch (SQLException ex) {
            return false;
            // Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close_connection();
        }
        return false;
    }

    public void addAuthor(Author author){
        String insertAuthor = "INSERT INTO Author (Idnumber, Fullname, Email, Country, Biography) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = connect_to_database();
            prprd_statement = connection.prepareStatement(insertAuthor);
            prprd_statement.setString(1, author.getId_number());
            prprd_statement.setString(2, author.getFull_name());
            prprd_statement.setString(3, author.getEmail_id());
            prprd_statement.setString(4, author.getCountry());
            prprd_statement.setString(5, author.getBiography());
            
            prprd_statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            close_connection();
        }
    }
    
    public void addBook(Book new_book) {
    String query1 = "INSERT INTO Book (Bookid, Title, AuthorIdnumber, Publicationyear, Edition, Genre, Lang, Synopsis, Price) VALUES ";
    String query2 = "('" + new_book.getISBN()+ "', " +
                    "'" + new_book.getTitle() + "', " +
                    "'" + new_book.getAuthor().getId_number() + "', " +
                    new_book.getYear()+ ", " +
                    new_book.getEdition() + ", " +
                    "'" + new_book.getGenre() + "', " +
                    "'" + new_book.getLanguage()+ "', " +
                    "'" + new_book.getSynopsis() + "', " +
                    new_book.getPrice() + ")";
    try {
        connection = connect_to_database();
        statement = connection.createStatement();
        statement.executeUpdate(query1 + query2);
    } catch (SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
    } finally {
        close_connection();
    }
}

    
    public void borrowBook(String borrId, String user_id, String title, Datee rd, Datee dd) {
        String query= "SELECT Bookid from Book WHERE Title = ?";
        
        try {
            connection = connect_to_database();
            prprd_statement = connection.prepareStatement(query);
            prprd_statement.setString(1, title);
            result = prprd_statement.executeQuery();
            
            query = "INSERT INTO Borrow_transaction (borrowId, Bookid, UserID, Rent_date, Due_date) VALUES (?, ?, ?, ?, ?)";
            
            prprd_statement = connection.prepareStatement(query);
            
            prprd_statement.setString(1, borrId);
            if(result.next())
                prprd_statement.setString(2, result.getString("Bookid"));
            prprd_statement.setString(3, user_id);
            
            String rentday = Integer.toString(rd.getYear()) + "-" + 
                    Integer.toString(rd.getMonth()) + "-" + 
                    Integer.toString(rd.getDay());
            String dueday = Integer.toString(dd.getYear()) + "-" + 
                    Integer.toString(dd.getMonth()) + "-" + 
                    Integer.toString(dd.getDay());
            prprd_statement.setString(4, rentday);
            prprd_statement.setString(5, dueday);
            
            prprd_statement.executeUpdate();
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
    }
    
    public boolean returnBook(String bookid, String userid){
        String query = "DELETE FROM Borrow_transaction WHERE Bookid = '" + bookid + "' AND UserID = '" + userid + "'";
        int rowsAffected = 0;
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
            return (rowsAffected > 0);
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally{
            close_connection();
        }
        return false;
    }
    
    //// errorr ---
    public ArrayList<Book> getBookList() {
        String query = "SELECT * FROM Book";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column Name: " + metaData.getColumnName(i));
            }

            while(result.next()){
                books.add(new Book(result.getString("Title"),
                        getAuthorById(result.getString("AuthorIdNumber")), 
                        result.getString("Bookid"), 
                        result.getInt("Publicationyear"), 
                        result.getInt("Edition"), 
                        result.getString("Genre"), 
                        result.getString("Lang"), 
                        result.getString("Synopsis"), 
                        result.getDouble("Price")));
            }            
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
        return this.books;
    }
    
    public Worker getWorkerById(String id) {
        String query = "SELECT * FROM Worker WHERE IdNumber = '" + id + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while(result.next()){
                String full_n = result.getString("Full_name");
                String worker_id = result.getString("IdNumber");
                String role = result.getString("Position");
                String email = result.getString("Email");
                return new Worker(full_n, worker_id, role, email);
            }
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return null;
        } finally {
            close_connection();
        }
        return null;
    }

    public boolean getAvailabilityOfABook(String title) {
        String query = "SELECT * FROM Book where Title = '" + title + "'";
        try {
            connection= connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            if(result.next())
                return true;
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
        return false;
    }

    public static int getTotalNumberOfBooks() {
        return count_row("Book");
    }

    public static int getTotalNumberOfWorkers() {
        return count_row("Worker");
    }

    public static int getTotalNumberOfUsers() {
        return count_row("User");
    }
    
    private static int count_row(String table){
        String query = "SELECT COUNT(*) AS row_count FROM " + table;
        int rowCount;
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            if(result.next()){
                rowCount = result.getInt("row_count");
                return rowCount;
            }
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
        return -1;
    }

    private Author getAuthorById(String authorIdNumber) {
        String query = "SELECT * FROM Author WHERE Idnumber = '" + authorIdNumber + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while(result.next()){
                return new Author(result.getString("Biography"), result.getString("Country"), result.getString("Fullname"), result.getString("Idnumber"), result.getString("Email"));
            }
        } catch(SQLException e){
            System.out.println("SQLException: "+ e.getMessage());
            return null;
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("------------------Testing The Database---------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");

        Library lib = new Library();
        /* Author a = lib.getAuthorById("A000");
        if (a != null){
            System.out.println("Full name: " + a.getFull_name());
            System.out.println("Biography: " + a.getBiography());
        } 
        System.out.println("#of Authors in db: " + count_row("Book")); 
        
        if(lib.getAvailabilityOfABook("The Silent Symphony"))
            System.out.println("The book 'The Silent Symphony' exists in the database");
        else
            System.out.println("The book 'The Silent Symphony' doesn't exist in the database");
        
        Worker w = lib.getWorkerById("ETS1518/14");
        
        if (w != null){
            System.out.println("Full name: " + w.getId_number());
            System.out.println("Position : " + w.getEmail_id());
        } 
        
        ArrayList<Book> bl = lib.getBookList();
        if(bl != null){
        for(Book b : bl)
            System.out.println(b.getTitle());
        } 
        
        Datee bd = new Datee(2024, 01, 10);
        Datee dd = new Datee(2023, 02, 10);
        
        lib.borrowBook("B006_U001", "U001", "The Silent Symphony", bd, dd);
        
        Author a = lib.getAuthorById("A015");
        Book b = new Book("Shattered Reflections", a, "B015", 2022, 1, "Contemporary Fiction", "English", "Exploring shattered relationships and the pursuit of self-discovery.", 27.99);
        lib.addBook(b); 
        Author auth = new Author("Historical fiction writer weaving tales from ancient civilizations.", "Egypt", "Ahmed Ali", "A020", "ahmed.ali@email.com" );
        lib.addAuthor(auth);
        
        if(lib.verify_user("Eva Martinez", "U005")){
            System.out.println("User U005 is verified!");
        }
        
        User u = new User("Reader", "Jack Miller", "U010", "jack.miller@email.com");
        lib.register_new_user(u);
        
        lib.fire_worker("-001");
        
        Worker newW = new Worker("Grace Brown", "W010", "Administrator", "grace.brown@email.com");
        lib.hire_new_worker(newW); */
        
        //
        
        //lib.admin_signup("ETS1518/14", "tamiu", "abcd");
        lib.resetPassword("ETS1518/14", "abcd");
    }
}
