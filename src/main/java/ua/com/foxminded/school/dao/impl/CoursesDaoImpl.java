package ua.com.foxminded.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.school.dao.CourseDao;
import ua.com.foxminded.school.dao.DataBaseConnector;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.exceptions.DaoExceptions;
import ua.com.foxminded.school.model.Course;
import ua.com.foxminded.school.model.Student;

public class CoursesDaoImpl implements CourseDao {

    private static final String INSERT_COURSE = "insertCourse";
    private static final String GET_ALL = "getAll";
    private static final String FIND_STIDENTS = "findStudents";

    private DataBaseConnector connector;
    private PropertiesDataBase properties;

    public CoursesDaoImpl(DataBaseConnector connector, PropertiesDataBase properties) {
        this.connector = connector;
        this.properties = properties;
    }

    public List<Course> createCourse(List<String> listCourses) {
        List<Course> courses = new ArrayList<>();
        String query = properties.getPropertiesDataBase().getProperty(INSERT_COURSE);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (String course : listCourses) {
                statement.setString(1, course);
                statement.execute();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    courses.add(new Course(rs.getInt(1), course));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect data input");
        }
        return courses;
    }

    public List<Student> findStudentsRelatedCourse(String courseName) {
        List<Student> students = new ArrayList<>();
        String query = properties.getPropertiesDataBase().getProperty(FIND_STIDENTS);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect course name");
        }
        return students;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = properties.getPropertiesDataBase().getProperty(GET_ALL);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    courses.add(new Course(rs.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Impossible get course");
        }
        return courses;
    }

}
