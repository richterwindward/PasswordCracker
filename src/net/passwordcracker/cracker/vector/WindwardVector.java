package net.passwordcracker.cracker.vector;


import javafx.application.Platform;
import net.passwordcracker.cracker.Controller;
import net.passwordcracker.cracker.LineSeperatedList;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;

public class WindwardVector implements Vector
{
    private final String SPECIAL_SYMBOLS = "!@#$%^&*()?";
    private final String[] GRADUATION_YEARS;
    private final char[] SPECIAL_SYMBOLS_CHAR_ARRAY;

    private File nounsFile;
    private LineSeperatedList wrappedNounsFile;

    public WindwardVector(File nounsFile) {
        this.nounsFile = nounsFile;
        this.wrappedNounsFile = new LineSeperatedList(nounsFile);
        this.GRADUATION_YEARS = this.computeGraduationYears();
        this.SPECIAL_SYMBOLS_CHAR_ARRAY = this.SPECIAL_SYMBOLS.toCharArray();
    }

    private String[] computeGraduationYears() {
        String[] graduationYears = new String[6];
        final int year = Calendar.getInstance().get(Calendar.YEAR) + 1;

        for (int i = 0; i < 6; i++) {
            graduationYears[i] = String.valueOf(year + i);
        }

        return graduationYears;
    }

    public int attack(ExecutorService pool, String password, String mask) {
        boolean found = false;
        int comparisons = 0;


        while(!found) {
            String nextItem = wrappedNounsFile.getNextPassword();

            if(nextItem == null) {
                /* check for EOF */
                break;
            }

            nextItem = nextItem.toLowerCase();

            final int temp = comparisons;

            Platform.runLater(() -> {
                Controller.get().attempts.setText("About " + temp + " Comparisons");
            });

            /* capitalize the string */
            String capitalizedItem = nextItem.substring(0, 1).toUpperCase() + nextItem.substring(1);

            StringBuilder pwd = new StringBuilder(capitalizedItem);
            int maskLen = pwd.length();

            for (char symbol:
                    this.SPECIAL_SYMBOLS_CHAR_ARRAY) {
                pwd.append(symbol);
                for(String year:
                    this.GRADUATION_YEARS) {
                    pwd.append(year);
                    comparisons++;
                    if(pwd.toString().equals(password)) {
                        System.out.println("Windward Vector: Tips\nThere really isn't too much you can do here! Windward passwords are very predictable, and the school makes you use them. Sorry bud, fresh out of luck");
                        System.out.println("Found password: " + pwd.toString());
                        return comparisons;
                    }
                    /* delete the year from the StringBuilder */
                    pwd.delete(maskLen + 1, pwd.length());
                }
                /* delete the special character from the StringBuilder */
                pwd.deleteCharAt(maskLen);
            }
        }

        return comparisons;
    }
}
