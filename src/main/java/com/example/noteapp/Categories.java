package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
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

public class Categories {
    private int id;
    private String category_name;
    private Date created_date;

    public int getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    @FXML
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Categories(){}

    public Categories(int id, String category_name, Date created_date) {
        this.id = id;
        this.category_name = category_name;
        this.created_date = created_date;
    }

//    public Categories(String category_name){
//        this.category_name = category_name;
//    }
    @FXML
    private TextField categoryid;

    @FXML
    private Button createbtn;

    @FXML
    private Menu view;

    @FXML
    private MenuBar menubar;

    @FXML
    public void createCategory(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            String sql = "insert into category(category_name) values (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            String cat = categoryid.getText();
            statement.setString(1, cat);
            statement.executeUpdate();

            showAlert("Created Successful", "201 created!");
        }catch(Exception e){
            System.out.println("Connection failed.");
        }
    }

    @FXML
    public void showCategories(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("categorylist.fxml"));
            AnchorPane categoryLayout = loader.load();
            Scene loginScene = new Scene(categoryLayout);

            // Access the controller of the login.fxml file and pass the primaryStage reference
            ViewCategories categoriesController = loader.getController();
            categoriesController.setPrimaryStage(primaryStage);

            primaryStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
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
