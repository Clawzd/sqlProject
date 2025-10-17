package com.example.sqlproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ApproveTrainerController {

    @FXML
    private TextField trainerIdField;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextField stableIdField;


    @FXML
    private void onApproveTrainer(ActionEvent e) {
        String trainerId = trainerIdField.getText();
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        String stableId = stableIdField.getText();



        //Sql function here todo


    }


    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }
}
