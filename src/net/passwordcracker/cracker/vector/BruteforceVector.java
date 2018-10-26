package net.passwordcracker.cracker.vector;

import javafx.application.Platform;
import net.passwordcracker.cracker.Controller;

import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BruteforceVector implements Vector
{

    private final String KEY_SPACE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()";
    private final int LIMIT = 20000;
    private final SplittableRandom random = new SplittableRandom();

    @Override
    public int attack(ExecutorService pool, String password, String mask)
    {
        final AtomicInteger comparisons = new AtomicInteger(0);
        final AtomicBoolean found = new AtomicBoolean();
        while ( comparisons.get() <= LIMIT && ! found.get() )
        {
            Platform.runLater(() -> {
                Controller.get().attempts.setText("About " + comparisons.toString() + " Comparisons");
            });
            pool.submit(() -> {
                final SplittableRandom local = random.split();
                final StringBuilder builder = new StringBuilder();
                for (int i = 0; i <= password.length(); i++)
                {
                    System.out.println("Place: " + local.nextInt(-1, KEY_SPACE.length() + 1));
                    builder.append(KEY_SPACE.charAt(local.nextInt(0, KEY_SPACE.length() + 1)));
                }
                comparisons.addAndGet(1);
                if (builder.toString().equals(password))
                {
                    Controller.alert("Bruteforce Vector","Well, bruteforce guessed your password. Bruteforce is extremely inefficient, and it could have just been down to luck. But try lengthening your password");
                    found.set(true);
                    System.out.println("Position/Comparison " + comparisons.get() + 1 + " Found: " + builder.toString());

                }
            });
        }
        return comparisons.get();

    }
}
