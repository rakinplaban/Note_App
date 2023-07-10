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
    private TableView<Takenote> categorytab;

    @FXML
    private TableColumn<Takenote, String> title;

    @FXML
    private TableColumn<Takenote, Integer> id;

    @FXML
    private TableColumn<Takenote, String> content;

    public void initialize() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        viewCategories();
    }

    @FXML
    public void viewCategories(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);

            String sql = "SELECT title,content FROM note where user_id = 1";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<Takenote> categories = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Takenote notes = new Takenote(title,content);
                categories.add(notes);
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
