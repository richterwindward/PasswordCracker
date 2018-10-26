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
            String nextNoun;

            if((nextNoun = this.wrappedNounsFile.sanitize(wrappedNounsFile.getNextPassword()).toLowerCase()) == null) {
                /* check EOF then return */
                break;
            }

            final int temp = comparisons;

            Platform.runLater(() -> {
                Controller.get().attempts.setText("About " + temp + " Comparisons");
            });

            /* capitalize the string */
            String capitalizedItem = nextNoun.substring(0, 1).toUpperCase() + nextNoun.substring(1);

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
