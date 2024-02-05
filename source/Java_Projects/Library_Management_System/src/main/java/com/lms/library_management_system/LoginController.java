package com.lms.library_management_system;

import java.io.IOException;
import java.util.Optional;

import com.lms.backend.Library;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController extends Application {

    private Scene scene;
    private Parent root;
    private Stage stage;

    private Library library;

    @FXML private BorderPane scenePane;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField usernameTextField;

    @FXML private TextField sginUp_email_tf;
    @FXML private TextField sginUp_fullName_tf;
    @FXML private TextField sginUp_idNum_tf;
    @FXML private PasswordField sginUp_password_tf;
    @FXML private TextField sginUp_userName_tf;

    private TextField resetPassword_IdTextField;
    private PasswordField resetPassword_NewPassPasswordField;

    @FXML
    void aboutUs(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"", ButtonType.OK);
        alert.setTitle("INNOV8");
        alert.setHeaderText(" Innov8");
        alert.setContentText("Developed by Team INNOV8");
        alert.setHeight(100);

        alert.show();
    }

    @FXML
    void exit(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Confirm before you exit");

        try {
            if (alert.showAndWait().get() == ButtonType.OK) {
                stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
            }
        } catch (Exception f) {
            //System.out.println(f.getMessage());
        }
    }
    @FXML
    void resetPassword(ActionEvent event) {
        Dialog<?> dialog = new Dialog<>();
        dialog.setTitle("Reset Password");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().setContent(resetForm());
        try {
        	if(dialog.showAndWait().get() == ButtonType.APPLY){
                String idNum = resetPassword_IdTextField.getText();
                String newPass = resetPassword_NewPassPasswordField.getText();
                if(newPass.length() < 4) {
    				showAlertMessage("INVALID NEW PASSWORD");
    			} else {
                    Library lib = new Library();
                    if (lib.resetPassword(idNum, newPass)) {
                        showAlertSuccessMessage("Your password reset successfully.");
                    } else {
                        showAlertMessage("INVALID ID NUMBER");
                    }
                }
            }	
        } catch(Exception e) {
        	// Do nothing
        }
        
    }

    public static void showAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("ERROR");
        alert.showAndWait();
    }

    private void showAlertSuccessMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("SUCCESS");
        alert.showAndWait();
    }

    private Node resetForm(){
        resetPassword_IdTextField = new TextField("");
        resetPassword_NewPassPasswordField = new PasswordField();
        GridPane pane = new GridPane(2, 2);
        pane.add(new Label("Id number : "), 0, 0);
        pane.add(new Label("New password : "), 0, 1);
        pane.add(resetPassword_IdTextField, 1, 0);
        pane.add(resetPassword_NewPassPasswordField, 1, 1);
        return pane;
    }

    @FXML
    void signup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("signupPage.fxml"));
            Node node = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Sign up");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
            dialog.getDialogPane().setContent(node);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.APPLY) {
                sginUp_email_tf = (TextField) fxmlLoader.getNamespace().get("sginUp_email_tf");
                sginUp_fullName_tf = (TextField) fxmlLoader.getNamespace().get("sginUp_fullName_tf");
                sginUp_idNum_tf = (TextField) fxmlLoader.getNamespace().get("sginUp_idNum_tf");
                sginUp_userName_tf = (TextField) fxmlLoader.getNamespace().get("sginUp_userName_tf");
                sginUp_password_tf = (PasswordField) fxmlLoader.getNamespace().get("sginUp_password_tf");

                String email = sginUp_email_tf.getText();
                String fn = sginUp_fullName_tf.getText();
                String id = sginUp_idNum_tf.getText();
                String userName = sginUp_userName_tf.getText();
                String password = sginUp_password_tf.getText();

                System.out.println(email);
                System.out.println(userName);
                System.out.println(password);

                library = new Library();
                if(email.length() <= 3 || fn.length() <= 3 || id.length() < 1 || userName.length() <= 3 || password.length() <= 4 ) {
					showAlertMessage("INVALID INPUTS");
				} else{
                    library.admin_signup(id, fn, email, userName, password);
                    showAlertSuccessMessage("Signed Up Successfully as an Admin. You can now Login");
                }
            }
        } catch (IOException ex) {
            showAlertMessage("Operation unsuccessful");
            //ex.printStackTrace();
        }
    }

    @FXML
    void login(ActionEvent event) {
        library = new Library();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!library.admin_login(username, password) || username.isBlank() || password.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("INCORRECT USERNAME OR PASSWORD");
            alert.setContentText("Please provide correct username/password");
            alert.show();
        } else {
            try {
                FXMLLoader mainPageLoader = new FXMLLoader(MainController.class.getResource("mainpage.fxml"));

                root = mainPageLoader.load();

                stage = (Stage) scenePane.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("adminlogin.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("LibraryIcon.png"));

        root = fxmlLoader.load();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Library Management System");

        stage.getIcons().add(icon);
        stage.show();
    }
}
