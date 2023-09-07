package parser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static List<String> parseData(File file) {
        List<String> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                if (line.isBlank()) continue;
                result.add(line);
            }

            return result;
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    };


}
