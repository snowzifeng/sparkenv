package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtil {

    public static List<List<String>> readCsv(final String filename, final boolean jumpHead) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<List<String>> ret = new ArrayList<List<String>>();
        String line;

        if (jumpHead) {
            reader.readLine();
        }

        while ((line = reader.readLine()) != null) {
            String[] strings = line.split(",");
            ret.add(Arrays.asList(strings));
        }
        return ret;
    }

    public static List<List<String>> readCsv(final String filename) throws IOException {
        return readCsv(filename, true);
    }

}
