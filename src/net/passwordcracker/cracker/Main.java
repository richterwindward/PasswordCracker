package net.passwordcracker.cracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.passwordcracker.cracker.vector.WindwardVector;

import java.io.File;
import java.net.URL;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Password Cracker");
        primaryStage.setScene(new Scene(root, 300, 275));
        URL nounList = Main.class.getClassLoader().getResource("englishdictionary.txt");
        System.out.println(nounList);
        File nounfile = new File(nounList.getFile());
        WindwardVector wv = new WindwardVector(nounfile);
        wv.attack("Hippopotamus&2020");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
