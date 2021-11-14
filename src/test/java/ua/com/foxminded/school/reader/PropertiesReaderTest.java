package ua.com.foxminded.school.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertiesReaderTest {

    private PropertiesReader reader = new PropertiesReader();
    private Properties expected = new Properties();

    @BeforeEach
    void setUp() {
        expected.put("createTablesScript", "schoolTabelsCreate_Test.sql");
    }

    @Test
    void shouldReturnPropertyWhenDeliverFileNameWithProperty() {
        //given
        String propertiesName = "database_Test.properties";

        //when
        Properties actual = reader.readFileProperties(propertiesName);

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldThrowExceptionWhenDeliverInvalidFileName() {
        //given
        String invalidFileName = "database1_Test.properties";
        String expected = "Properties file not found";

        //when
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> reader.readFileProperties(invalidFileName));

        String actual = exception.getMessage();

        //then
        assertEquals(actual, expected);
    }

}
