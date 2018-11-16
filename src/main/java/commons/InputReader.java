package commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InputReader implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(InputReader.class);

    private BufferedReader bufferedReader;

    public InputReader(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        this.bufferedReader = new BufferedReader(new InputStreamReader(fis));
    }

    public String readLine() {
        try {
            String s = bufferedReader.readLine();
            // logger.debug(s);
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readLineInt() {
        return Integer.parseInt(readLine());
    }

    public List<String> readStringParts() {
        String line = readLine();
        return Arrays.asList(line.split(" "));
    }

    public List<Integer> readIntParts() {
        String line = readLine();
        String[] parts = line.split(" ");
        return Arrays.stream(parts)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public <Obj> Obj readLineObject(Function<List<String>, Obj> mapper) {
        return mapper.apply(readStringParts());
    }

    public <Obj> Stream<Obj> readObjects(Function<List<String>, Obj> mapper) {
        int numberOfObjects = readLineInt();
        return IntStream.range(0, numberOfObjects).mapToObj(index -> readLineObject(mapper));
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
