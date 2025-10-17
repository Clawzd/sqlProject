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

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteOwnerController {

    @FXML
    private TextField ownerIdField;


    @FXML
    private void onDeleteOwner(ActionEvent e) {
        String ownerId = ownerIdField.getText();

//        sql fuction here todo
    }


    @FXML
    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }

}
