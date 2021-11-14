package ua.com.foxminded.school.dao;

import java.util.List;

import ua.com.foxminded.school.model.Course;
import ua.com.foxminded.school.model.Student;

public interface CourseDao extends GenericDao<Course> {

    List<Course> createCourse(List<String> listCourse);
    List<Student> findStudentsRelatedCourse(String courseName);
    List<Course> getAllCourses();
}
