package ua.com.foxminded.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ua.com.foxminded.school.model.Student;
import ua.com.foxminded.school.dao.DataBaseConnector;
import ua.com.foxminded.school.dao.StudentsDao;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.exceptions.DaoExceptions;

public class StudentsDaoImpl implements StudentsDao {

    private static final String INSERT_STUDENTS = "insertStudents";
    private static final String ASSIGNED = "assigned";
    private static final String ADD_STUDENT = "addStudent";
    private static final String DELETE = "delete";
    private static final String ADD_TO_COURSE = "addToCourse";
    private static final String DELETE_FROM_COURSE = "deleteToCourse";

    private DataBaseConnector connector;
    private PropertiesDataBase properties;

    public StudentsDaoImpl(DataBaseConnector connector, PropertiesDataBase properties) {
        this.connector = connector;
        this.properties = properties;
    }

    public List<Student> createStudents(List<Student> student) {
        String query = properties.getPropertiesDataBase().getProperty(INSERT_STUDENTS);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (Student students : student) {
                if (students.getGroupId() == null) {
                    statement.setNull(1, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(1, students.getGroupId());
                }
                statement.setString(2, students.getName());
                statement.setString(3, students.getLastname());
                statement.execute();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    students.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data ", e);
        }
        return student;
    }

    public void assignStudents(int idStudent, int idCourse) {
        String query = properties.getPropertiesDataBase().getProperty(ASSIGNED);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idStudent);
            statement.setInt(2, idCourse);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data ", e);
        }
    }

    public Student addStudents(Student student) {
        String query = properties.getPropertiesDataBase().getProperty(ADD_STUDENT);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                while (rs.next()) {
                    student.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data", e);
        }
        return student;
    }

    public int delete(int id) {
        String query = properties.getPropertiesDataBase().getProperty(DELETE);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data", e);
        }
        return id;
    }

    public void addStudentsCourse(int idStudent, int idCourse) {
        String query = properties.getPropertiesDataBase().getProperty(ADD_TO_COURSE);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idStudent);
            statement.setInt(2, idCourse);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("The student is already on the course");
        }
    }

    public void deleteFromCourse(int idStudent, int idCourse) {
        String query = properties.getPropertiesDataBase().getProperty(DELETE_FROM_COURSE);

        try (Connection connection = connector.connection(properties.getConfigurationDB());
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idStudent);
            statement.setInt(2, idCourse);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoExceptions("Incorrect input data", e);
        }
    }

}
