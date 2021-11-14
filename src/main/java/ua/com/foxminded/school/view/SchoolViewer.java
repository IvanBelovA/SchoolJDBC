package ua.com.foxminded.school.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ua.com.foxminded.school.exceptions.BufferedReaderException;
import ua.com.foxminded.school.model.Course;
import ua.com.foxminded.school.model.GroupModel;
import ua.com.foxminded.school.model.Student;

public class SchoolViewer {

    public void showMenu() {
        String menu = "Enter one of the below commands:" + System.lineSeparator()
        + "a. Find all groups with less or equals student count" + System.lineSeparator()
        + "b. Find all students related to course with given name" + System.lineSeparator()
        + "c. Add new student" + System.lineSeparator()
        + "d. Delete student by STUDENT_ID" + System.lineSeparator()
        + "e. Add a student to the course (from a list)" + System.lineSeparator()
        + "f. Remove the student from one of his or her courses";

        System.out.println(menu);
    }

    public String getCommand() {
        System.out.println("Waiting command:");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read command");
        }
    }

    public String getCourseName() {
        System.out.println("Enter course name:");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read course name.");
        }
    }

    public String setName() {
        System.out.println("Enter student name");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read student name");
        }
    }

    public String setLastName() {
        System.out.println("Enter student last name");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read student last name.");
        }
    }

    public void printNewStudent(Student student) {
        System.out.println(String.format("New student number of %s %s %s, added %n",
                student.getId(), student.getName(), student.getLastname()));
    }

    public int setStudentId() {
        System.out.println("Enter Student_Id from delete");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read student_id.");
        } catch (NumberFormatException e) {
            throw new BufferedReaderException("Introduced not integer type");
        }
    }

    public void printDeleteStudent(int id) {
        System.out.println(String.format("Student number %d delete %n", id));
    }

    public void printListCourses(List<Course> courses) {
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public int setStudentIdForAdd() {
        System.out.println("Enter Student_Id for add to course");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read student id");
        } catch (NumberFormatException e) {
            throw new BufferedReaderException("Introduced not integer type");
        }
    }

    public int setCourseIdForAdd() {
        System.out.println("Enter Course_Id from add");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read course id");
        } catch (NumberFormatException e) {
            throw new BufferedReaderException("Introduced not integer type");
        }
    }

    public int setStudentIdForDelete() {
        System.out.println("Enter Student_Id for delete to course");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read student id");
        } catch (NumberFormatException e) {
            throw new BufferedReaderException("Introduced not integer type");
        }
    }

    public int setCourseIdForDelete() {
        System.out.println("Enter Course_Id for delete to course");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new BufferedReaderException("Impossible read course id");
        } catch (NumberFormatException e) {
            throw new BufferedReaderException("Introduced not integer type");
        }
    }

    public void printLessGroup(List<GroupModel> groups) {
        for (GroupModel group : groups) {
            System.out.println(String.format("Less group №%d, name - %s, quantity students %d.",
                    group.getGroupId(), group.getGroupName(), group.getGroupQuantity()));
        }
    }

    public void printListStudents(List<Student> students) {
        System.out.println("List students related course:");
        for (Student student : students) {
            System.out.println(String.format("%s %s.", student.getName(), student.getLastname()));
        }
    }

}
