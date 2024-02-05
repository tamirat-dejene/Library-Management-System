package com.lms.library_management_system;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.lms.backend.Library;
import com.lms.backend.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UserController implements Initializable {
    private Library library;

    @FXML private TableColumn<User, String> emailCol;

    @FXML private TableColumn<User, String> fullNameCol;

    @FXML private TableColumn<User, String> idNumberCol;

    @FXML private TableColumn<User, String> typeCol;

    @FXML private TableView<User> userTable;

    @FXML private TextField newEmailTextField;

    @FXML private TextField newIdTextField;

    @FXML private TextField newNameTextField;

    @FXML private TextField newTypeTextField;

    @FXML private TextField totalUserTextField;

    @FXML private AnchorPane userPane;

    @FXML private Label verifyStatusLabel;

    @FXML private TextField verifyUserTextField;

    @FXML CheckBox dbCheckBox;

    @FXML CheckBox viewCheckBox;

    @FXML
    void addButton(ActionEvent event) {
        //userPane.getChildren().add(new ScrollPane());
        String idNumber = newIdTextField.getText();
        String full_name = newNameTextField.getText();
        String email = newEmailTextField.getText();
        String type = newTypeTextField.getText();

        library = new Library();
        library.register_new_user(new User(type, full_name, idNumber, email));
        update_total_user_text_field(1);

        refreshButton(event);
    }

    @FXML
    void refreshButton(ActionEvent event) {
        insertDataIntoTable(loadUserData());
        Integer s = Library.getTotalNumberOfUsers();
        totalUserTextField.setText(s.toString());
        verifyStatusLabel.setText("");
        verifyUserTextField.setText("");
        newEmailTextField.setText("");
        newIdTextField.setText("");
        newNameTextField.setText("");
        newTypeTextField.setText("");
        dbCheckBox.setSelected(false);
        viewCheckBox.setSelected(false);
    }

    @FXML
    void removeUserButton(ActionEvent event) {
        if(viewCheckBox.isSelected() || dbCheckBox.isSelected()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("You cant undo after the action.");
            alert.setHeaderText("Confirm Your Action!");


            if(alert.showAndWait().get()== ButtonType.OK){
                Library lib = new Library();
                ObservableList<User> allUser, selectedUser;
                allUser = userTable.getItems();
                selectedUser = userTable.getSelectionModel().getSelectedItems();
                if (viewCheckBox.isSelected() && !dbCheckBox.isSelected()){
                    try {
                        selectedUser.forEach(allUser::remove);
                        this.update_total_user_text_field(-selectedUser.size());
                    } catch(Exception ex){
                        totalUserTextField.setText("0");
                    }
                } else{
                        try {
                                for(User u : selectedUser){
                                lib.removeUser(u.getId_number());
                            }
                            refreshButton(event);
                        }catch(Exception ex){
                    }
                }
            }
        }
    }

    @FXML
    void verifyButton(ActionEvent event) {
        String id_text = verifyUserTextField.getText();
        library = new Library();
        if(library.isVerified(id_text)) {
			verifyStatusLabel.setText("The user is verified!");
		} else {
			verifyStatusLabel.setText("The user is NOT verified!");
		}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        idNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_number()));
        fullNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFull_name()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail_id()));

        insertDataIntoTable(loadUserData());
        update_total_user_text_field(Library.getTotalNumberOfUsers());
    }

    private void insertDataIntoTable(ArrayList<User> data){
        ObservableList<User> ol = FXCollections.observableArrayList(data);
        userTable.setItems(ol);
    }

    private ArrayList<User> loadUserData(){
        library = new Library();
        ArrayList<User> userList = library.getUserList();
        return userList;
    }

    private void update_total_user_text_field(int x){
        String text = totalUserTextField.getText();
        Integer val = Integer.valueOf(text);
        val += x;
        totalUserTextField.setText(Integer.toString(val));
    }
}
