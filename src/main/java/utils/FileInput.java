package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileInput {
    private static final Logger logger =  LogManager.getLogger(FileInput.class);

    BufferedReader bufferedReader;


    public FileInput(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        this.bufferedReader = new BufferedReader(new InputStreamReader(fis));
    }


    public String readLine() throws IOException {
        String s = bufferedReader.readLine();
        // logger.debug(s);
        return s;
    }

}
