package net.passwordcracker.cracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.passwordcracker.cracker.vector.WindwardVector;

import java.io.File;
import java.net.URL;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(Main.class.getClassLoader().getResource("GUI.fxml"));
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource("GUI.fxml"));
        primaryStage.setTitle("Password Cracker");
        primaryStage.setScene(new Scene(root, ((AnchorPane) root).getPrefWidth(), ((AnchorPane) root).getPrefHeight()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
