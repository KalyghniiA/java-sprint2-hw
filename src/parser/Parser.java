package parser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    public static List<String> parseDataToMonth(File file) {
        List<String> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                result.add(br.readLine());
            }

            return result;
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
