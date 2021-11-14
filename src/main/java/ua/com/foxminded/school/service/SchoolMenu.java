package ua.com.foxminded.school.service;

import ua.com.foxminded.school.dao.impl.CoursesDaoImpl;
import ua.com.foxminded.school.dao.impl.GroupsDaoImpl;
import ua.com.foxminded.school.dao.impl.StudentsDaoImpl;
import ua.com.foxminded.school.model.Student;
import ua.com.foxminded.school.view.SchoolViewer;

public class SchoolMenu {

    private SchoolViewer viewer;
    private GroupsDaoImpl groupsDaoImpl;
    private CoursesDaoImpl coursesDaoImpl;
    private StudentsDaoImpl studentsDaoImpl;

    public SchoolMenu(SchoolViewer viewer, GroupsDaoImpl groupsDaoImpl, CoursesDaoImpl coursesDaoImpl,
            StudentsDaoImpl studentsDaoImpl) {
        this.viewer = viewer;
        this.groupsDaoImpl = groupsDaoImpl;
        this.coursesDaoImpl = coursesDaoImpl;
        this.studentsDaoImpl = studentsDaoImpl;
    }

    public void runSchool() {
        viewer.showMenu();

        String command = "";
        while (!command.equals("exit")) {
            command = viewer.getCommand();
            if (command.equalsIgnoreCase("a")) {
                viewer.printLessGroup(groupsDaoImpl.findMinimumGroup());

            } else if (command.equalsIgnoreCase("b")) {
                viewer.printListStudents(coursesDaoImpl.findStudentsRelatedCourse(viewer.getCourseName()));

            } else if (command.equalsIgnoreCase("c")) {
                viewer.printNewStudent(studentsDaoImpl.addStudents(new Student(viewer.setName(),
                        viewer.setLastName())));

            } else if (command.equalsIgnoreCase("d")) {
                viewer.printDeleteStudent(studentsDaoImpl.delete(viewer.setStudentId()));

            } else if (command.equalsIgnoreCase("e")) {
                viewer.printListCourses(coursesDaoImpl.getAllCourses());
                studentsDaoImpl.addStudentsCourse(viewer.setStudentIdForAdd(), viewer.setCourseIdForAdd());

            } else if (command.equalsIgnoreCase("f")) {
                studentsDaoImpl.deleteFromCourse(viewer.setStudentIdForDelete(), viewer.setCourseIdForDelete());
            }
        }
    }

}
