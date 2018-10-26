package net.passwordcracker.cracker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.passwordcracker.cracker.vector.BruteforceVector;
import net.passwordcracker.cracker.vector.DictionaryAttackVector;
import net.passwordcracker.cracker.vector.Vector;
import net.passwordcracker.cracker.vector.WindwardVector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
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
        vectors.put("Windward Attack",new WindwardVector(new File(this.getClass().getClassLoader().getResource("englishdictionary.txt").getFile())));
        vector.getItems().addAll("Brute Force","Dictionary Attack","Windward Attack");
    }
    public void crack()
    {
        final String vect = vector.getValue();
        final Vector attack = vectors.get(vect);
        attack.attack(SERVICE,password.getText(),null);
    }
    public static void alert(String title, String message) {
        try
        {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.setHeaderText(title);
                alert.showAndWait();
                latch.countDown();
            });
            latch.await();
        }
        catch (Exception e) {}

    }

    public static Controller get()
    {
        return INSTANCE;
    }
}
