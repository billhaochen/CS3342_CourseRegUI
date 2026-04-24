package CourseRegisterUI.util;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppContextTest {

    @Test
    void constructor_initializesSignedOutCurrentUserAndFilteredList() {
        AppContext context = new AppContext();

        assertNotNull(context.getCurrentUser());
        assertTrue(context.getCurrentUser().role() instanceof SignedOut);
        assertNotNull(context.getFilteredCourseRows());
    }

    @Test
    void setCurrentUser_withUserObject_replacesCurrentUserReference() {
        AppContext context = new AppContext();
        User user = new User("1", "Jane Doe", Student.factoryCreate("Jane Doe", "Doe", "Jane", "S12345678", "janedoe1", "secret"));

        context.setCurrentUser(user);

        assertSame(user, context.getCurrentUser());
    }

    @Test
    void addNewUser_addsUserToExportedContext() {
        AppContext context = new AppContext();
        User user = new User("1", "Jane Doe", Student.factoryCreate("Jane Doe", "Doe", "Jane", "S12345678", "janedoe1", "secret"));

        context.addNewUser(user);

        assertTrue(context.exportContext().users().contains(user));
    }

    @Test
    void clearSelectedCourses_emptiesSelectedCoursesList() {
        AppContext context = new AppContext();
        context.getSelectedCourses().add(ExampleJSONBuilder.buildSampleCourses().get(0));

        context.clearSelectedCourses();

        assertTrue(context.getSelectedCourses().isEmpty());
    }

    @Test
    void isAdmin_flag_canBeSetAndRead() {
        AppContext context = new AppContext();

        context.setIsAdmin(true);

        assertTrue(context.isAdmin());
    }

    @Test
    void registerCourses_appendsSelectedCoursesToStudentEnrollment() {
        AppContext context = new AppContext();
        Course existing = ExampleJSONBuilder.buildSampleCourses().get(0);
        Course selected = ExampleJSONBuilder.buildSampleCourses().get(1);
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
                new ArrayList<>(List.of(existing)),
                List.of(),
                null
        );
        User user = new User("1", "Jane Doe", student);
        context.setCurrentUser(user);
        context.addNewUser(user);
        context.getSelectedCourses().add(selected);

        context.registerCourses();

        User updated = context.getCurrentUser();
        Student updatedStudent = (Student) updated.role();
        assertEquals(2, updatedStudent.enrolled_courses().size());
        assertTrue(updatedStudent.enrolled_courses().stream().anyMatch(c -> c.title().equals(existing.title())));
        assertTrue(updatedStudent.enrolled_courses().stream().anyMatch(c -> c.title().equals(selected.title())));
    }
}
