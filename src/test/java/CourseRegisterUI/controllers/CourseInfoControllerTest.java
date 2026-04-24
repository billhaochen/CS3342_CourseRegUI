package CourseRegisterUI.controllers;

import CourseRegisterUI.models.Admin;
import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseInfoControllerTest {

    private CourseInfoController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CourseInfoController();
        setField("courseName", new Label());
        setField("courseCode", new Label());
        setField("courseCRN", new Label());
        setField("professorName", new Label());
        setField("departmentName", new Label());
        setField("courseLevel", new Label());
        setField("courseCredits", new Label());
        setField("courseWebEnabled", new Label());
        setField("courseTable", new TableView<>());
        setField("subjectColumn", new TableColumn<>());
        setField("courseNumberColumn", new TableColumn<>());
        setField("titleColumn", new TableColumn<>());
        setField("academicUnitColumn", new TableColumn<>());
        setField("courseColumn", new TableColumn<>());
        setField("courseCalendarGrid", new javafx.scene.layout.GridPane());
        setField("mainScroll", new javafx.scene.control.ScrollPane());
        setField("weekTitle", new Label());
        setField("prevWeek", new Button());
        setField("nextWeek", new Button());
        setField("todayBtn", new Button());
        setField("joinWaitlistButton", new Button());
        setField("unregisterButton", new Button());
    }

    @Test
    void setCourseInfo_populatesLabelsForCompleteCourse() throws Exception {
        Course prerequisite = course("MATH1010", "Calculus I", null, false, 3, List.of());
        Course course = course("CS2200", "Software Engineering", new Admin("Prof X"), true, 4, List.of(prerequisite));

        controller.setCourseInfo(course);

        assertEquals("Software Engineering", labelText("courseName"));
        assertEquals("CS2200", labelText("courseCode"));
        assertEquals("12345", labelText("courseCRN"));
        assertEquals("Prof X", labelText("professorName"));
        assertEquals("CS", labelText("departmentName"));
        assertEquals("UG", labelText("courseLevel"));
        assertEquals("4", labelText("courseCredits"));
        assertEquals("Yes", labelText("courseWebEnabled"));
        assertEquals(1, table().getItems().size());
    }

    @Test
    void setCourseInfo_usesFallbackValuesForMissingData() throws Exception {
        Course course = new Course(
                "CS",
                null,
                null,
                null,
                College.COLLEGE_OF_COMPUTING,
                null,
                "A",
                null,
                "Main",
                null,
                null,
                20,
                30,
                false,
                null,
                null,
                "09:00",
                "10:00",
                "Mon",
                "Building",
                "101",
                null,
                "English",
                "Mon 09:00-10:00",
                List.of(),
                List.of()
        );

        controller.setCourseInfo(course);

        assertEquals("Unknown Title", labelText("courseName"));
        assertEquals("Unknown Code", labelText("courseCode"));
        assertEquals("N/A", labelText("courseCRN"));
        assertEquals("TBD", labelText("professorName"));
        assertEquals("N/A", labelText("departmentName"));
        assertEquals("N/A", labelText("courseLevel"));
        assertEquals("0", labelText("courseCredits"));
        assertEquals("No", labelText("courseWebEnabled"));
        assertTrue(table().getItems().isEmpty());
    }

    @Test
    void setCourseInfo_doesNothingWhenCourseIsNull() throws Exception {
        label("courseName").setText("unchanged");

        controller.setCourseInfo(null);

        assertEquals("unchanged", label("courseName").getText());
    }

    private Course course(String code, String title, Admin admin, boolean webEnabled, Integer credits, List<Course> prerequisites) {
        return new Course(
                "CS",
                "CS",
                code,
                title,
                College.COLLEGE_OF_COMPUTING,
                "12345",
                "A",
                credits,
                "Main",
                webEnabled,
                "UG",
                20,
                30,
                false,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 5, 12),
                "09:00",
                "10:00",
                "Mon",
                "Building",
                "101",
                admin,
                "English",
                "Mon 09:00-10:00",
                prerequisites,
                List.of()
        );
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = CourseInfoController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private Label label(String fieldName) throws Exception {
        Field field = CourseInfoController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Label) field.get(controller);
    }

    private String labelText(String fieldName) throws Exception {
        return label(fieldName).getText();
    }

    @SuppressWarnings("unchecked")
    private TableView<Object> table() throws Exception {
        Field field = CourseInfoController.class.getDeclaredField("courseTable");
        field.setAccessible(true);
        return (TableView<Object>) field.get(controller);
    }
}
