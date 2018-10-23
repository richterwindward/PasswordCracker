package net.passwordcracker.cracker;

import java.io.*;

public class LineSeparatedList {
    private File file;
    private BufferedReader br;

    public LineSeparatedList(File file) {
        this.file = file;
        try {
            this.br = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNextItem() {
        try {
            return this.br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

}
