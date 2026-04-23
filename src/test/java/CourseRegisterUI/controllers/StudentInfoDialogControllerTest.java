package CourseRegisterUI.controllers;

import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentInfoDialogControllerTest {

    private StudentInfoDialogController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new StudentInfoDialogController();
        setField("fullNameLabel", new javafx.scene.control.Label());
        setField("firstNameLabel", new javafx.scene.control.Label());
        setField("surnameLabel", new javafx.scene.control.Label());
        setField("studentIdLabel", new javafx.scene.control.Label());
        setField("studentEidLabel", new javafx.scene.control.Label());
        setField("collegeLabel", new javafx.scene.control.Label());
        setField("degreeLabel", new javafx.scene.control.Label());
        setField("statusLabel", new javafx.scene.control.Label());
        setField("programLabel", new javafx.scene.control.Label());
        setField("locationLabel", new javafx.scene.control.Label());
        setField("startDateLabel", new javafx.scene.control.Label());
        setField("endDateLabel", new javafx.scene.control.Label());
        setField("enrolledCoursesLabel", new javafx.scene.control.Label());
        setField("completedCoursesLabel", new javafx.scene.control.Label());
    }

    @Test
    void setStudent_populatesLabelsForTypicalStudent() throws Exception {
        Course enrolled = course("CS1010", "Intro to CS");
        Course completed = course("MATH1010", "Calculus I");
        Student student = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "12345678",
                "jd123",
                "secret",
                College.COLLEGE_OF_COMPUTING,
                "Bachelor's Degree",
                null,
                null,
                "Main Campus",
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2029, 5, 20),
                List.of(enrolled),
                List.of(completed),
                null
        );

        controller.setStudent(student);

        assertEquals("Jane Doe", labelText("fullNameLabel"));
        assertEquals("Jane", labelText("firstNameLabel"));
        assertEquals("Doe", labelText("surnameLabel"));
        assertEquals("12345678", labelText("studentIdLabel"));
        assertEquals("jd123", labelText("studentEidLabel"));
        assertEquals("COLLEGE_OF_COMPUTING", labelText("collegeLabel"));
        assertEquals("Bachelor's Degree", labelText("degreeLabel"));
        assertEquals("-", labelText("statusLabel"));
        assertEquals("-", labelText("programLabel"));
        assertEquals("Main Campus", labelText("locationLabel"));
        assertEquals("2026-01-12", labelText("startDateLabel"));
        assertEquals("2029-05-20", labelText("endDateLabel"));
        assertEquals("Intro to CS", labelText("enrolledCoursesLabel"));
        assertEquals("Calculus I", labelText("completedCoursesLabel"));
    }

    @Test
    void setStudent_usesDashForNullBlankAndEmptyValues() throws Exception {
        Student student = new Student(
                " ",
                null,
                "",
                null,
                null,
                "secret",
                null,
                " ",
                null,
                null,
                null,
                null,
                null,
                List.of(),
                null,
                null
        );

        controller.setStudent(student);

        assertEquals("-", labelText("fullNameLabel"));
        assertEquals("-", labelText("firstNameLabel"));
        assertEquals("-", labelText("surnameLabel"));
        assertEquals("-", labelText("studentIdLabel"));
        assertEquals("-", labelText("studentEidLabel"));
        assertEquals("-", labelText("collegeLabel"));
        assertEquals("-", labelText("degreeLabel"));
        assertEquals("-", labelText("statusLabel"));
        assertEquals("-", labelText("programLabel"));
        assertEquals("-", labelText("locationLabel"));
        assertEquals("-", labelText("startDateLabel"));
        assertEquals("-", labelText("endDateLabel"));
        assertEquals("-", labelText("enrolledCoursesLabel"));
        assertEquals("-", labelText("completedCoursesLabel"));
    }

    @Test
    void setStudent_handlesNullCourseEntriesInCourseLists() throws Exception {
        Student student = new Student(
                "Jane Doe",
                "Doe",
                "Jane",
                "12345678",
                "jd123",
                "secret",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of((Course) null),
                List.of((Course) null),
                null
        );

        controller.setStudent(student);

        assertEquals("-", labelText("enrolledCoursesLabel"));
        assertEquals("-", labelText("completedCoursesLabel"));
    }

    @Test
    void setStudent_doesNothingWhenStudentIsNull() throws Exception {
        javafx.scene.control.Label fullName = getLabel("fullNameLabel");
        fullName.setText("unchanged");

        controller.setStudent(null);

        assertEquals("unchanged", fullName.getText());
    }

    private Course course(String code, String title) {
        return new Course(
                "CS",
                "CS",
                code,
                title,
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
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 5, 12),
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
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = StudentInfoDialogController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private javafx.scene.control.Label getLabel(String fieldName) throws Exception {
        Field field = StudentInfoDialogController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (javafx.scene.control.Label) field.get(controller);
    }

    private String labelText(String fieldName) throws Exception {
        return getLabel(fieldName).getText();
    }
}
