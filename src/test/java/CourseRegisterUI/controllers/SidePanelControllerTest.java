package CourseRegisterUI.controllers;

import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SidePanelControllerTest {

    private SidePanelController controller;
    private CourseRow courseRow;

    @BeforeEach
    void setUp() throws Exception {
        controller = new SidePanelController();
        setField("search_by_text_field", new TextField());
        setField("academicUnitTextField", new TextField());
        setField("academicUnitComboBox", new ComboBox<String>());
        setField("creditCheckBox", new CheckBox());
        setField("creditTextField", new TextField());
        setField("exactCreditButton", new RadioButton());
        setField("programCheckBox", new CheckBox());
        setField("programComboBox", new ComboBox<College>());
        setField("mediumCheckBox", new CheckBox());
        setField("mediumComboBox", new ComboBox<String>());

        courseRow = new CourseRow(course());
    }

    @Test
    void matchesAllFilters_returnsTrue_whenNoFiltersAreActive() throws Exception {
        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesSearchTextAgainstTitle() throws Exception {
        textField("search_by_text_field").setText("software");

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenSearchTextDoesNotMatch() throws Exception {
        textField("search_by_text_field").setText("biology");

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesAcademicUnitDropdownIgnoringCase() throws Exception {
        comboBoxString("academicUnitComboBox").setValue("cs");

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenAcademicUnitDropdownDiffers() throws Exception {
        comboBoxString("academicUnitComboBox").setValue("MATH");

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenCreditFilterSelectedButBlank() throws Exception {
        checkBox("creditCheckBox").setSelected(true);
        textField("creditTextField").setText("");

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesExactCreditValue() throws Exception {
        checkBox("creditCheckBox").setSelected(true);
        textField("creditTextField").setText("3");
        radioButton("exactCreditButton").setSelected(true);

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenExactCreditDoesNotMatch() throws Exception {
        checkBox("creditCheckBox").setSelected(true);
        textField("creditTextField").setText("4");
        radioButton("exactCreditButton").setSelected(true);

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesMinimumCreditThreshold() throws Exception {
        checkBox("creditCheckBox").setSelected(true);
        textField("creditTextField").setText("2");
        radioButton("exactCreditButton").setSelected(false);

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_forInvalidCreditNumber() throws Exception {
        checkBox("creditCheckBox").setSelected(true);
        textField("creditTextField").setText("three");

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesSelectedCollegeWhenProgramFilterEnabled() throws Exception {
        checkBox("programCheckBox").setSelected(true);
        comboBoxCollege("programComboBox").setValue(College.COLLEGE_OF_COMPUTING);

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenSelectedCollegeDiffers() throws Exception {
        checkBox("programCheckBox").setSelected(true);
        comboBoxCollege("programComboBox").setValue(College.COLLEGE_OF_ENGINEERING);

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_matchesMediumIgnoringCase() throws Exception {
        checkBox("mediumCheckBox").setSelected(true);
        comboBoxString("mediumComboBox").setValue("english");

        assertTrue(invokeMatchesAllFilters(courseRow));
    }

    @Test
    void matchesAllFilters_returnsFalse_whenMediumDiffers() throws Exception {
        checkBox("mediumCheckBox").setSelected(true);
        comboBoxString("mediumComboBox").setValue("Chinese");

        assertFalse(invokeMatchesAllFilters(courseRow));
    }

    private boolean invokeMatchesAllFilters(CourseRow row) throws Exception {
        Method method = SidePanelController.class.getDeclaredMethod("matchesAllFilters", CourseRow.class);
        method.setAccessible(true);
        return (boolean) method.invoke(controller, row);
    }

    private Course course() {
        return new Course(
                "CS",
                "CS",
                "2200",
                "Software Engineering",
                College.COLLEGE_OF_COMPUTING,
                "12345",
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
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private TextField textField(String fieldName) throws Exception {
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (TextField) field.get(controller);
    }

    @SuppressWarnings("unchecked")
    private ComboBox<String> comboBoxString(String fieldName) throws Exception {
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (ComboBox<String>) field.get(controller);
    }

    @SuppressWarnings("unchecked")
    private ComboBox<College> comboBoxCollege(String fieldName) throws Exception {
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (ComboBox<College>) field.get(controller);
    }

    private CheckBox checkBox(String fieldName) throws Exception {
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (CheckBox) field.get(controller);
    }

    private RadioButton radioButton(String fieldName) throws Exception {
        Field field = SidePanelController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (RadioButton) field.get(controller);
    }
}
