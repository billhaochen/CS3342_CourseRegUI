package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CourseCreateController implements ContextAware {
    @FXML private Button cancelButton;
    @FXML private Button okButton;
    @FXML private TextField academicUnitField;
    @FXML private TextField subjectField;
    @FXML private TextField courseCodeField;
    @FXML private TextField titleField;
    @FXML private ChoiceBox<College> collegeChoiceBox;
    @FXML private TextField crnField;
    @FXML private TextField sectionField;
    @FXML private TextField creditField;
    @FXML private ChoiceBox<String> campusChoiceBox;
    @FXML private CheckBox webEnabledCheckBox;
    @FXML private ChoiceBox<String> levelChoiceBox;
    @FXML private TextField availabilityField;
    @FXML private TextField capField;
    @FXML private CheckBox waitlistAvailableCheckBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TextField dayField;
    @FXML private ChoiceBox<String> buildingChoiceBox;
    @FXML private TextField roomField;
    @FXML private TextField instructorIdField;
    @FXML private ChoiceBox<String> mediumChoiceBox;
    @FXML private TextField meetingTimeField;
    @FXML private TextArea prerequisitesArea;
    @FXML private TextArea waitlistArea;

    private AppContext context;
    private Course course;

    @FXML
    private void initialize() {
        // populate enum-like choice boxes
        collegeChoiceBox.getItems().setAll(College.values());

        // TODO: replace these placeholder lists with your real enums
        campusChoiceBox.getItems().setAll("MAIN", "CITY", "ONLINE");
        levelChoiceBox.getItems().setAll("UG", "PG");
        buildingChoiceBox.getItems().setAll("ENG", "SCI", "BUS");
        mediumChoiceBox.getItems().setAll("IN_PERSON", "ONLINE", "HYBRID");
        okButton.getStyleClass().add("btn-submit");
        cancelButton.getStyleClass().add("link-ghost");
    }

    // --------------------------
    // Builders
    // --------------------------

    /**
     * Full builder that uses all fields.
     */
    public Course buildEditedCourse() {
        String academicUnit = academicUnitField.getText();
        String subject = subjectField.getText();
        String courseCode = courseCodeField.getText();
        String title = titleField.getText();
        College college = collegeChoiceBox.getValue();
        String crn = crnField.getText();
        String section = sectionField.getText();
        Integer credit = parseIntegerOrNull(creditField.getText());
        String campus = campusChoiceBox.getValue();
        Boolean webEnabled = webEnabledCheckBox.isSelected();
        String level = levelChoiceBox.getValue();
        Integer availability = parseIntegerOrNull(availabilityField.getText());
        Integer cap = parseIntegerOrNull(capField.getText());
        Boolean waitlistAvailable = waitlistAvailableCheckBox.isSelected();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String day = dayField.getText();
        String building = buildingChoiceBox.getValue();
        String room = roomField.getText();
        String instructorId = instructorIdField.getText();
        String medium = mediumChoiceBox.getValue();
        String meetingTime = meetingTimeField.getText();

        // For now: prerequisites not resolved to real Course objects
        List<Course> prerequisites = new ArrayList<>();
        // You could look up actual Course objects by code/CRN here.

        List<String> waitlist = parseCommaSeparated(waitlistArea.getText());

        course = new Course(
                academicUnit,
                subject,
                courseCode,
                title,
                college,
                crn,
                section,
                credit,
                campus,
                webEnabled,
                level,
                availability,
                cap,
                waitlistAvailable,
                startDate,
                endDate,
                startTime,
                endTime,
                day,
                building,
                room,
                instructorId,
                medium,
                meetingTime,
                prerequisites,
                waitlist
        );

        return course;
    }

    /**
     * Minimal builder using only the important fields, with defaults for the rest.
     */
    public Course buildMinimalCourse() {
        String error = validateImportantFields();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        String academicUnit = academicUnitField.getText();
        String subject = subjectField.getText();
        String courseCode = courseCodeField.getText();
        String title = titleField.getText();
        College college = collegeChoiceBox.getValue();
        Integer credit = parseIntegerOrNull(creditField.getText());
        String campus = campusChoiceBox.getValue();
        String level = levelChoiceBox.getValue();
        String day = dayField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        course = Course.factoryMinimal(
                academicUnit,
                subject,
                courseCode,
                title,
                college,
                credit,
                campus,
                level,
                startDate,
                endDate,
                startTime,
                endTime,
                day
        );
        return course;
    }

    // --------------------------
    // Validation helpers
    // --------------------------

    private Integer parseIntegerOrNull(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null; // or throw / show error
        }
    }

    private List<String> parseCommaSeparated(String text) {
        if (text == null || text.isBlank()) return new ArrayList<>();
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    /**
     * Validates the minimal set of fields that must be present.
     * Returns null if everything is OK, otherwise a human-readable error.
     */
    private String validateImportantFields() {
        if (isBlank(titleField.getText())) {
            return "Title is required.";
        }
        if (isBlank(courseCodeField.getText())) {
            return "Course code is required.";
        }
        if (collegeChoiceBox.getValue() == null) {
            return "College is required.";
        }
        Integer credit = parseIntegerOrNull(creditField.getText());
        if (credit == null) {
            return "Credit must be a valid integer.";
        }
        if (isBlank(campusChoiceBox.getValue())) {
            return "Campus is required.";
        }
        if (isBlank(levelChoiceBox.getValue())) {
            return "Level is required.";
        }
        if (isBlank(dayField.getText())) {
            return "Day is required.";
        }
        if (isBlank(startTimeField.getText()) || isBlank(endTimeField.getText())) {
            return "Start time and end time are required.";
        }
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            return "Start date and end date are required.";
        }
        return null; // all good
    }

    // --------------------------
    // Accessors / Context
    // --------------------------

    public Course getCourse() {
        return course;
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    // --------------------------
    // UI handlers
    // --------------------------

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) crnField.getScene().getWindow();
        stage.close();
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successfully Created Course");
        Stage owner = (Stage) crnField.getScene().getWindow();
        alert.initOwner(owner);
        alert.showAndWait();
    }

    @FXML
    private void showConflict(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Course Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage owner = (Stage) crnField.getScene().getWindow();
        alert.initOwner(owner);
        alert.showAndWait();
    }

    @FXML
    private void handleOk() {
        try {
            // Decide which builder to use:
            // If all important fields are present AND the advanced fields are mostly empty,
            // you could use minimal; otherwise use full. For now, we try minimal first.
            Course new_course;
            String importantError = validateImportantFields();
            if (importantError == null) {
                // Important fields satisfied: build minimal course
                new_course = buildMinimalCourse();
            } else {
                // Fall back to full builder to preserve all inputs
                new_course = buildEditedCourse();
            }

            context.createCourse(new_course);
            MasterJSONBuilder.writeLocalToMaster(context.exportContext());
            showSuccessDialog();
            Stage stage = (Stage) crnField.getScene().getWindow();
            stage.close();
        } catch (IllegalArgumentException ex) {
            // From validateImportantFields() / buildMinimalCourse()
            showConflict(ex.getMessage());
        } catch (IOException e) {
            showConflict("Error saving course: " + e.getMessage());
        }
    }
}