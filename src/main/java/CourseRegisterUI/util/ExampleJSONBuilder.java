package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleJSONBuilder {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule());

    public static List<Course> buildSampleCourses() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course(
          "Accountancy",
                "AC",
                "3202",
                "Corporate Accounting I",
                College.COLLEGE_OF_BUSINESS,
                "10857",
                "S01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                4,
                20,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "15:00",
                "17:50",
                "R",
                "YEUNG",
                "B4702",
                new Teacher("Kim Tae Wook"),
                "English",
                "03:00 PM - 05:50 PM"
        );
        Course c2 = new Course(
                "Computer Science",
                "CS",
                "1102",
                "Introduction to Computer Studies",
                College.COLLEGE_OF_COMPUTING,
                "10342",
                "C01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                0,
                120,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "16:00",
                "18:50",
                "T",
                "LI",
                "6606",
                new Teacher("Liu Chen"),
                "English",
                "04:00 PM - 06:50 PM"
        );
        Course c3 = new Course(
                "Chemistry",
                "CHEM",
                "1101",
                "Introduction to Chemistry",
                College.COLLEGE_OF_LIFE_SCIENCES,
                "11760",
                "C01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                20,
                40,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "09:00",
                "10:50",
                "R",
                "YEUNG",
                "LT-6",
                new Teacher("Chan Michael C."),
                "English",
                "09:00 AM - 10:50 AM"
        );
        Course c4 = new Course(
                "English",
                "GE",
                "2402",
                "English for Business Communication",
                College.COLLEGE_OF_LIBERAL_ARTS,
                "10091",
                "T01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                25,
                25,
                Boolean.FALSE,
                LocalDate.of(2026, 6, 8),
                LocalDate.of(2026, 7, 25),
                "09:00",
                "11:50",
                "R",
                "YEUNG",
                "P1615",
                new Teacher("TBA LC005"),
                "English",
                "09:00 AM - 11:50 AM"
        );
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        courses.add(c4);
        return courses;
    }

    public static List<User> buildSampleStudents() {
        List<User> users = new ArrayList<User>();
        List<Course> courses = buildSampleCourses();
        courses.removeLast();
        Student s1 = new Student(
                "John Doe",
                "Doe",
                "John",
                "S12345678",
                "johndoe3",
                "password1",
                College.COLLEGE_OF_BUSINESS,
                "Bachelor's Degree",
                Status.FULL_TIME,
                Program.EXCHANGE,
                "Main Campus",
                LocalDate.of(2022, 1, 12),
                LocalDate.of(2026, 4, 18),
                new ArrayList<Course>(),
                courses
        );
        Student s2 = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "S23456789",
                "janedoe4",
                "password2",
                College.COLLEGE_OF_LIFE_SCIENCES,
                "Bachelor's Degree",
                Status.PART_TIME,
                Program.LOCAL,
                "Main Campus",
                LocalDate.of(2023, 1, 12),
                LocalDate.of(2027, 4, 18),
                courses,
                new ArrayList<>()
        );
        Student s3 = new Student(
                "Frank Ocean",
                "Ocean",
                "Frank",
                "S34567890",
                "frankocean5",
                "password3",
                College.COLLEGE_OF_LIBERAL_ARTS,
                "Bachelor's Degree",
                Status.PART_TIME,
                Program.INTERNATIONAL,
                "Main Campus",
                LocalDate.of(2024, 1, 12),
                LocalDate.of(2028, 4, 18),
                courses,
                new ArrayList<>()
        );
        User u1 = new User(
                "1",
                "John Doe",
                s1
        );
        User u2 = new User(
                "2",
                "Jane Doe",
                s2
        );
        User u3 = new User(
                "3",
                "Frank Ocean",
                s3
        );

        users.add(u1);
        users.add(u2);
        users.add(u3);
        return users;
    }

    public static void writeExampleCourseFile(File file) throws IOException {
        mapper.registerSubtypes(Student.class, Teacher.class);
        mapper.writeValue(file, buildSampleCourses());
    }

    public static void writeExampleStudentFile(File file) throws IOException {
        mapper.registerSubtypes(Student.class, Teacher.class);
        mapper.writeValue(file, buildSampleStudents());
    }
}
