package com.example.noteapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 620, 470);
//        stage.setTitle("Note App");
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        BorderPane root = fxmlLoader.load();

        // Access the controller of the hello-view.fxml file and pass the primaryStage reference
        HelloController helloController = fxmlLoader.getController();
        helloController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 620, 470);
        primaryStage.setTitle("Note App");
        primaryStage.setScene(scene);
        primaryStage.show();

//        helloController.LoginOption(); // Call LoginOption() after setting the primaryStage reference
    }

    public static void main(String[] args) {
        launch();
    }
}