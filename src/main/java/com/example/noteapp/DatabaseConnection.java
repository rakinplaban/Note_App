package com.example.noteapp;
import java.sql.*;
public class DatabaseConnection {
    public void connectionMaker(){
        String url = "jdbc:mysql://localhost:3306/noteapp";
        String user = "root";
        String pass = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
        }catch(Exception e){
            System.out.println("Connection failed.");
        }
    }
}
