package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminController {


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


    @FXML
    private void onLogout(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.centerOnScreen();
        stage.show();
    }


    private void switchScene(String fxml, ActionEvent e) throws Exception {
        Parent newRoot = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(newRoot);
    }
}
