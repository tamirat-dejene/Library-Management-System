package com.lms.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Library extends BookTransaction  {
    private ArrayList<Worker> workers;
    private ArrayList<User> users;
    private ArrayList<Book> books;
    private ArrayList<Author> authors;
    private ArrayList<BookTransaction> issued_books;

    // Database
    private static String url;
    private static String user;
    private static String password;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;
    private static PreparedStatement prprd_statement;

    /**
     *  Team INNOV8
     */
    public Library() {
        super();
        this.users = new ArrayList<>();
        this.setWorkers(new ArrayList<>());
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.issued_books = new ArrayList<>();

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
            //System.out.println("SQLException : " + e.getMessage());
            //System.out.println("SQLState     : " + e.getSQLState());
            //System.out.println("VendorError  : " + e.getErrorCode());
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
        String user_name, pass_word;
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM Systemadmin");

            while(result.next()){
                user_name = result.getString("Username");
                pass_word = result.getString("Password");
                if (user_name.equals(username) && pass_word.equals(password)) {
					return true;
				}
            }
            return false;
            //System.out.println("SUCCESFULLY  LOGGEDIN");
            // System.out.println("INCORRECT USERNAME OR PASSWORD");
        } catch(SQLException e) {
            return false;
           // System.out.println("SQLException : " + e.getMessage());
        } finally {
            close_connection();
        }
    }

    public boolean admin_signup(String worker_id, String full_name, String email, String user_name, String password) {
        String queryCheckWorker = "SELECT Full_name FROM Worker WHERE IdNumber = ?";
        String queryInsertAdmin = "INSERT INTO Systemadmin(Idnumber, Username, Password) VALUES (?, ?, ?)";

        try {
            hire_new_worker(new Worker(full_name,worker_id,"Admin",email));
        } catch(SQLException ex){
            // Just in case the worker doesn't exist in the database
        }

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
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            close_connection();
        }
        return false;
    }

    public boolean resetPassword(String id_number, String new_password){
        String query = "UPDATE Systemadmin SET Password = ? WHERE  Idnumber = ?";
        try {
            connection = connect_to_database();
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, new_password);
                preparedStatement.setString(2, id_number);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // System.out.println("Cell value updated successfully.");
                    return true;
                } else {
                   //  System.out.println("No matching records found for the given condition.");
                   return false;
                }
            }
        } catch (SQLException e) {
           // System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
        return false;
    }

    public void hire_new_worker(Worker newWorker) throws SQLException {
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
            throw e;
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
            //System.out.println(e.getMessage());
        } finally {
            close_connection();
        }
    }

    public void removeUser(String id_number){
        String query = "DELETE FROM User WHERE Idnumber = '" + id_number + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch(SQLException ex){
            //
        } finally {
            close_connection();
        }
    }

    public boolean isVerified(String id_number) {
        String query = "SELECT * FROM User WHERE Idnumber = '" + id_number + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while(result.next()){
                return id_number.equals(result.getString("Idnumber"));
            }
        } catch (SQLException ex) {
            return false;
        } finally {
            close_connection();
        }
        return false;
    }

    public ArrayList<User> getUserList(){
        ResultSet resultSet = null;
        String query = "SELECT * FROM User";
        try{
            connection = connect_to_database();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                users.add(new User(resultSet.getString("Type"),
                        resultSet.getString("Fullname"),
                        resultSet.getString("Idnumber"),
                        resultSet.getString("Email")));
            }
        } catch(SQLException e){
            return null;
        } finally {
            close_connection();
            if(resultSet!=null) {
				try {
				    resultSet.close();
				} catch (SQLException ex) {
				}
			}
        }
        return users;
    }

    public ArrayList<Author> getAuthorList(){
        ResultSet resultSet = null;
        String query = "SELECT * FROM Author";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                authors.add(new Author(resultSet.getString("Biography"),
                        resultSet.getString("Country"),
                        resultSet.getString("Fullname"),
                        resultSet.getString("Idnumber"),
                        resultSet.getString("Email")));
            }
            return authors;
        } catch(SQLException e){
            //
        } finally {
            close_connection();
            if(resultSet!=null) {
				try {
				    resultSet.close();
				} catch (SQLException ex) {
				    ////
				}
			}
        }
        return authors;
    }

    public ArrayList<BookTransaction> getIssuedBooks(){
        String query = "SELECT * FROM Borrow_transaction";
        ResultSet resultSet = null;

        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                issued_books.add(new BookTransaction(resultSet.getString("borrowId"),
                        resultSet.getString("Bookid"),
                        resultSet.getString("UserID"),
                        resultSet.getString("Rent_date"),
                        resultSet.getString("Due_date")));
            }
            return issued_books;
        } catch(SQLException e){
            /// I donnnooo
        } finally{
            close_connection();
            if(resultSet != null) {
				try {
				    resultSet.close();
				} catch (SQLException ex) {
				    ////
				}
			}
        }
        return issued_books;
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

    public int removeAuthor(String auth_id) throws SQLException{
        String query = "DELETE FROM Author WHERE Idnumber = '" + auth_id + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            int rows = statement.executeUpdate(query);
            return rows;
        } catch(SQLException e){
            throw e;
        } finally{
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

    public int removeBook(String bookId) throws SQLException{
        String query = "DELETE FROM Book WHERE bookId = '" + bookId + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(query);
            return rowAffected;
        } catch(SQLException ex){
            throw ex;
        } finally{
            close_connection();
        }
    }

    public int borrowBook(String borrowId, String userId, String title, String issueDate, String dueDate) {
        String selectQuery = "SELECT Bookid FROM Book WHERE Title = ?";
        String insertQuery = "INSERT INTO Borrow_transaction (borrowId, Bookid, UserID, Rent_date, Due_date) VALUES (?, ?, ?, ?, ?)";
        String bookId;

        try {
            connection = connect_to_database();
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, title);
                ResultSet selectResult = selectStatement.executeQuery();

                if (selectResult.next()) {
                    bookId = selectResult.getString("Bookid");
                    java.sql.Date rentDate = java.sql.Date.valueOf(issueDate);
                    java.sql.Date dueDateSql = java.sql.Date.valueOf(dueDate);

                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, borrowId);
                        insertStatement.setString(2, bookId);
                        insertStatement.setString(3, userId);
                        insertStatement.setDate(4, rentDate);
                        insertStatement.setDate(5, dueDateSql);

                        int rowAffected = insertStatement.executeUpdate();
                        return rowAffected;
                    }
                } else {
                    return 0;
                }
            }

        } catch (SQLException e) {
            return 0;
        } finally {
            close_connection();
        }
    }

    public String getBookId(String title){
        String query = "SELECT Bookid FROM Book WHERE Title = '" + title + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            if(result.next()) {
				return result.getString("Bookid");
			} else {
				return "";
			}
        } catch(SQLException e){

        }
        return "";
    }

    public void returnBook(String bookid, String userid){
        String query = "DELETE FROM Borrow_transaction WHERE Bookid = '" + bookid + "' AND UserID = '" + userid + "'";
       // int rowsAffected;
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            statement.executeUpdate(query);
           // return (rowsAffected > 0);
        } catch(SQLException e){
           // System.out.println("SQLException: " + e.getMessage());
        } finally{
            close_connection();
        }
    }

    public ArrayList<Book> getBookList() throws Exception {
        ResultSet resultSet = null;
        String query = "SELECT * FROM Book";
        try {
            connection = connect_to_database();
            connection.setAutoCommit(true);

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String title = resultSet.getString("Title");
                String auth_id = resultSet.getString("AuthorIdNumber");
                String bookId = resultSet.getString("Bookid");
                int year = resultSet.getInt("Publicationyear");
                int edition = resultSet.getInt("Edition");
                String genre = resultSet.getString("Genre");
                String lang = resultSet.getString("Lang");
                String synopsis = resultSet.getString("Synopsis");
                Double price = resultSet.getDouble("Price");

                Author author = getAuthorById(auth_id);
                books.add(new Book(title, author, bookId, year, edition, genre,lang, synopsis, price));
            }
        } catch(SQLException e){
            throw new Exception("BOOKS NOT FOUND");
        } finally {
            close_connection();
            if(resultSet!=null) {
				try {
				    resultSet.close();
				} catch (SQLException ex) {
				}
			}
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
          //  System.out.println("SQLException: " + e.getMessage());
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
            if(result.next()) {
				return true;
			}
        } catch (SQLException e){
           // System.out.println("SQLException: " + e.getMessage());
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
            //System.out.println("SQLException: " + e.getMessage());
        } finally {
            close_connection();
        }
        return -1;
    }

    public Author getAuthorById(String authorIdNumber) throws Exception, SQLException {
        ResultSet result;
        String query = "SELECT * FROM Author WHERE Idnumber = '" + authorIdNumber + "'";
        try {
            connection = connect_to_database();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while(result.next()){
                return new Author(result.getString("Biography"), result.getString("Country"), result.getString("Fullname"), result.getString("Idnumber"), result.getString("Email"));
            }
        } catch(SQLException e){
            throw new Exception("AUTHOR NOT FOUND IN THE DATABASE");
        } finally{
            close_connection();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("------------------Testing The Database---------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");

        new Library();




    }

	public ArrayList<Worker> getWorkers() {
            return workers;
	}

	public void setWorkers(ArrayList<Worker> workers) {
		this.workers = workers;
	}
}
