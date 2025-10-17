package com.example.sqlproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.control.cell.PropertyValueFactory;

public class GuestController {

    @FXML
    private void goBack(ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400)); // نفس حجم شاشة البداية
        stage.centerOnScreen();
        stage.show();
    }




    // Tab 1: Horses by Owner Last Name
    @FXML private TextField ownerLastNameField;
    @FXML private TableView<OwnerHorseRow> tblOwnerHorses;
    @FXML private TableColumn<OwnerHorseRow, String>  colHorseName;
    @FXML private TableColumn<OwnerHorseRow, Integer> colHorseAge;
    @FXML private TableColumn<OwnerHorseRow, String>  colTrainerF;
    @FXML private TableColumn<OwnerHorseRow, String>  colTrainerL;

    @FXML
    private void onBrowseHorsesByOwner(ActionEvent e) {
        String last = ownerLastNameField.getText();
        tblOwnerHorses.setItems(fetchOwnerHorses(last));
    }


    private ObservableList<OwnerHorseRow> fetchOwnerHorses(String lastName) {

        //sql todo

        ObservableList<OwnerHorseRow> data = FXCollections.observableArrayList();

        //conn todo
        return data;
    }





    // Tab 2: Trainers who trained winners
    @FXML private TableView<WinnerTrainerRow> tblWinnerTrainers;
    @FXML private TableColumn<WinnerTrainerRow, String>    colWT_TF;
    @FXML private TableColumn<WinnerTrainerRow, String>    colWT_TL;
    @FXML private TableColumn<WinnerTrainerRow, String>    colWT_H;
    @FXML private TableColumn<WinnerTrainerRow, String>    colWT_R;
    @FXML private TableColumn<WinnerTrainerRow, LocalDate> colWT_D;
    @FXML private TableColumn<WinnerTrainerRow, LocalTime> colWT_T;

    @FXML
    private void onBrowseWinnerTrainers(ActionEvent e) {

        tblWinnerTrainers.setItems(fetchWinnerTrainers());
    }


    private ObservableList<WinnerTrainerRow> fetchWinnerTrainers() {

        //sql todo

        ObservableList<WinnerTrainerRow> data = FXCollections.observableArrayList();

        //conn
        return data;

    }




    // Tab 3: Trainer total winnings
    @FXML private TableView<TrainerWinningsRow> tblTrainerWinnings;
    @FXML private TableColumn<TrainerWinningsRow, String> colTW_F;
    @FXML private TableColumn<TrainerWinningsRow, String> colTW_L;
    @FXML private TableColumn<TrainerWinningsRow, Double> colTW_S;

    @FXML
    private void onBrowseTrainerWinnings(ActionEvent e) {
        tblTrainerWinnings.setItems(fetchTrainerWinnings());
    }


    private ObservableList<TrainerWinningsRow> fetchTrainerWinnings() {
        //sql todo

        ObservableList<TrainerWinningsRow> data = FXCollections.observableArrayList();

        //conn todo
        return data;

    }






    // Tab 4: Tracks stats
    @FXML private TableView<TrackStatsRow> tblTrackStats;
    @FXML private TableColumn<TrackStatsRow, String>  colTS_Name;
    @FXML private TableColumn<TrackStatsRow, Integer> colTS_Races;
    @FXML private TableColumn<TrackStatsRow, Integer> colTS_Horses;

    @FXML
    private void onBrowseTrackStats(ActionEvent e) {
        tblTrackStats.setItems(fetchTrackStats());
    }


    private ObservableList<TrackStatsRow> fetchTrackStats() {
        //sql todo

        ObservableList<TrackStatsRow> data = FXCollections.observableArrayList();

        //conn todo
        return data;
    }










    @FXML
    private void initialize() {


        // Tab1 columns
        colHorseName.setCellValueFactory(new PropertyValueFactory<>("horseName"));
        colHorseAge .setCellValueFactory(new PropertyValueFactory<>("age"));
        colTrainerF .setCellValueFactory(new PropertyValueFactory<>("trainerFirst"));
        colTrainerL .setCellValueFactory(new PropertyValueFactory<>("trainerLast"));


        // Tab2 columns
        colWT_TF.setCellValueFactory(new PropertyValueFactory<>("trainerFirst"));
        colWT_TL.setCellValueFactory(new PropertyValueFactory<>("trainerLast"));
        colWT_H .setCellValueFactory(new PropertyValueFactory<>("horseName"));
        colWT_R .setCellValueFactory(new PropertyValueFactory<>("raceName"));
        colWT_D .setCellValueFactory(new PropertyValueFactory<>("raceDate"));
        colWT_T .setCellValueFactory(new PropertyValueFactory<>("raceTime"));


        // Tab3 columns
        colTW_F.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colTW_L.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colTW_S.setCellValueFactory(new PropertyValueFactory<>("totalWinnings"));


        // Tab4 columns
        colTS_Name .setCellValueFactory(new PropertyValueFactory<>("trackName"));
        colTS_Races.setCellValueFactory(new PropertyValueFactory<>("raceCount"));
        colTS_Horses.setCellValueFactory(new PropertyValueFactory<>("totalHorses"));


        // empty tables initially
        tblOwnerHorses.setItems(FXCollections.observableArrayList());
        tblWinnerTrainers.setItems(FXCollections.observableArrayList());
        tblTrainerWinnings.setItems(FXCollections.observableArrayList());
        tblTrackStats.setItems(FXCollections.observableArrayList());
    }



    public static class OwnerHorseRow {

        private final String horseName;
        private final int age;
        private final String trainerFirst;
        private final String trainerLast;

        public OwnerHorseRow(String horseName, int age, String trainerFirst, String trainerLast){
            this.horseName=horseName;
            this.age=age;
            this.trainerFirst=trainerFirst;
            this.trainerLast=trainerLast;
        }

        public String getHorseName(){
            return horseName;
        }
        public int getAge(){
            return age;
        }
        public String getTrainerFirst(){
            return trainerFirst;
        }
        public String getTrainerLast(){
            return trainerLast;
        }
    }

    public static class WinnerTrainerRow {

        private final String trainerFirst, trainerLast, horseName, raceName;
        private final LocalDate raceDate; private final LocalTime raceTime;

        public WinnerTrainerRow(String trainerFirst,String trainerLast,String horseName,String raceName,LocalDate raceDate,LocalTime raceTime){
            this.trainerFirst=trainerFirst;
            this.trainerLast=trainerLast;
            this.horseName=horseName;
            this.raceName=raceName;
            this.raceDate=raceDate;
            this.raceTime=raceTime;
        }
        public String getTrainerFirst(){
            return trainerFirst;
        }
        public String getTrainerLast(){
            return trainerLast;
        }
        public String getHorseName(){
            return horseName;
        }
        public String getRaceName(){
            return raceName;
        }
        public LocalDate getRaceDate(){
            return raceDate;
        }
        public LocalTime getRaceTime(){
            return raceTime;
        }
    }

    public static class TrainerWinningsRow {
        private final String firstName;
        private final String lastName;
        private final double totalWinnings;

        public TrainerWinningsRow(String firstName, String lastName, double totalWinnings) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.totalWinnings = totalWinnings;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Double getTotalWinnings() {
            return totalWinnings;
        }
    }

    public static class TrackStatsRow {
        private final String trackName;
        private final int raceCount;
        private final int totalHorses;

        public TrackStatsRow(String trackName, int raceCount, int totalHorses) {
            this.trackName = trackName;
            this.raceCount = raceCount;
            this.totalHorses = totalHorses;
        }

        public String getTrackName() {
            return trackName;
        }

        public Integer getRaceCount() {
            return raceCount;
        }

        public Integer getTotalHorses() {
            return totalHorses;
        }
    }
}
