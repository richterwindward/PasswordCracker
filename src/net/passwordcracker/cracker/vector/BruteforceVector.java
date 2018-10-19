package net.passwordcracker.cracker.vector;

import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BruteforceVector implements Vector
{

    private final String KEY_SPACE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()";
    private final int LIMIT = 500;
    private final SplittableRandom random = new SplittableRandom();

    @Override
    public int attack(ExecutorService pool, String password, String mask)
    {
        final AtomicInteger comparisons = new AtomicInteger(0);
        final AtomicBoolean found = new AtomicBoolean();
        while ( comparisons.get() <= LIMIT && ! found.get() )
        {
            pool.submit(() -> {
                final SplittableRandom local = random.split();
                final StringBuilder builder = new StringBuilder();
                for (int i = 0; i <= password.length(); i++)
                {
                    builder.append(KEY_SPACE.charAt(local.nextInt(0, KEY_SPACE.length() + 1)));
                } comparisons.addAndGet(1);
                if (builder.toString().equals(password))
                {
                    found.set(true);
                }
            });
        }
        return comparisons.get();

    }
}
