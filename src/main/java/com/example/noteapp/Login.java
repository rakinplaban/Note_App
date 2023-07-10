package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    @FXML
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url,user,pass);
                String sql = "Select * from user where username=? and password=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                String u = username.getText();
                String p = password.getText();
                statement.setString(1, u);
                statement.setString(2, p);
                ResultSet resultSet=statement.executeQuery();

                showAlert("Successfully login", "Login as "+u);
//                if (resultSet.next()) {
//                    int userId = resultSet.getInt("id");
//                    String userName = resultSet.getString("username");
//                    String password = resultSet.getString("password");
//                    User auth_user = new User(userId, userName, password);
//                }
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User auth_user = new User(userId, userName, password);

                    // Additional code to pass the authenticated user to the next scene or controller
                    // For example, you can pass the authenticated user to the CategoriesController
                    FXMLLoader noteFormLoader = new FXMLLoader(getClass().getResource("takenote.fxml"));
                    AnchorPane noteFormLayout = noteFormLoader.load();
                    Scene loginScene = new Scene(noteFormLayout);

                    Takenote noteTakeController = noteFormLoader.getController();
                    noteTakeController.setPrimaryStage(primaryStage);
                    noteTakeController.setAuthenticatedUser(auth_user);

                    primaryStage.setScene(loginScene);
                } else {
                    showAlert("Error", "Invalid username or password.");
                }

            }catch(Exception e){
                System.out.println("Connection failed.");
                showAlert("Error 501", "Internal Server Error!");
            }
            /* Load category page. */
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("categories.fxml"));
//                AnchorPane categoryLayout = loader.load();
//                Scene loginScene = new Scene(categoryLayout);
//
//                // Access the controller of the login.fxml file and pass the primaryStage reference
//                Categories categoriesController = loader.getController();
//                categoriesController.setPrimaryStage(primaryStage);
//
//                primaryStage.setScene(loginScene);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

//    @FXML
//    protected void createCategory() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("categories.fxml"));
//            AnchorPane categoryLayout = loader.load();
//            Scene loginScene = new Scene(categoryLayout);
//
//            // Access the controller of the login.fxml file and pass the primaryStage reference
//            Login loginController = loader.getController();
//            loginController.setPrimaryStage(primaryStage);
//
//            primaryStage.setScene(loginScene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
