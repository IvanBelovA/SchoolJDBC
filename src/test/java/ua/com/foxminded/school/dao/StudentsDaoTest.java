package ua.com.foxminded.school.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.school.dao.impl.StudentsDaoImpl;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.exceptions.DaoExceptions;
import ua.com.foxminded.school.model.Student;

class StudentsDaoTest {

    private DataBaseConnector connector;
    private PropertiesDataBase properties;
    private StudentsDaoImpl studentsDao;
    private List<Student> preparedStudents;

    @BeforeEach
    void setUp() {
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        Properties testProperties = databaseTest.setUpSqlQuery();

        connector = Mockito.mock(DataBaseConnector.class);
        properties = Mockito.mock(PropertiesDataBase.class);
        studentsDao = new StudentsDaoImpl(connector, properties);

        preparedStudents = Arrays.asList(new Student("Alexey", "Martynov"),
                new Student("Maxym", "Litvinov"));

        when(properties.getPropertiesDataBase()).thenReturn(testProperties);
    }

    @Test
    void test() {
        //given
        ConnectorDatabaseTest test = new ConnectorDatabaseTest();
        Student expected = new Student(1, "Ivan", "Petrov");

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(test.getCleanDataBase());
        Student actual = studentsDao.addStudents(new Student("Ivan", "Petrov"));

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldReturnListStudentsWithIdWhenDeliverListStudents() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        List<Student> std = Arrays.asList(new Student("Alexey", "Martynov"), new Student("Maxym", "Litvinov"));

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.getCleanDataBase());
        List<Student> actual = Arrays.asList(new Student(1, "Alexey", "Martynov"), new Student(2 ,"Maxym", "Litvinov"));
        List<Student> expected = studentsDao.createStudents(std);

        //then
        assertEquals(actual, expected);
    }

    @Test
    void shouldDeleteStudentWhenDeliverStudentId() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        Connection connection = databaseTest.getCleanDataBase();

        String selectionTestData = "SELECT student_id FROM schooldatabase.students WHERE student_id = ?;";
        String insertTestsData = "INSERT INTO schooldatabase.students(first_name, last_name) VALUES (?, ?);";

        int idForDelete = 1;
        String resultDeletedStudent = new String();

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.getCleanDataBase());
        try (PreparedStatement statement = connection.prepareStatement(insertTestsData)) {
            for (Student student : preparedStudents) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getLastname());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        studentsDao.delete(idForDelete);


        try (PreparedStatement statement = connection.prepareStatement(selectionTestData)) {
            statement.setInt(1, idForDelete);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.first()) {
                    resultDeletedStudent = null;
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        //then
        assertNull(resultDeletedStudent);
    }

    @Test
    void shouldInsertIdStudentAndIdCourseWhenDeliverIdStudentAndIdCourse() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpStudentCourse();

        String selectionTestData = "SELECT student_id, course_id FROM schooldatabase.students_courses;";
        int expectedStudentId = 1;
        int expectedCourseId = 1;
        int actualStudentId = 0;
        int actualCourseId = 0;

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        studentsDao.assignStudents(expectedStudentId, expectedCourseId);

        try (PreparedStatement statement = databaseTest.connection().prepareStatement(selectionTestData)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    actualStudentId = rs.getInt(1);
                    actualCourseId = rs.getInt(2);
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("");
        }

        //then
        assertEquals(actualStudentId, expectedStudentId);
        assertEquals(actualCourseId, expectedCourseId);
    }

    @Test
    void shouldInsertIdStudentAndIdCourseFromCourseWhenDeliverIdStudentAndIdCourse() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpStudentCourse();

        String selectionTestData = "SELECT student_id, course_id FROM schooldatabase.students_courses;";
        int expectedStudentId = 1;
        int expectedCourseId = 1;
        int actualStudentId = 0;
        int actualCourseId = 0;

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        studentsDao.addStudentsCourse(expectedStudentId, expectedCourseId);

        try (PreparedStatement statement = databaseTest.connection().prepareStatement(selectionTestData)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    actualStudentId = rs.getInt(1);
                    actualCourseId = rs.getInt(2);
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("");
        }

        //then
        assertEquals(actualStudentId, expectedStudentId);
        assertEquals(actualCourseId, expectedCourseId);
    }

    @Test
    void shouldDeleteStudentFromCourseWhenDeliverStudentIdAndCourseId() {
        //given
        ConnectorDatabaseTest databaseTest = new ConnectorDatabaseTest();
        databaseTest.setUpStudentCourse();

        String selectionStudentCourse = "SELECT student_id FROM schooldatabase.students_courses WHERE student_id = ?;";
        Integer studentIdDelete = 1;
        Integer courseIdDelete = 1;

        //when
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        studentsDao.addStudentsCourse(studentIdDelete, courseIdDelete);
        when(connector.connection(properties.getConfigurationDB())).thenReturn(databaseTest.connection());
        studentsDao.deleteFromCourse(studentIdDelete, courseIdDelete);

        try (PreparedStatement statement = databaseTest.connection().prepareStatement(selectionStudentCourse)) {
            statement.setInt(1, studentIdDelete);
            statement.execute();
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.first()) {
                    studentIdDelete = null;
                    courseIdDelete = null;
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("");
        }

        //then
        assertNull(courseIdDelete);
        assertNull(studentIdDelete);
    }

}
