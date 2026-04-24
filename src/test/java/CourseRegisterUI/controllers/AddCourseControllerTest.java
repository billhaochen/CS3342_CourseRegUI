package CourseRegisterUI.controllers;

import CourseRegisterUI.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddCourseControllerTest {

    private AddCourseController controller;

    @BeforeEach
    void setUp() {
        controller = new AddCourseController();
    }

    @Test
    void timeConflicts_returnsTrue_whenSameDayDateRangeAndTimeRangeOverlap() throws Exception {
        Course first = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:30",
                List.of()
        );
        Course second = course(
                "CS1020", "Mon",
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 4, 30),
                "10:00", "11:00",
                List.of()
        );

        boolean result = invokePrivateBoolean("timeConflicts", new Class[]{Course.class, Course.class}, first, second);

        assertTrue(result);
    }

    @Test
    void timeConflicts_returnsFalse_whenDaysDiffer() throws Exception {
        Course first = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:30",
                List.of()
        );
        Course second = course(
                "CS1020", "Tue",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:30", "10:15",
                List.of()
        );

        boolean result = invokePrivateBoolean("timeConflicts", new Class[]{Course.class, Course.class}, first, second);

        assertFalse(result);
    }

    @Test
    void timeConflicts_returnsFalse_whenDateRangesDoNotOverlap() throws Exception {
        Course first = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10),
                "09:00", "10:30",
                List.of()
        );
        Course second = course(
                "CS1020", "Mon",
                LocalDate.of(2026, 2, 11), LocalDate.of(2026, 5, 10),
                "09:15", "10:00",
                List.of()
        );

        boolean result = invokePrivateBoolean("timeConflicts", new Class[]{Course.class, Course.class}, first, second);

        assertFalse(result);
    }

    @Test
    void timeConflicts_returnsFalse_whenTimesOnlyTouchAtBoundary() throws Exception {
        Course first = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:00",
                List.of()
        );
        Course second = course(
                "CS1020", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "10:00", "11:00",
                List.of()
        );

        boolean result = invokePrivateBoolean("timeConflicts", new Class[]{Course.class, Course.class}, first, second);

        assertFalse(result);
    }

    @Test
    void timeConflicts_returnsFalse_whenFirstCourseDayIsNull() throws Exception {
        Course first = course(
                "CS1010", null,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:00",
                List.of()
        );
        Course second = course(
                "CS1020", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:30", "10:30",
                List.of()
        );

        boolean result = invokePrivateBoolean("timeConflicts", new Class[]{Course.class, Course.class}, first, second);

        assertFalse(result);
    }

    @Test
    void takenConflicts_returnsTrue_whenStudentAlreadyCompletedOneRequestedCourse() throws Exception {
        Course completed = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:00",
                List.of()
        );
        Course newCourse = course(
                "CS1020", "Tue",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "11:00", "12:00",
                List.of()
        );
        User studentUser = userWithCompletedCourses(completed);

        boolean result = invokePrivateBoolean("takenConflicts", new Class[]{User.class, List.class}, studentUser, List.of(completed, newCourse));

        assertTrue(result);
    }

    @Test
    void takenConflicts_returnsFalse_whenStudentHasNotCompletedRequestedCourses() throws Exception {
        Course completed = course(
                "CS1010", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "09:00", "10:00",
                List.of()
        );
        Course requested = course(
                "CS1020", "Tue",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "11:00", "12:00",
                List.of()
        );
        User studentUser = userWithCompletedCourses(completed);

        boolean result = invokePrivateBoolean("takenConflicts", new Class[]{User.class, List.class}, studentUser, List.of(requested));

        assertFalse(result);
    }

    @Test
    void takenConflicts_returnsFalse_whenUserIsSignedOut() throws Exception {
        Course requested = course(
                "CS1020", "Tue",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "11:00", "12:00",
                List.of()
        );
        User signedOut = new User("", "Guest", new SignedOut());

        boolean result = invokePrivateBoolean("takenConflicts", new Class[]{User.class, List.class}, signedOut, List.of(requested));

        assertFalse(result);
    }

    @Test
    void prerequisiteConflicts_returnsFalse_whenStudentHasAllPrerequisites() throws Exception {
        Course prerequisite = course(
                "CS1000", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "08:00", "09:00",
                List.of()
        );
        Course requested = course(
                "CS2000", "Wed",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "13:00", "14:00",
                List.of(prerequisite)
        );
        User studentUser = userWithCompletedCourses(prerequisite);

        boolean result = invokePrivateBoolean("prerequisiteConflicts", new Class[]{User.class, List.class}, studentUser, List.of(requested));

        assertFalse(result);
    }

    @Test
    void prerequisiteConflicts_returnsTrue_whenStudentIsMissingPrerequisite() throws Exception {
        Course prerequisite = course(
                "CS1000", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "08:00", "09:00",
                List.of()
        );
        Course requested = course(
                "CS2000", "Wed",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "13:00", "14:00",
                List.of(prerequisite)
        );
        User studentUser = userWithCompletedCourses();

        boolean result = invokePrivateBoolean("prerequisiteConflicts", new Class[]{User.class, List.class}, studentUser, List.of(requested));

        assertTrue(result);
    }

    @Test
    void prerequisiteConflicts_returnsFalse_whenUserIsNotStudent() throws Exception {
        Course prerequisite = course(
                "CS1000", "Mon",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "08:00", "09:00",
                List.of()
        );
        Course requested = course(
                "CS2000", "Wed",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "13:00", "14:00",
                List.of(prerequisite)
        );
        User signedOut = new User("", "Guest", new SignedOut());

        boolean result = invokePrivateBoolean("prerequisiteConflicts", new Class[]{User.class, List.class}, signedOut, List.of(requested));

        assertFalse(result);
    }

    @Test
    void prerequisiteConflicts_returnsFalse_whenRequestedCoursesHaveNoPrerequisites() throws Exception {
        Course requested = course(
                "CS2000", "Wed",
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 5, 10),
                "13:00", "14:00",
                List.of()
        );
        User studentUser = userWithCompletedCourses();

        boolean result = invokePrivateBoolean("prerequisiteConflicts", new Class[]{User.class, List.class}, studentUser, List.of(requested));

        assertFalse(result);
    }

    private boolean invokePrivateBoolean(String methodName, Class<?>[] parameterTypes, Object... args) throws Exception {
        Method method = AddCourseController.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (boolean) method.invoke(controller, args);
    }

    private User userWithCompletedCourses(Course... completedCourses) {
        Student student = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "12345678",
                "jd123",
                "password",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(),
                List.of(completedCourses),
                null,
                List.of()
        );
        return new User(student.student_id(), student.name(), student);
    }

    private Course course(
            String code,
            String day,
            LocalDate startDate,
            LocalDate endDate,
            String startTime,
            String endTime,
            List<Course> prerequisites
    ) {
        return new Course(
                "CS",
                "CS",
                code,
                "Title " + code,
                College.COLLEGE_OF_COMPUTING,
                "10000",
                "A",
                3,
                "Main",
                false,
                "UG",
                20,
                30,
                false,
                startDate,
                endDate,
                startTime,
                endTime,
                day,
                "Building",
                "101",
                new Admin("Prof X"),
                "English",
                day + " " + startTime + "-" + endTime,
                prerequisites,
                List.of()
        );
    }
}
