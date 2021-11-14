package ua.com.foxminded.school.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.school.exceptions.BufferedReaderException;

public class FileReader {

    public List<String> readFile(String fileName) {
        List<String> result = new ArrayList<>();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileReader.class.getClassLoader()
                    .getResourceAsStream(fileName)));
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible to read the file");
        } catch (NullPointerException e) {
            throw new NullPointerException("File not found");
        }
        return result;
    }

}
