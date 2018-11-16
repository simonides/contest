package utils;

import java.io.*;

public class FileOutput implements AutoCloseable {
    BufferedWriter bufferedWriter;

    public FileOutput(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        FileOutputStream fos = new FileOutputStream(file);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
    }

    public void writeLine(String line) {
        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(int var) {
        try {
            bufferedWriter.write(var);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String var) {
        try {
            bufferedWriter.write(var);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(int var) {
        try {
            bufferedWriter.write(var);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newLine() {
        try {
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
