package net.passwordcracker.cracker;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import net.passwordcracker.cracker.vector.BruteforceVector;
import net.passwordcracker.cracker.vector.DictionaryAttackVector;
import net.passwordcracker.cracker.vector.Vector;
import net.passwordcracker.cracker.vector.WindwardVector;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller
{
    private static final Map<String, Vector> vectors = new HashMap<>();
    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(10);
    @FXML
    private TextField password;
    @FXML
    private Label attempts;
    @FXML
    private ComboBox<String> vector;

    public void initialize()
    {
        vectors.put("Brute Force",new BruteforceVector());
        vectors.put("Dictionary Attack",new DictionaryAttackVector());
        vectors.put("Windward Attack",new WindwardVector());
        vector.getItems().addAll("Brute Force","Dictionary Attack","Windward Attack");
    }
    public void crack()
    {
        final String vect = vector.getValue();

    }



}
