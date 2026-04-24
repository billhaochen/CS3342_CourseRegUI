package CourseRegisterUI.util;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExampleJSONBuilderTest {

    @Test
    void buildSampleCourses_returnsExpectedSampleData() {
        List<Course> courses = ExampleJSONBuilder.buildSampleCourses();

        assertEquals(4, courses.size());
        assertEquals("Corporate Accounting I", courses.get(0).title());
        assertEquals("English for Business Communication", courses.get(3).title());
        assertEquals(1, courses.get(3).prerequisites().size());
        assertEquals("Corporate Accounting I", courses.get(3).prerequisites().get(0).title());
    }

    @Test
    void buildSampleStudents_returnsExpectedUsers() {
        List<User> users = ExampleJSONBuilder.buildSampleUsers();

        assertEquals(4, users.size());
//        assertTrue(users.stream().allMatch(u -> u.role() instanceof Student));
        assertEquals("John Doe", users.get(0).name());
        assertEquals("Jane Doe", users.get(1).name());
        assertEquals("Frank Ocean", users.get(2).name());
    }

    @Test
    void writeExampleCourseFile_writesReadableJson(@TempDir Path tempDir) {
        File file = tempDir.resolve("exampleCourse.json").toFile();

        assertDoesNotThrow(() -> ExampleJSONBuilder.writeExampleCourseFile(file));
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void writeExampleStudentFile_writesReadableJson(@TempDir Path tempDir) {
        File file = tempDir.resolve("exampleStudent.json").toFile();

        assertDoesNotThrow(() -> ExampleJSONBuilder.writeExampleUserFile(file));
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
