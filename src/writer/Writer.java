package writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    public static void saveData(File file, String header, List<String> info) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(header);
            for (String line: info) {
                fw.write("\n");
                fw.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
