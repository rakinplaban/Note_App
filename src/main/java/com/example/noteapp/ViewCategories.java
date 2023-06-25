package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class ViewCategories {
    private Stage primaryStage; // Declare the Stage reference

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private TableView<Categories> categorytab;

    @FXML
    private TableColumn<Categories, String> category_name;

    @FXML
    private TableColumn<Categories, Integer> id;

    @FXML
    private TableColumn<Categories, Date> created_date;

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        category_name.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        created_date.setCellValueFactory(new PropertyValueFactory<>("created_date"));
        viewCategories();
    }

    @FXML
    public void viewCategories(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);

            String sql = "SELECT * FROM category";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<Categories> categories = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("category_name");
                Date createdDate = resultSet.getDate("created_date");
                Categories category = new Categories(categoryId, categoryName, createdDate);
                categories.add(category);
            }

            categorytab.setItems(categories);

            resultSet.close();
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    @FXML
    public void takeNoteOption(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("takenote.fxml"));
            AnchorPane categoryLayout = loader.load();
            Scene loginScene = new Scene(categoryLayout);

            // Access the controller of the login.fxml file and pass the primaryStage reference
            Takenote notepage = loader.getController();
            notepage.setPrimaryStage(primaryStage);

            primaryStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
