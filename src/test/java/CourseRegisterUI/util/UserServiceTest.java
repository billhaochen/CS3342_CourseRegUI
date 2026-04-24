package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void assignStudentEid_returnsNameWithOneWhenNoMatchingStudentsExist() {
        Root root = new Root(List.of(), List.of());

        String eid = UserService.assignStudentEid(root, "Jane Doe");

        assertEquals("janedoe1", eid);
    }

    @Test
    void getStudentByName_returnsMatchingStudentUser() {
        User user = studentUser("1", "Jane Doe", "S12345678", "janedoe1", List.of(), List.of());
        Root root = new Root(List.of(user), List.of());

        Optional<User> result = UserService.getStudentByName(root, "Jane Doe");

        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().name());
    }

    @Test
    void getStudentById_returnsMatchingStudentUser() {
        User user = studentUser("1", "Jane Doe", "S12345678", "janedoe1", List.of(), List.of());
        Root root = new Root(List.of(user), List.of());

        Optional<User> result = UserService.getStudentByID(root, "S12345678");

        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().name());
    }

    @Test
    void getCoursesFromStudent_returnsEnrolledCoursesForStudentRole() {
        Course course = ExampleJSONBuilder.buildSampleCourses().get(0);
        User user = studentUser("1", "Jane Doe", "S12345678", "janedoe1", List.of(course), List.of());

        List<Course> result = UserService.getCoursesFromStudent(user);

        assertEquals(1, result.size());
        assertEquals(course.title(), result.get(0).title());
    }

    @Test
    void getCourseRowsFromStudent_wrapsEnrolledCoursesInCourseRows() {
        Course course = ExampleJSONBuilder.buildSampleCourses().get(0);
        User user = studentUser("1", "Jane Doe", "S12345678", "janedoe1", List.of(course), List.of());

        List<CourseRow> result = UserService.getCourseRowsFromStudent(user);

        assertEquals(1, result.size());
        assertEquals(course.title(), result.get(0).getCourse().title());
    }

    private User studentUser(String id, String name, String studentId, String eid, List<Course> enrolled, List<Course> completed) {
        Student student = new Student(
                name,
                "Doe",
                "Jane",
                studentId,
                eid,
                "secret",
                College.COLLEGE_OF_COMPUTING,
                "Bachelor's Degree",
                null,
                null,
                "Main Campus",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2028, 1, 1),
                enrolled,
                completed,
                null,
                List.of()
        );
        return new User(id, name, student);
    }
}
