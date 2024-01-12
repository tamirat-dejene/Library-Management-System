module com.lms.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    
    opens com.lms.library_management_system to javafx.fxml;
    exports com.lms.library_management_system;
}
