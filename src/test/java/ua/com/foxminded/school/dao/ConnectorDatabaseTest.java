package ua.com.foxminded.school.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import ua.com.foxminded.school.exceptions.BufferedReaderException;
import ua.com.foxminded.school.exceptions.DaoExceptions;
import ua.com.foxminded.school.model.Student;

public class ConnectorDatabaseTest {

    private static final String NAME_FILE_SCRIPT = "schooltabelscreate_test.sql";
    private static final String URL = "url";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    private static final int COURSE_ID = 1;
    private static final int STUDENT_ID = 1;
    private static final int GROUP_ID = 1;
    private static final String GROUP_NAME = "AA-11";
    private static final String COURSE_NAME = "math";
    private static final List<Student> PREPARED_STUDENTS = Arrays.asList(new Student("Alexey", "Martynov"),
            new Student("Maxym", "Litvinov"));

    private static final String INSERT_STUDENT =
            "INSERT INTO schooldatabase.students(first_name, last_name) VALUES (?, ?);";
    private static final String INSERT_STUDENT_GROUP =
            "INSERT INTO schooldatabase.students(first_name, last_name, group_id) VALUES (?, ?, ?);";
    private static final String INSERT_COURSE =
            "INSERT INTO schooldatabase.courses(course_name) VALUES ?;";
    private static final String INSERT_GROUP =
            "INSERT INTO schooldatabase.groups(group_name) VALUES ?;";
    private static final String INSERT_STUDENT_COURSE =
            "INSERT INTO schooldatabase.students_courses(student_id, course_id) VALUES(?, ?);";

    private Properties configurationDB = new Properties();

    public Connection getCleanDataBase() {
        setUpConfig();
        createTabels(readFile(NAME_FILE_SCRIPT));
        return connection();
    }

    public Connection connection() {
        try {
            return DriverManager.getConnection(configurationDB.getProperty(URL),
                    configurationDB.getProperty(USER_NAME), configurationDB.getProperty(PASSWORD));
        } catch (SQLException e) {
            throw new DaoExceptions("Impossible get connection ", e);
        }
    }

    private void createTabels(List<String> sqlScript) { 
        try (Connection connector = connection();
                Statement statement = connector.createStatement()) {

            statement.execute(sqlScript.stream()
                    .map(Object :: toString)
                    .collect(Collectors.joining(System.lineSeparator())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpConfig() {
        configurationDB.put("url", "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        configurationDB.put("username", "sa");
        configurationDB.put("password", "");
    }

    private List<String> readFile(String fileName) {
        List<String> result = new ArrayList<>();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (ConnectorDatabaseTest.class.getClassLoader().getResourceAsStream(fileName)));
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

    public void setUpStudentCourse() {
        setUpConfig();
        createTabels(readFile(NAME_FILE_SCRIPT));
        Connection connnection = connection();

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_STUDENT)) {
            for (Student student : PREPARED_STUDENTS) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getLastname());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_COURSE)) {
            statement.setString(1, COURSE_NAME);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }
    }

    public void setUpGroupsDaoTest() {
        setUpConfig();
        createTabels(readFile(NAME_FILE_SCRIPT));
        Connection connnection = connection();

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_GROUP)) {
            statement.setString(1, GROUP_NAME);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_STUDENT_GROUP)) {
            statement.setString(1, PREPARED_STUDENTS.get(1).getName());
            statement.setString(2, PREPARED_STUDENTS.get(1).getLastname());
            statement.setInt(3, GROUP_ID);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }
    }

    public void setUpCourseDaoTest() {
        setUpConfig();
        createTabels(readFile(NAME_FILE_SCRIPT));
        Connection connnection = connection();

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_COURSE)) {
            statement.setString(1, COURSE_NAME);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_STUDENT)) {
            for (Student student : PREPARED_STUDENTS) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getLastname());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }

        try (PreparedStatement statement = connnection.prepareStatement(INSERT_STUDENT_COURSE)) {
            statement.setInt(1, STUDENT_ID);
            statement.setInt(2, COURSE_ID);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data.");
        }
    }
    
    public Properties setUpSqlQuery() {
//        Properties properties = new Properties();
        Properties properties1 = new Properties();
        properties1.put("insertCourse", "INSERT INTO schooldatabase.courses(course_name) VALUES (?)");
        properties1.put("findStudents", "SELECT s.first_name, s.last_name FROM schooldatabase.courses AS c "
                + "JOIN schooldatabase.students_courses AS sc "
                + "ON sc.course_id = c.course_id JOIN schooldatabase.students AS s "
                + "ON s.student_id = sc.student_id WHERE c.course_name = ?");
        properties1.put("getAll", "SELECT course_id, course_name FROM schooldatabase.courses");
        properties1.put("findLessGroup", "SELECT students.group_id, COUNT(students.group_id), groups.group_name "
                + "FROM schooldatabase.students JOIN schooldatabase.groups "
                + "ON groups.group_id = students.group_id GROUP BY students.group_id, groups.group_name "
                + "HAVING COUNT(students.group_id) <= (SELECT MIN(min) "
                + "FROM (SELECT group_id, COUNT(group_id) AS min FROM schooldatabase.students "
                + "WHERE group_id IS NOT NULL GROUP BY group_id) AS min);");
        properties1.put("insertGroup", "INSERT INTO schooldatabase.groups(group_name) VALUES (?)");
        properties1.put("insertStudents", "INSERT INTO schooldatabase.students(group_id, first_name, last_name) VALUES (?, ?, ?)");
        properties1.put("assigned", "INSERT INTO schooldatabase.students_courses(student_id, course_id) VALUES (?, ?)");
        properties1.put("addStudent", "INSERT INTO schooldatabase.students(first_name, last_name) VALUES (?, ?)");
        properties1.put("delete", "DELETE FROM schooldatabase.students WHERE student_id = ?");
        properties1.put("addToCourse", "INSERT INTO schooldatabase.students_courses(student_id, course_id) VALUES (?, ?)");
        properties1.put("deleteToCourse", "DELETE FROM schooldatabase.students_courses WHERE student_id = ? AND course_id = ?;");
        return properties1;
    }

}
