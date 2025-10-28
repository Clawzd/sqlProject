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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;

public class GuestController {

    @FXML
    private void goBack(ActionEvent e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
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
        if (last.isEmpty()) {
            showAlert("Error", "Please enter owner's last name");
            return;
        }
        tblOwnerHorses.setItems(fetchOwnerHorses(last));
    }

    private ObservableList<OwnerHorseRow> fetchOwnerHorses(String lastName) {
        ObservableList<OwnerHorseRow> data = FXCollections.observableArrayList();
        String sql = "SELECT h.horseName, h.age, t.fname, t.lname " +
                "FROM Horse h " +
                "JOIN Owns o ON h.horseId = o.horseId " +
                "JOIN Owner ow ON o.ownerId = ow.ownerId " +
                "JOIN Trainer t ON h.stableId = t.stableId " +
                "WHERE ow.lname = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new OwnerHorseRow(
                        rs.getString("horseName"),
                        rs.getInt("age"),
                        rs.getString("fname"),
                        rs.getString("lname")
                ));
            }
        } catch (SQLException ex) {
            showAlert("Error", "Failed to fetch horses: " + ex.getMessage());
        }
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
        ObservableList<WinnerTrainerRow> data = FXCollections.observableArrayList();
        String sql = "SELECT t.fname, t.lname, h.horseName, r.raceName, r.raceDate, r.raceTime " +
                "FROM Trainer t " +
                "JOIN Horse h ON t.stableId = h.stableId " +
                "JOIN RaceResults rr ON h.horseId = rr.horseId " +
                "JOIN Race r ON rr.raceId = r.raceId " +
                "WHERE rr.results = 'first'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new WinnerTrainerRow(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("horseName"),
                        rs.getString("raceName"),
                        rs.getDate("raceDate").toLocalDate(),
                        rs.getTime("raceTime").toLocalTime()
                ));
            }
        } catch (SQLException ex) {
            showAlert("Error", "Failed to fetch winning trainers: " + ex.getMessage());
        }
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
        ObservableList<TrainerWinningsRow> data = FXCollections.observableArrayList();
        String sql = "SELECT t.fname, t.lname, SUM(rr.prize) as total_winnings " +
                "FROM Trainer t " +
                "JOIN Horse h ON t.stableId = h.stableId " +
                "JOIN RaceResults rr ON h.horseId = rr.horseId " +
                "GROUP BY t.trainerId, t.fname, t.lname " +
                "ORDER BY total_winnings DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new TrainerWinningsRow(
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getDouble("total_winnings")
                ));
            }
        } catch (SQLException ex) {
            showAlert("Error", "Failed to fetch trainer winnings: " + ex.getMessage());
        }
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
        ObservableList<TrackStatsRow> data = FXCollections.observableArrayList();
        String sql = "SELECT r.trackName, " +
                "COUNT(DISTINCT r.raceId) as race_count, " +
                "COUNT(DISTINCT rr.horseId) as horse_count " +
                "FROM Race r " +
                "JOIN RaceResults rr ON r.raceId = rr.raceId " +
                "GROUP BY r.trackName";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new TrackStatsRow(
                        rs.getString("trackName"),
                        rs.getInt("race_count"),
                        rs.getInt("horse_count")
                ));
            }
        } catch (SQLException ex) {
            showAlert("Error", "Failed to fetch track statistics: " + ex.getMessage());
        }
        return data;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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

    // Inner classes remain the same (they're perfect!)
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

        public String getHorseName(){ return horseName; }
        public int getAge(){ return age; }
        public String getTrainerFirst(){ return trainerFirst; }
        public String getTrainerLast(){ return trainerLast; }
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
        public String getTrainerFirst(){ return trainerFirst; }
        public String getTrainerLast(){ return trainerLast; }
        public String getHorseName(){ return horseName; }
        public String getRaceName(){ return raceName; }
        public LocalDate getRaceDate(){ return raceDate; }
        public LocalTime getRaceTime(){ return raceTime; }
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

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public Double getTotalWinnings() { return totalWinnings; }
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

        public String getTrackName() { return trackName; }
        public Integer getRaceCount() { return raceCount; }
        public Integer getTotalHorses() { return totalHorses; }
    }
}
