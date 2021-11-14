package ua.com.foxminded.school.service;

import java.util.Properties;

import ua.com.foxminded.school.dto.FileReaderData;
import ua.com.foxminded.school.reader.FileReader;
import ua.com.foxminded.school.reader.PropertiesReader;

public class DataCreatorService {

    private static final String NAME = "name";
    private static final String LASTNAME = "lastname";
    private static final String COURSES = "courses";
    private static final String GROUPS = "groups";

    private PropertiesReader propertiesReader;
    private FileReader fileReader;

    public DataCreatorService(PropertiesReader propertiesReader, FileReader fileReader) {
        this.propertiesReader = propertiesReader;
        this.fileReader = fileReader;
    }

    public FileReaderData create(String nameProperties) {
        Properties properties = propertiesReader.readFileProperties(nameProperties);

        return new FileReaderData(fileReader.readFile(properties.getProperty(NAME)),
                fileReader.readFile(properties.getProperty(LASTNAME)),
                fileReader.readFile(properties.getProperty(GROUPS)),
                fileReader.readFile(properties.getProperty(COURSES)));
    }

}
