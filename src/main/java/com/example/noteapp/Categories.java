package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;

public class Categories {
    @FXML
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private TextField categoryid;

    @FXML
    private Button createbtn;

    @FXML
    public void createCategory(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            String sql = "insert into category(category_name) values (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            String cat = categoryid.getText();
            statement.setString(1, cat);
            statement.executeUpdate();

                showAlert("Registration Successful", "User registered successfully!");
        }catch(Exception e){
            System.out.println("Connection failed.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
