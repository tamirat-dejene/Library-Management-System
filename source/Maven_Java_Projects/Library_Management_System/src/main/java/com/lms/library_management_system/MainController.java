/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lms.library_management_system;

import com.lms.backend.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author PC
 */
public class MainController implements Initializable {
    private Scene scene;
    private Node root;
    private Stage stage;
    
    @FXML TableView<User> userTable;
    @FXML private TableColumn<User, String> colIdNumber;
    @FXML private TableColumn<User, String> colFullName;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colType;
    
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private BorderPane mainBorderPane;
    
    @FXML
    private MenuItem registerUserMenu;
    @FXML
    private MenuItem verifyUserMenu;
    @FXML
    private MenuItem getUserByIDMenu;
    @FXML
    private MenuItem totalUsersMenu;
    @FXML
    private MenuItem listBooksMenu;
    @FXML
    private MenuItem addBookMenu;
    @FXML
    private MenuItem addAuthorsMenu;
    @FXML
    private MenuItem lendBooksMenu;
    @FXML
    private MenuItem booksStatusMenu;
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    public void registerUser() throws IOException{
       
    }
    @FXML
    public void verifyUser(){
        
    }
    @FXML
    public void getUserByID(){
        
    }
    @FXML
    public void addBooks(){
        
    }
    @FXML
    public void addAuthors(){
        
    }
    @FXML
    public void lendBooks(){
        
    }
    @FXML
    public void checkBookStatus(){
        
    }
    @FXML
    public void listBooks(){
        
    }
    
    @FXML
    public void listUsers(ActionEvent e) throws IOException{
       root = loadFXML("userviewtable");       
       mainBorderPane.setCenter(root);
       root.setMouseTransparent(true);
       
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    private void registerUsers(ActionEvent event) {
    }

    @FXML
    private void totalUsers(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colIdNumber.setCellValueFactory(new PropertyValueFactory<User, String> ("id_number"));
        colFullName.setCellValueFactory(new PropertyValueFactory<User, String> ("full_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<User, String> ("full_name"));
        colType.setCellValueFactory(new PropertyValueFactory<User, String> ("email_id"));
    }
}
