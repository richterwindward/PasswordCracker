package net.passwordcracker.cracker.vector;

import javafx.application.Platform;
import net.passwordcracker.cracker.Controller;
import net.passwordcracker.cracker.LineSeperatedList;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DictionaryAttackVector implements Vector
{
    private static LineSeperatedList list = new LineSeperatedList(new File(DictionaryAttackVector.class.getClassLoader().getResource("password.lst").getFile()));
    @Override
    public int attack(ExecutorService pool, String password, String mask)
    {
        list.reset();
        String pass;
        final AtomicBoolean found = new AtomicBoolean();
        final AtomicInteger comparisons = new AtomicInteger(0);
        while (!found.get() && (pass = list.getNextPassword()) != null)
        {
            Platform.runLater(() -> {
                Controller.get().attempts.setText("About " + comparisons.toString() + " Comparisons");
            });

            final String javaDumbBruh = pass;

            pool.submit(() -> {
                comparisons.addAndGet(1);
                if(javaDumbBruh.equals(password)) {
                    found.set(true);
                    System.out.println("Position/Comparison " + comparisons.get() + 1 + " Found: " + javaDumbBruh);
                }
            });
        }
        return comparisons.get();

    }
}
