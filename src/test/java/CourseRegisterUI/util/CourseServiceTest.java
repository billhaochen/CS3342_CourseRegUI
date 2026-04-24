package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    @Test
    void loadCourseRowsFromRoot_createsOneRowPerCourse() {
        List<Course> courses = ExampleJSONBuilder.buildSampleCourses();
        Root root = new Root(List.of(), courses);

        ObservableList<CourseRow> rows = CourseService.loadCourseRowsFromRoot(root);

        assertEquals(courses.size(), rows.size());
        assertEquals(courses.get(0).title(), rows.get(0).getCourse().title());
    }

    @Test
    void loadCoursesForStudent_returnsEnrolledCoursesForStudentUser() {
        Course enrolled = ExampleJSONBuilder.buildSampleCourses().get(0);
        Student student = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "S12345678",
                "janedoe1",
                "secret",
                College.COLLEGE_OF_COMPUTING,
                "Bachelor's Degree",
                null,
                null,
                "Main Campus",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2028, 1, 1),
                List.of(enrolled),
                List.of(),
                null
        );
        User user = new User("1", "Jane Doe", student);

        ObservableList<Course> result = CourseService.loadCoursesForStudent(user);

        assertEquals(1, result.size());
        assertEquals(enrolled.title(), result.get(0).title());
    }

    @Test
    void loadCourseRowsForStudent_returnsCourseRowsForStudentUser() {
        Course enrolled = ExampleJSONBuilder.buildSampleCourses().get(0);
        Student student = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "S12345678",
                "janedoe1",
                "secret",
                College.COLLEGE_OF_COMPUTING,
                "Bachelor's Degree",
                null,
                null,
                "Main Campus",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2028, 1, 1),
                List.of(enrolled),
                List.of(),
                null
        );
        User user = new User("1", "Jane Doe", student);

        ObservableList<CourseRow> result = CourseService.loadCourseRowsForStudent(user);

        assertEquals(1, result.size());
        assertEquals(enrolled.title(), result.get(0).getCourse().title());
    }

    @Test
    void coursesFromTitles_filtersCoursesByTitleMembership() {
        List<Course> courses = ExampleJSONBuilder.buildSampleCourses();
        Root root = new Root(List.of(), courses);

        List<Course> result = CourseService.coursesFromTitles(
                root,
                List.of("Corporate Accounting I", "Introduction to Chemistry")
        );

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.title().equals("Corporate Accounting I")));
        assertTrue(result.stream().anyMatch(c -> c.title().equals("Introduction to Chemistry")));
    }
}
