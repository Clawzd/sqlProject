package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class AddRaceController {

    @FXML private TextField raceIdField;
    @FXML private TextField raceNameField;
    @FXML private TextField trackNameField;
    @FXML private DatePicker raceDatePicker;
    @FXML private TextField raceTimeField;

    @FXML
    private void onAddRace(ActionEvent e) {
        String raceId = raceIdField.getText().trim();
        String raceName = raceNameField.getText().trim();
        String trackName = trackNameField.getText().trim();
        LocalDate raceDate = raceDatePicker.getValue();
        String raceTime = raceTimeField.getText().trim();

        if (raceId.isEmpty() || raceName.isEmpty() || trackName.isEmpty() ||
                raceDate == null || raceTime.isEmpty()) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call AddRaceWithResults(?, ?, ?, ?, ?)}")) {

            stmt.setString(1, raceId);
            stmt.setString(2, raceName);
            stmt.setString(3, trackName);
            stmt.setDate(4, java.sql.Date.valueOf(raceDate));
            stmt.setString(5, raceTime);
            stmt.execute();
            showAlert("Success", "Race added successfully!");

            // Clear all fields
            raceIdField.clear();
            raceNameField.clear();
            trackNameField.clear();
            raceDatePicker.setValue(null);
            raceTimeField.clear();

        } catch (SQLException ex) {
            showAlert("Error", "Failed to add race: " + ex.getMessage());
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


