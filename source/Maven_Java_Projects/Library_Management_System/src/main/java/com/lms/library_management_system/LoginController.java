package com.lms.library_management_system;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import com.lms.backend.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private Library library;
    private Scene scene;
    private Parent root;
    private Stage stage;
    
    @FXML
    private BorderPane scenePane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    
    @FXML
    private void login() {
        library  = new Library();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!library.admin_login(username, password)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("INCORRECT USERNAME OR PASSWORD");
            alert.setContentText("Please provide correct username/password");
            alert.show();
        } else{
            try {
                root = loadFXML("mainpage");
                stage = (Stage) scenePane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @FXML
    public void exit(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Confirm before you exit");
      
        try {
            if(alert.showAndWait().get() == ButtonType.OK){
                stage = (Stage) scenePane.getScene().getWindow();
                stage.close();  
            }
        } catch(Exception f){
            System.out.println(f.getMessage());
        }
    }

    @FXML
    public void signup() {
        try {
            root = loadFXML("signupPage");
            stage = (Stage) scenePane.getScene().getWindow();
            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void aboutUs() {
        
    }

    @FXML
    private void resetPassword() {
        
    } 
    
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
