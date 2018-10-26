package net.passwordcracker.cracker;

import java.io.*;

public class LineSeperatedList {
    private File file;
    private BufferedReader br;

    public LineSeperatedList(File file) {
        this.file = file;
        try {
            this.br = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNextPassword() {
        try {
            return this.br.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    public void reset() {
        try
        {
            this.br.close();
            this.br = new BufferedReader(new FileReader(file));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sanitize(String s) {
        return s.replace(".", "").replace(" ", "").replace("\'", "");
    }

}
