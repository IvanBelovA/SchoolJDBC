package ua.com.foxminded.school.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import ua.com.foxminded.school.exceptions.BufferedReaderException;

public class PropertiesReader {

    public Properties readFileProperties(String propertiesName) {
        Properties properties = new Properties();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    PropertiesReader.class.getClassLoader().getResourceAsStream(propertiesName)));
            properties.load(bufferedReader);

        } catch (IOException e) {
            throw new BufferedReaderException("Impossible to read the file");
        } catch (NullPointerException e) {
            throw new NullPointerException("Properties file not found");
        }

        return properties;
    }

}
