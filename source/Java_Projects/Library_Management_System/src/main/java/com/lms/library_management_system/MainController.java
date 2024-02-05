package com.lms.library_management_system;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

/**
 * @author Team INNOV8
 */
public class MainController {
    @FXML private AnchorPane centerPane;
    @FXML private AnchorPane homeAnchorPane;
    //@FXML private BorderPane mainBorderPane;  // Main Layout

    @FXML
    public void listBooks() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BookController.class.getResource("BookDashboard.fxml"));
            SplitPane newContent = fxmlLoader.load();

            centerPane.getChildren().setAll(newContent);

        } catch (IOException ex) {
           // System.out.println("IOException: " + ex.getMessage());
        }
    }

    @SuppressWarnings("exports")
	@FXML
    public void listUsers(ActionEvent e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("UserDashboard.fxml"));
            AnchorPane newContent = fxmlLoader.load();
            centerPane.getChildren().setAll(newContent);

        } catch (IOException ex) {
           // System.out.println("IOException: " + ex.getMessage());
        }
    }

    @FXML
    void home(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePane.fxml"));
            AnchorPane newContent = fxmlLoader.load();
            centerPane.getChildren().setAll(newContent);

        } catch (IOException ex) {
           // System.out.println("IOException: " + ex.getMessage());
        }
    }
}
