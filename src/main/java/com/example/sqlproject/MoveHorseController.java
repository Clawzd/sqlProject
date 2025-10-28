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

public class MoveHorseController {

    @FXML private TextField horseIdField;
    @FXML private TextField newStableIdField;

    @FXML
    private void onMove(ActionEvent e) {
        String horseId = horseIdField.getText().trim();
        String newStableId = newStableIdField.getText().trim();

        if (horseId.isEmpty() || newStableId.isEmpty()) {
            showAlert("Error", "Please enter both Horse ID and New Stable ID");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call MoveHorseStable(?, ?)}")) {

            stmt.setString(1, horseId);
            stmt.setString(2, newStableId);
            stmt.execute();
            showAlert("Success", "Horse moved successfully!");

            // Clear fields
            horseIdField.clear();
            newStableIdField.clear();

        } catch (SQLException ex) {
            showAlert("Error", "Failed to move horse: " + ex.getMessage());
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
