package ua.com.foxminded.school.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderTest {

    private FileReader reader = new FileReader();
    private List<String> expected;

    @BeforeEach
    void setUp() {
        expected = Arrays.asList("Nikita", "Stanislav",
                "Gennadiy", "Vladislav", "Alena");
    }

    @Test
    void shouldReadFileAndReturnListStringWhenDeliverFileName() {
        //given
        String fileName = "name_Test.txt";

        //when
        List<String> actual = reader.readFile(fileName);

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldThrowExceptionWhenFileNameInvalid() {
        //given
        String invalidFileName = "name_Teest.txt";
        String expected = "File not found";

        //when
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> reader.readFile(invalidFileName));

        String actual = exception.getMessage();

        //then
        assertEquals(actual, expected);
    }

}
