package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Login {

    @FXML
    private Button registerAcc;

    @FXML
    private TextField username;


    @FXML
    private PasswordField password;



    @FXML
    protected void onLoginButton() {
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        if(username.getText().isBlank()==false && password.getText().isBlank()==false){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url,user,pass);
                String sql = "Select * from user where username=? and password=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                String u = username.getText();
                String p = password.getText();
                statement.setString(1, u);
                statement.setString(2, p);
                statement.executeUpdate();

                showAlert("Successfully login", "Login as "+u);
            }catch(Exception e){
                System.out.println("Connection failed.");
            }
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
