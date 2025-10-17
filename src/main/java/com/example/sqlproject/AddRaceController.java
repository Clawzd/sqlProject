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

public class AddRaceController {

    @FXML
    private TextField raceIdField;

    @FXML
    private TextField raceNameField;

    @FXML
    private TextField trackNameField;

    @FXML
    private DatePicker raceDatePicker;

    @FXML
    private TextField raceTimeField;


    @FXML
    private void onAddRace(ActionEvent e) {
        String raceId = raceIdField.getText();
        String raceName = raceNameField.getText();
        String trackName = trackNameField.getText();
        LocalDate raceDate = raceDatePicker.getValue();
        String raceTime = raceTimeField.getText();


        //Sql function here todo


    }




    public void goBack(ActionEvent e) throws Exception {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(adminRoot);
    }
}


