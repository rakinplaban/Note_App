package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
public class Takenote {
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private ChoiceBox<?> category;

    @FXML
    private TextArea note;

    @FXML
    private Button submit;

    @FXML
    private TextField title;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    public void createNote(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            String sql = "insert into note(title,content,user_id,category_id) values (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            String title_text = title.getText();
            statement.setString(1, title_text);
            String note_text = note.getText();
            statement.setString(2,note_text);
            statement.setInt(3,userId);
            statement.executeUpdate();

            showAlert("Created Successful", "201 created!");
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
