package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    public void onLogoutClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/sqlproject/hello-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onAddRace(ActionEvent e) throws Exception {
        switchScene("add_race.fxml", e);
    }

    @FXML
    private void onDeleteOwner(ActionEvent e) throws Exception {
        switchScene("delete_owner.fxml", e);
    }

    @FXML
    private void onMoveHorse(ActionEvent e) throws Exception {
        switchScene("move_horse.fxml", e);
    }

    @FXML
    private void onApproveTrainer(ActionEvent e) throws Exception {
        switchScene("approve_trainer.fxml", e);
    }

    private void switchScene(String fxml, ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
