package com.example.noteapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
//import java.net.URL;
//import java.util.ResourceBundle;

import java.sql.*;

public class Takenote {
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private User authenticatedUser;

    private String title_ ;
    private String content_;

    public String getTitle() {
        return title_;
    }

    public void setTitle(String title_) {
        this.title_ = title_;
    }

    public String getContent() {
        return content_;
    }

    public void setContent(String content_) {
        this.content_ = content_;
    }

    public void setAuthenticatedUser(User user) {
        authenticatedUser = user;
    }
    @FXML
    private ComboBox<String> category;

    @FXML
    private TextArea note;

    @FXML
    private Button submit;

    @FXML
    private TextField title;

    @FXML
    private AnchorPane rootAnchorPane;


    @FXML
    public void rootAnchorPaneInitialized() {
        try {
            String url = "jdbc:mysql://localhost:3306/noteapp";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT category_name FROM category";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            ObservableList<String> data = FXCollections.observableArrayList();
            while (rs.next()) {
                data.add(rs.getString("category_name"));
            }
            category.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load categories.");
        }
    }

    @FXML
    private void initialize() {
        rootAnchorPaneInitialized();
    }

    public Takenote(){}

    public Takenote(String title_, String content_) {
        this.title_ = title_;
        this.content_ = content_;
    }


    @FXML
    public void createNote() {
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            String categoryQuery = "SELECT id FROM category WHERE category_name = ?";
            PreparedStatement categoryStatement = connection.prepareStatement(categoryQuery);
            categoryStatement.setString(1, category.getValue());
            ResultSet categoryResult = categoryStatement.executeQuery();

            if (categoryResult.next()) {
                int categoryId = categoryResult.getInt("id");
                String noteQuery = "INSERT INTO note (title, content, user_id, category_id) VALUES (?, ?, ?, ?)";
                PreparedStatement noteStatement = connection.prepareStatement(noteQuery);
                noteStatement.setString(1, title.getText());
                noteStatement.setString(2, note.getText());
                int userId = authenticatedUser.getId();
                noteStatement.setInt(3, userId);
                noteStatement.setInt(4, categoryId);
                noteStatement.executeUpdate();

                showAlert("Created Successful", "Note created successfully!");

                FXMLLoader notelistLoader = new FXMLLoader(getClass().getResource("categorylist.fxml"));
                AnchorPane notelistLayout = notelistLoader.load();
                Scene listScene = new Scene(notelistLayout);

                ViewCategories noteviewController = notelistLoader.getController();
                noteviewController.setPrimaryStage(primaryStage);
//                noteviewController.setAuthenticatedUser(auth_user);
                primaryStage.setScene(listScene);

            } else {
                showAlert("Error", "Failed to retrieve category ID.");
            }
        } catch (Exception e) {
            System.out.println("Connection failed.");
            showAlert("Error", "500 Internal Server Error");
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
