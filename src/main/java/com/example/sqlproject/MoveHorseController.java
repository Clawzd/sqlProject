package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MoveHorseController {

    @FXML private TextField horseIdField;
    @FXML private TextField newStableIdField;

    @FXML
    private void onMove(ActionEvent e) {
        String horseId = horseIdField.getText().trim();
        String newStableId = newStableIdField.getText().trim();

        //Sql function here todo

    }
    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }
}
