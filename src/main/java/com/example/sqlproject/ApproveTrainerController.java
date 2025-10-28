package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class ApproveTrainerController {

    @FXML private TextField trainerIdField;
    @FXML private TextField fnameField;
    @FXML private TextField lnameField;
    @FXML private TextField stableIdField;

    @FXML
    private void onApproveTrainer(ActionEvent e) {
        String trainerId = trainerIdField.getText().trim();
        String fname = fnameField.getText().trim();
        String lname = lnameField.getText().trim();
        String stableId = stableIdField.getText().trim();

        if (trainerId.isEmpty() || fname.isEmpty() || lname.isEmpty() || stableId.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call ApproveTrainer(?, ?, ?, ?)}")) {

            stmt.setString(1, trainerId);
            stmt.setString(2, lname);
            stmt.setString(3, fname);
            stmt.setString(4, stableId);
            stmt.execute();
            showAlert("Success", "Trainer approved successfully!");

            // Clear all fields
            trainerIdField.clear();
            fnameField.clear();
            lnameField.clear();
            stableIdField.clear();

        } catch (SQLException ex) {
            showAlert("Error", "Failed to approve trainer: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }
}
