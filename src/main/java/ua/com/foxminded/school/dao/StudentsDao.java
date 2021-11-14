package ua.com.foxminded.school.dao;

import java.util.List;

import ua.com.foxminded.school.model.Student;

public interface StudentsDao extends GenericDao<Student> {

    List<Student> createStudents(List<Student> student);
    void assignStudents(int idStudent, int idCourse);
    Student addStudents(Student student);
    int delete(int id);
    void addStudentsCourse(int idStudent, int idCourse);
    void deleteFromCourse(int idStudent, int idCourse);
}
