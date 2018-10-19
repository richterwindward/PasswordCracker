package sample.io;

import java.io.*;

public class Wordlist {
    private File file;
    private BufferedReader br;

    public Wordlist(File file) {
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

}
