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
    private final String[] GRADUATION_YEARS = {"2019", "2020", "2021", "2022", "2023", "2024"};

    private File nounsFile;
    private LineSeperatedList wrappedNounsFile;

    public WindwardVector(File nounsFile) {
        this.nounsFile = nounsFile;
        this.wrappedNounsFile = new LineSeperatedList(nounsFile);
    }

    private String[] computeGraduationYears() {
        String[] graduationYears = new String[6];
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 6; i++) {
            graduationYears[i] = String.valueOf(year + i);
        }
        return graduationYears;
    }

    public int attack(ExecutorService pool, String password, String mask) {
        boolean found = false;
        int comparisons = 0;
        while(!found) {
            String nextNoun = wrappedNounsFile.getNextPassword();
            final int temp = comparisons;
            Platform.runLater(() -> {
                Controller.get().attempts.setText("About " + temp + " Comparisons");
            });
            if(nextNoun == null) {
                break;
            }

            String capitalizedNoun = nextNoun.substring(0, 1).toUpperCase() + nextNoun.substring(1);
            StringBuilder pwd = new StringBuilder(capitalizedNoun);
            int maskLen = pwd.length();

            for (char symbol:
                 SPECIAL_SYMBOLS.toCharArray()) {
                pwd.append(symbol);
                for(String year:
                    GRADUATION_YEARS) {
                    pwd.append(year);
                    if(pwd.toString().equals(password)) {
                        Controller.alert("Windward Vector: Tips","There really isn't too much you can do here! Windward passwords are very predictable, and the school makes you use them. Sorry bud, fresh out of luck");
                        comparisons++;
                        System.out.println("Found password: " + pwd.toString());
                        return 1;
                    }
                    pwd.delete(maskLen + 1, pwd.length()); /* delete the year from the StringBuilder */
                }
                pwd.deleteCharAt(maskLen); /* delete the special character from the StringBuilder */
            }
        }

        return comparisons;
    }
}
