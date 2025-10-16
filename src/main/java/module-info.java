module com.example.sqlproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sqlproject to javafx.fxml;
    exports com.example.sqlproject;
}