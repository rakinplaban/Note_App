package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;

public class HelloController {
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private Button registerAcc;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink Login;

    @FXML
    protected void onRegisterButton() {
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        if(username.getText().isBlank()==false && email.getText().isBlank()==false && password.getText().isBlank()==false){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url,user,pass);
                String sql = "insert into user(username, email, password) values (?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                String u = username.getText();
                String p = password.getText();
                String e = email.getText();
                statement.setString(1, u);
                statement.setString(2, e);
                statement.setString(3, p);
                statement.executeUpdate();

                showAlert("Registration Successful", "User registered successfully!");
            }catch(Exception e){
                System.out.println("Connection failed.");
            }
        }
    }

    @FXML
    protected void LoginOption() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            BorderPane loginLayout = loader.load();
            Scene loginScene = new Scene(loginLayout);

            // Access the controller of the login.fxml file and pass the primaryStage reference
            Login loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            primaryStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLoginHyperlink() {
        LoginOption(); // Call LoginOption() to switch to the login page
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}