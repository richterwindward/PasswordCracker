package net.passwordcracker.cracker.vector;

import com.sun.istack.internal.Nullable;

import java.util.concurrent.ExecutorService;

public interface Vector
{
    /**
     * Attack the given password using the Supplied mask
     * @param pool The executor service used to attack.
     * @param password Password
     * @param mask The mask, nullable for most attacks
     * @return The number of comparisons it made to find the password. If the cap was reached and we gave up, returns -1
     */
    int attack(ExecutorService pool, String password, @Nullable  String mask);
}
