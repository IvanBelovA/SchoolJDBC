package ua.com.foxminded.school.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import ua.com.foxminded.school.dao.TabelsSchoolCreator;
import ua.com.foxminded.school.dao.impl.CoursesDaoImpl;
import ua.com.foxminded.school.dao.impl.GroupsDaoImpl;
import ua.com.foxminded.school.dao.impl.StudentsDaoImpl;
import ua.com.foxminded.school.dto.FileReaderData;
import ua.com.foxminded.school.dto.PropertiesDataBase;
import ua.com.foxminded.school.model.Course;
import ua.com.foxminded.school.model.Group;
import ua.com.foxminded.school.model.Student;
import ua.com.foxminded.school.reader.FileReader;

public class ShoolCreatorService {

    private static final String PROPERTIES_NAME = "createTablesScript";
    private static final int MAXIMUM_STUENTS = 30;
    private static final int MINIMUM_STUDENTS = 10;
    private static final int MAXIMUM_COURSES = 3;
    private static final int MINIMUM_COURSES = 1;
    private static final int STUDENTS_QUANTITY = 200;

    private PropertiesDataBase propertiesDataBase;
    private FileReader reader;
    private TabelsSchoolCreator creator;
    private GroupsDaoImpl groupsDaoImpl;
    private CoursesDaoImpl coursesDaoImpl;
    private StudentsDaoImpl studentsDaoImpl;

    public ShoolCreatorService(PropertiesDataBase propertiesDataBase, FileReader reader, TabelsSchoolCreator creator,
            GroupsDaoImpl groupsDaoImpl, CoursesDaoImpl coursesDaoImpl, StudentsDaoImpl studentsDaoImpl) {
        this.propertiesDataBase = propertiesDataBase;
        this.reader = reader;
        this.creator = creator;
        this.groupsDaoImpl = groupsDaoImpl;
        this.coursesDaoImpl = coursesDaoImpl;
        this.studentsDaoImpl = studentsDaoImpl;
    }

    public void createSchool(FileReaderData data) {
        creator.createTabels(reader.readFile(propertiesDataBase.getPropertiesDataBase().getProperty(PROPERTIES_NAME)));

        List<Student> studentsFullName = generateFullName(data);
        List<Group> groups = createGroups(data.getGroup());
        List<Course> courses = createCourses(data.getCourse());

        distributeStudents(groups, studentsFullName);
        assignCourse(createStudents(studentsFullName), courses);
    }

    private List<Group> createGroups(List<String> groups) {
        return groupsDaoImpl.createGroups(groups);
    }

    private List<Course> createCourses(List<String> courses) {
        return coursesDaoImpl.createCourse(courses);
    }

    private List<Student> createStudents(List<Student> students) {
        return studentsDaoImpl.createStudents(students);
    }

    private void distributeStudents(List<Group> groups, List<Student> students) {
        Collections.shuffle(students);
        Collections.shuffle(groups);
        Random random = new Random();
        ListIterator<Group> groupsIterator = groups.listIterator();
        ListIterator<Student> studentsIterator = students.listIterator();
        while (studentsIterator.hasNext()) {
            int groupSize = random.nextInt((MAXIMUM_STUENTS - MINIMUM_STUDENTS) + 1) + MINIMUM_STUDENTS;

            if (groupsIterator.hasNext()) {
                int id = groupsIterator.next().getId();
                for (int i = 0; i < groupSize && studentsIterator.hasNext(); i++) {
                    studentsIterator.next().setGroupId(id);
                }
            } else {
                studentsIterator.next().setGroupId(null);
                break;
            }
        }
    }

    private void assignCourse(List<Student> students, List<Course> courses) {
        Random random = new Random();
        for (Student student : students) {
            Collections.shuffle(courses);
            int quantityCourses = random.nextInt((MAXIMUM_COURSES - 1) + MINIMUM_COURSES) + MINIMUM_COURSES;
            courses.stream().limit(quantityCourses).forEach(p -> {
                studentsDaoImpl.assignStudents(student.getId(), p.getId());
            });
        }
    }

    private List<Student> generateFullName(FileReaderData data) {
        Random random = new Random();
        List<Student> student = new ArrayList<>();
        for (int i = 0; i < STUDENTS_QUANTITY; i++) {
            student.add(new Student(data.getName().get(random.nextInt(20)),
                    data.getLastname().get(random.nextInt(20))));
        }
        return student;
    }

}
