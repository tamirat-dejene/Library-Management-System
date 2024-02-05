package com.lms.library_management_system;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.lms.backend.Author;
import com.lms.backend.Book;
import com.lms.backend.BookTransaction;
import com.lms.backend.Library;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BookController implements Initializable {

    // Author db cols
    @FXML private TableView<Author> AuthorTableView;
    @FXML private TableColumn<Author, String> AuthDb_colBio;
    @FXML private TableColumn<Author, String> AuthDb_colCountry;
    @FXML private TableColumn<Author, String> AuthDb_colEmail;
    @FXML private TableColumn<Author, String> AuthDb_colFullName;
    @FXML private TableColumn<Author, String> AuthorDb_colAuthId;


    // BookShelf table cols
    @FXML private TableView<Book> BookShelfTableView;
    @FXML private TableColumn<Book, Integer> BookShelf_ColEdition;
    @FXML private TableColumn<Book, String> BookShelf_colAuthorId;
    @FXML private TableColumn<Book, String> BookShelf_colBookId;
    @FXML private TableColumn<Book, String> BookShelf_colGenre;
    @FXML private TableColumn<Book, String> BookShelf_colLanguage;
    @FXML private TableColumn<Book, Double> BookShelf_colPrice;
    @FXML private TableColumn<Book, String> BookShelf_colSynopsis;
    @FXML private TableColumn<Book, String> BookShelf_colTitle;
    @FXML private TableColumn<Book, Integer> BookShelf_colYear;

    // Issuedbooks table cols
    @FXML private TableView<BookTransaction> issuedBookListTableView;
    @FXML private TableColumn<BookTransaction, String> issuedBook_colBookId;
    @FXML private TableColumn<BookTransaction, String> issuedBook_colIssueDate;
    @FXML private TableColumn<BookTransaction, String> issuedBook_colIssueId;
    @FXML private TableColumn<BookTransaction, String> issuedBook_colUserId;
    @FXML private TableColumn<BookTransaction, String> issuedBook_returnDate;

    // Issue book form controls
    @FXML private DatePicker dueDateDatePicker;
    @FXML private DatePicker issueDateDatePicker;
    @FXML private TextField issueId_tf;
    @FXML private TextField issuedBookTitle_tf;
    @FXML private TextField issuedUserId_tf;

    // Back Panes
    @FXML private SplitPane mainSplitPane;
    @FXML private AnchorPane upperAnchorPane;
    @FXML private AnchorPane lowerAnchorPane;

    // Tables container
    @FXML private TabPane tableViews_TabPane;
    @FXML private ScrollPane booksTableScrollPane;
    @FXML private ScrollPane authorTableScrolPane;
    @FXML private ScrollPane issuedBookScrollPane;

    // New Author form controls
    @FXML private TextField newAuthorBio_tf;
    @FXML private TextField newAuthorCountry_tf;
    @FXML private TextField newAuthorEmail_tf;
    @FXML private TextField newAuthorFullname_tf;
    @FXML private TextField newAuthorId_tf;

    // New book form controls
    @FXML private TextField newBookAuthId_tf;
    @FXML private TextField newBookEdition_tf;
    @FXML private TextField newBookGenre_tf;
    @FXML private TextField newBookId_tf;
    @FXML private TextField newBookLang_tf;
    @FXML private TextField newBookPrice_tf;
    @FXML private TextField newBookSynop_tf;
    @FXML private TextField newBookTitle_tf;
    @FXML private TextField newBookYear_tf;

    // Return book form controls
    @FXML private TextField returnIssuedBookId_tf;
    @FXML private TextField returnIssuedUserId_tf;
    @FXML private Label returnStatusLabel;

    // Remove Book, Author
    @FXML private TextField removeBookTextField;
    @FXML private TextField removeAuthorTextField;

    @FXML
    void addNewAuthor(ActionEvent event) {
        String bio = newAuthorBio_tf.getText();
        String country = newAuthorCountry_tf.getText();
        String email = newAuthorEmail_tf.getText();
        String fullname = newAuthorFullname_tf.getText();
        String id = newAuthorId_tf.getText();

        Library lib = new Library();
        if(bio.isBlank() || country.isBlank() || email.isBlank() || fullname.isBlank() || id.isBlank()) {
			showAlertMessage("INVALID INPUTS");
		} else{
            Author newAuthor = new Author(bio, country, fullname, id, email);
            lib.addAuthor(newAuthor);
            AuthorTableView.getItems().add(newAuthor);
        }
    }

    @FXML
    void addNewBook(ActionEvent event) {
        try{
            String auth_id = newBookAuthId_tf.getText();
            String title = newBookTitle_tf.getText();
            String genre = newBookGenre_tf.getText();
            String bookId = newBookId_tf.getText();
            String lang = newBookLang_tf.getText();
            String synopsis = newBookSynop_tf.getText();
            int edition = Integer.parseInt(newBookEdition_tf.getText());
            int year = Integer.parseInt(newBookYear_tf.getText());
            double price = Double.parseDouble(newBookPrice_tf.getText());

            Library lib = new Library();
            if(auth_id.isBlank() || title.isBlank() || genre.isBlank() || bookId.isBlank() || lang.isBlank() || synopsis.isBlank() || edition < 1 || year < 0 || price < 0) {
				showAlertMessage("Invalid Inputs.");
			} else{
                Book b = new Book (title, lib.getAuthorById(auth_id), bookId, year, edition, genre, lang, synopsis, price);
                lib.addBook(b);
                BookShelfTableView.getItems().add(b);
            }
        } catch(Exception ex){
            showAlertMessage("AUTHOR NOT FOUND IN THE DATABASE. ADD THE AUTHOR FIRST.");
        }
    }

    @FXML
    void issueBook(ActionEvent event) throws Exception {
        try {
            String issue_id = issueId_tf.getText();
            String user_id = issuedUserId_tf.getText();
            String book_title = issuedBookTitle_tf.getText();
            String issueDate = issueDateDatePicker.getValue().toString();
            String dueDate = dueDateDatePicker.getValue().toString();

           // System.out.println(issueDateDatePicker.getValue().toString());
           // System.out.println(dueDateDatePicker.getValue().toString());

            Library lib = new Library();
            String book_id = lib.getBookId(book_title);
            if(issue_id.isBlank() || user_id.isBlank() || book_title.isBlank() || issueDate.isBlank() || dueDate.isBlank() || book_id.isBlank()) {
				showAlertMessage("INVALID INPUT DATA.");
			} else {
                int rows = lib.borrowBook(issue_id, user_id, book_title, issueDate, dueDate);
                if(rows < 1) {
					showAlertMessage("The Book is not returned (yet not available)");
				} else {
                    BookTransaction newTransaction = new BookTransaction(issue_id, book_id, user_id, issueDate, dueDate);
                    issuedBookListTableView.getItems().add(newTransaction);
                }
            }
        } catch(Exception e){
            showAlertMessage("SYSTEM ERROR!");
        }
    }

    @FXML
    void returnIssuedBook(ActionEvent event) {
        String bookId = returnIssuedBookId_tf.getText();
        String userId = returnIssuedUserId_tf.getText();

        Library lib = new Library();
        try{
            if(bookId.isBlank() || userId.isBlank()) {
				showAlertMessage("Invalid Input");
			} else{
                lib.returnBook(bookId, userId);
                refreshButton(event);
            }
            returnStatusLabel.setText("Thankyou. Come again!");
        } catch(Exception e){
            showAlertMessage("Operation Failed. Retry.");
        }
    }

    @FXML
    void removeBookButton(ActionEvent event) {
        String book_id = removeBookTextField.getText();
        Library lib = new Library();
        try{
            int x = lib.removeBook(book_id);
            refreshButton(event);
            if(x == 0) {
				showAlertMessage("THE BOOK IS NOT FOUND");
			}
        } catch(SQLException ex){
            showAlertMessage("THE BOOK IS NOT FOUND");
        }
    }

    @FXML
    void removeAuthorButton(ActionEvent event) {
        String author_id = removeAuthorTextField.getText();
        Library lib = new Library();
        try {
            int x = lib.removeAuthor(author_id);
            refreshButton(event);
            if(x == 0) {
				showAlertMessage("THE AUTHOR IS NOT FOUND");
			}
        } catch(SQLException ex){
            showAlertMessage("THE AUTHOR IS ASSOCIATED WITH LIBRARY BOOK. FIRST REMOVE THE BOOK");
        }
    }

    @FXML
    void refreshButton(ActionEvent event) {
        populate_bookshelf(load_data());
        populate_authortable(load_author_data());
        populate_issueBooks(load_issued_books());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize_BookShelfTableViewCols();
        initialize_AuthorTableViewCols();
        initialize_issuedBookListTableViewCols();

        populate_bookshelf(load_data());
        populate_authortable(load_author_data());
        populate_issueBooks(load_issued_books());
    }

    private void showAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("ERROR OCCURED WHILE PROCESSING THE DATA");
        alert.showAndWait();
    }

    private ArrayList<Book> load_data(){
        try {
            Library lib = new Library();
            return lib.getBookList();
        } catch (Exception ex) {
            showAlertMessage(ex.getMessage());
            return null;
        }
    }

    private ArrayList<Author> load_author_data(){
        Library lib = new Library();
        return lib.getAuthorList();
    }

    private ArrayList<BookTransaction> load_issued_books(){
        Library lib = new Library();
        return lib.getIssuedBooks();
    }

    private void initialize_AuthorTableViewCols() {
        AuthDb_colBio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBiography()));
        AuthDb_colCountry.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        AuthDb_colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail_id()));
        AuthDb_colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFull_name()));
        AuthorDb_colAuthId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_number()));
    }

    private void initialize_BookShelfTableViewCols() {
        BookShelf_ColEdition.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdition()).asObject());
        BookShelf_colAuthorId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getId_number()));
        BookShelf_colBookId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        BookShelf_colGenre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        BookShelf_colLanguage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
        BookShelf_colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        BookShelf_colSynopsis.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSynopsis()));
        BookShelf_colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        BookShelf_colYear.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
    }

    private void initialize_issuedBookListTableViewCols() {
        issuedBook_colBookId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook_id()));
        issuedBook_colIssueDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIssue_date()));
        issuedBook_returnDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReturn_date()));
        issuedBook_colIssueId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIssue_id()));
        issuedBook_colUserId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser_id()));
    }

    private void populate_bookshelf(ArrayList<Book> loaded_data) {
        ObservableList<Book> booklist = FXCollections.observableArrayList(loaded_data);
        BookShelfTableView.setItems(booklist);
    }

    private void populate_authortable(ArrayList<Author> loaded_data) {
        ObservableList<Author> authorList = FXCollections.observableArrayList(loaded_data);
        AuthorTableView.setItems(authorList);
    }

    private void populate_issueBooks(ArrayList<BookTransaction> loaded_transactions){
        ObservableList<BookTransaction> issuedBooks = FXCollections.observableArrayList(loaded_transactions);
        issuedBookListTableView.setItems(issuedBooks);
    }
}