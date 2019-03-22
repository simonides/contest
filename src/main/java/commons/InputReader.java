package commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

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

    public Table<Integer, Integer, Integer> readIntTable(int height) {

        final HashBasedTable<Integer, Integer, Integer> table = HashBasedTable.create();

        for (int y = 0; y < height; y++) {
            final List<Integer> row = readIntParts();

            for (int x = 0; x < row.size(); x++) {
                table.put(y, x, row.get(x));
            }
        }

        return table;
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
