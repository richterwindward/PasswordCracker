package net.passwordcracker.cracker.vector;


import com.sun.tools.javac.util.StringUtils;
import net.passwordcracker.cracker.LineSeparatedList;

import java.io.File;
import java.util.Calendar;

public class WindwardVector
{
    private final String SPECIAL_SYMBOLS = "!@#$%^&*()?";
    private final String[] GRADUATION_YEARS = {"2019", "2020", "2021", "2022", "2023", "2024"};

    private File nounsFile;
    private LineSeparatedList wrappedNounsFile;

    public WindwardVector(File nounsFile) {
        this.nounsFile = nounsFile;
        this.wrappedNounsFile = new LineSeparatedList(nounsFile);
    }

    private String[] computeGraduationYears() {
        String[] graduationYears = new String[6];
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 6; i++) {
            graduationYears[i] = String.valueOf(year + i);
        }
        return graduationYears;
    }

    public int attack(String password) {
        boolean found = false;

        while(!found) {
            String nextNoun = wrappedNounsFile.getNextItem();

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
                        System.out.println("Found password: " + pwd.toString());
                        return 1;
                    }
                    pwd.delete(maskLen + 1, pwd.length()); /* delete the year from the StringBuilder */
                }
                pwd.deleteCharAt(maskLen); /* delete the special character from the StringBuilder */
            }
        }

        return 0;
    }
}
