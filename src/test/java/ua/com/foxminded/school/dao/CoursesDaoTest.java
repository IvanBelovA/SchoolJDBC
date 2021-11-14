package ua.com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.school.dao.impl.CoursesDaoImpl;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.model.Course;
import ua.com.foxminded.school.model.Student;

class CoursesDaoTest {

    private static final String COURSE_NAME = "math";
    private static final int COURSE_ID = 1;

    private CoursesDaoImpl coursesDaoImpl;
    private DataBaseConnector connector;
    private PropertiesDataBase properties;

    @BeforeEach
    void setUp() {
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        Properties testProperties = databaseTest.setUpSqlQuery();

        connector = Mockito.mock(DataBaseConnector.class);
        properties = Mockito.mock(PropertiesDataBase.class);
        coursesDaoImpl = new CoursesDaoImpl(connector, properties);

        when(properties.getPropertiesDataBase()).thenReturn(testProperties);
    }

    @Test
    void shouldReturnListCoursesWhenDeliverListStringWithNameCourses() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.getCleanDataBase();
        List<Course> actual = Arrays.asList(new Course(COURSE_ID, COURSE_NAME));
        List<String> courseName = Arrays.asList(COURSE_NAME);

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        List<Course> expected = coursesDaoImpl.createCourse(courseName);

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldReturnListCoursesWithRealetionStudentWhenDeliverCourseName() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpCourseDaoTest();
        List<Student> actual = Arrays.asList(new Student("Alexey", "Martynov"));

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        List<Student> expected = coursesDaoImpl.findStudentsRelatedCourse(COURSE_NAME);

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldReturnAllCoursesWhenCallHim() {
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpCourseDaoTest();
        List<Course> actual = Arrays.asList(new Course(COURSE_ID, COURSE_NAME));

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        List<Course> expected = coursesDaoImpl.getAllCourses();

        //then
        assertEquals(actual, expected);
    }

}
