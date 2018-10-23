package net.passwordcracker.cracker;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.passwordcracker.cracker.vector.BruteforceVector;
import net.passwordcracker.cracker.vector.DictionaryAttackVector;
import net.passwordcracker.cracker.vector.Vector;
import net.passwordcracker.cracker.vector.WindwardVector;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller
{
    private static Controller INSTANCE;
    private static final Map<String, Vector> vectors = new HashMap<>();
    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(10);
    @FXML
    private TextField password;
    @FXML
    public Label attempts;
    @FXML
    private ComboBox<String> vector;

    public void initialize()
    {
        INSTANCE = this;
        vectors.put("Brute Force",new BruteforceVector());
        vectors.put("Dictionary Attack",new DictionaryAttackVector());
      //  vectors.put("Windward Attack",new WindwardVector());
        vector.getItems().addAll("Brute Force","Dictionary Attack","Windward Attack");
    }
    public void crack()
    {
        final String vect = vector.getValue();
        final Vector attack = vectors.get(vect);
        if(attack instanceof WindwardVector)
        {
            attack.attack(SERVICE, password.getText().split("[?!@#$%^&*()]")[0],password.getText().split("[?!@#$%^&*()]")[1]);
        }
        else {
            attack.attack(SERVICE,password.getText(),null);
        }

    }

    public static Controller get()
    {
        return INSTANCE;
    }
}
