package commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OutputWriter implements AutoCloseable {
    BufferedWriter bufferedWriter;

    public OutputWriter(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        FileOutputStream fos = new FileOutputStream(file);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
    }

    public void writeLine(String... parts) {
        writeSingleLine(StringUtils.join(parts, " "));
    }

    public void writeStringLine(List<String> parts) {
        writeSingleLine(StringUtils.join(parts));
    }

    public void writeIntLine(List<Integer> parts) {
        writeSingleLine(StringUtils.join(parts));
    }

    public void writeLine(int... parts) {
        writeSingleLine(StringUtils.join(parts, " "));
    }

    public void printfLine(String format, Object... args) {
        final String str = String.format(format, args);
        writeSingleLine(str);
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
            bufferedWriter.write(String.valueOf(var));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeSingleLine(String line) {
        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    public void newLine() {
    //        try {
    //            bufferedWriter.newLine();
    //        } catch (IOException e) {
    //            throw new RuntimeException(e);
    //        }
    //    }

    public void close() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
