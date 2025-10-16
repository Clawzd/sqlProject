package com.example.sqlproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    protected void loginFu(ActionEvent event) throws IOException {
        String user = username.getText();
        String pass = password.getText();

        if (user.equals("admin") && pass.equals("1234")) {

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/sqlproject/admin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Invalid credentials");
        }
    }
}
