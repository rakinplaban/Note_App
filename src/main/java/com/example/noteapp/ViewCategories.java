package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Menu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ViewCategories {

    @FXML
    private Menu createnote;

    @FXML
    private Menu viewnote;

    @FXML
    private Menu takenote;

    @FXML
    private MenuBar optionmenu;

    @FXML
    private TableView<Categories> categorytab;

    @FXML
    private TableColumn<Categories, String> categoryname;

    @FXML
    private TableColumn<Categories, Integer> catid;

    @FXML
    private TableColumn<Categories, Date> created_date;

    public void initialize() {
        catid.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryname.setCellValueFactory(new PropertyValueFactory<>("category_name"));
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
            String sql = "Select * from category";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<Categories> categories = FXCollections.observableArrayList();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category_name = resultSet.getString("category_name");
                Date created_date = resultSet.getDate("created_date");
                Categories category = new Categories();
                categories.add(category);
            }

            categorytab.setItems(categories);

            resultSet.close();
            statement.close();
            connection.close();
//            showAlert("Successfully login", "Login as "+u);
        }catch(Exception e){
            System.out.println("Connection failed.");
        }
    }

}
