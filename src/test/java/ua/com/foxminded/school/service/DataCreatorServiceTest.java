package ua.com.foxminded.school.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.school.dto.FileReaderData;
import ua.com.foxminded.school.reader.FileReader;
import ua.com.foxminded.school.reader.PropertiesReader;

class DataCreatorServiceTest {

    private PropertiesReader propertiesReader;
    private FileReader fileReader;
    private DataCreatorService creator;
    private Properties properties = new Properties();

    @BeforeEach
    void setUp() {
        propertiesReader = Mockito.mock(PropertiesReader.class);
        fileReader = Mockito.mock(FileReader.class);
        creator = new DataCreatorService(propertiesReader, fileReader);

        properties.put("courses", "Courses_Test.txt");
        properties.put("groups", "Groups_Test.txt");
        properties.put("name", "Name_Test.txt");
        properties.put("lastname", "Lastname_Test.txt");
    }

    @Test
    void test() {
        //given
        String nameProperties = "GenerateData_Test.properties";
        List<String> names = Arrays.asList("Nikita", "Stanislav", "Gennadiy", "Vladislav", "Alena");
        List<String> lastNames = Arrays.asList("Federov", "Vasnetsov", "Ivanov", "Chernikov", "Fetisov");
        List<String> groups = Arrays.asList("BA-12", "SE-66", "TK-54", "WW-30", "LS-88");
        List<String> courses = Arrays.asList("mathematics", "physics", "biology", "philosophy", "history");
        FileReaderData expected = new FileReaderData(names, lastNames, groups, courses);

        //when
        when(propertiesReader.readFileProperties(nameProperties)).thenReturn(properties);

        when(fileReader.readFile("Lastname_Test.txt")).thenReturn(lastNames);
        when(fileReader.readFile("Name_Test.txt")).thenReturn(names);
        when(fileReader.readFile("Groups_Test.txt")).thenReturn(groups);
        when(fileReader.readFile("Courses_Test.txt")).thenReturn(courses);

        FileReaderData actual = creator.create(nameProperties);

        //then
        assertEquals(actual, expected);
    }

}
