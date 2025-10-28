package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteOwnerController {

    @FXML
    private TextField ownerIdField;


    @FXML
    private void onDeleteOwner(ActionEvent e) {
        String ownerId = ownerIdField.getText();
        //   validation
        if (ownerId.isEmpty()) {
            showAlert("Error", "Please enter Owner ID");
            return;
        }

        //  database call
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DeleteOwner(?)}")) {

            stmt.setString(1, ownerId);
            stmt.execute();
            showAlert("Success", "Owner deleted successfully!");
            ownerIdField.clear(); //  Clear after success

        } catch (SQLException ex) {
            showAlert("Error", "Failed to delete owner: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }

}
