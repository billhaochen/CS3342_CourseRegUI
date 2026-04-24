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

public class CourseEditDialogController implements ContextAware {

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
    private Course originalCourse;
    private Course editedCourse;

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

    public void setCourse(Course course) {
        this.originalCourse = course;

        if (course == null) {
            return;
        }

        academicUnitField.setText(course.academic_unit());
        subjectField.setText(course.subject());
        courseCodeField.setText(course.course_code());
        titleField.setText(course.title());
        collegeChoiceBox.setValue(course.college());
        crnField.setText(course.crn());
        sectionField.setText(course.section());
        creditField.setText(course.credit() == null ? "" : course.credit().toString());
        campusChoiceBox.setValue(course.campus());
        webEnabledCheckBox.setSelected(Boolean.TRUE.equals(course.web_enabled()));
        levelChoiceBox.setValue(course.level());
        availabilityField.setText(course.availability() == null ? "" : course.availability().toString());
        capField.setText(course.cap() == null ? "" : course.cap().toString());
        waitlistAvailableCheckBox.setSelected(Boolean.TRUE.equals(course.waitlist_available()));
        startDatePicker.setValue(course.start_date());
        endDatePicker.setValue(course.end_date());
        startTimeField.setText(course.start_time());
        endTimeField.setText(course.end_time());
        dayField.setText(course.day());
        buildingChoiceBox.setValue(course.building());
        roomField.setText(course.room());
        instructorIdField.setText(course.instructor_id());
        mediumChoiceBox.setValue(course.medium());
        meetingTimeField.setText(course.meeting_time());

        // prerequisites as whatever string representation you want
        if (course.prerequisites() != null && !course.prerequisites().isEmpty()) {
            String prereqText = course.prerequisites().stream()
                    .map(Course::course_code) // or crn(), or title(), whatever you use
                    .collect(Collectors.joining(", "));
            prerequisitesArea.setText(prereqText);
        }

        if (course.waitlist() != null && !course.waitlist().isEmpty()) {
            waitlistArea.setText(String.join(", ", course.waitlist()));
        }
    }

    /**
     * Call this from your dialog result converter after OK is pressed.
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

        editedCourse = new Course(
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

        return editedCourse;
    }

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

    public Course getEditedCourse() {
        return editedCourse;
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) crnField.getScene().getWindow();
        stage.close();
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successfully Edited Course");
        alert.show();
    }

    @FXML
    private void showConflict(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleOk() {
        try {
            Course new_course = buildEditedCourse();
            context.updateCourse(originalCourse, new_course);
            MasterJSONBuilder.writeLocalToMaster(context.exportContext());
            context.loadInitialData();
            showSuccessDialog();
            Stage stage = (Stage) crnField.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            showConflict("Error editing course");
        }
    }
}