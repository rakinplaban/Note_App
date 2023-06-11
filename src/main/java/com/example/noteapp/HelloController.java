package com.example.noteapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Button registerAcc;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confimrpassword;


    @FXML
    protected void onRegisterButton() {
        if(username.getText().isBlank()==false && email.getText().isBlank()==false && password.getText().isBlank()==false){

        }
    }
}