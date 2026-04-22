package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class StudentInfoDialogController implements ContextAware {
    private AppContext context;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private Label fullNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label surnameLabel;
    @FXML private Label studentIdLabel;
    @FXML private Label studentEidLabel;
    @FXML private Label collegeLabel;
    @FXML private Label degreeLabel;
    @FXML private Label statusLabel;
    @FXML private Label programLabel;
    @FXML private Label locationLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label enrolledCoursesLabel;
    @FXML private Label completedCoursesLabel;
    @FXML private Button closeBtn;

    public void setStudent(Student student) {
        if (student == null) {
            return;
        }

        fullNameLabel.setText(safe(student.name()));
        firstNameLabel.setText(safe(student.first_name()));
        surnameLabel.setText(safe(student.surname()));
        studentIdLabel.setText(safe(student.student_id()));
        studentEidLabel.setText(safe(student.student_eid()));
        collegeLabel.setText(student.college() == null ? "-" : student.college().name());
        degreeLabel.setText(safe(student.degree()));
        statusLabel.setText(student.status() == null ? "-" : student.status().name());
        programLabel.setText(student.program() == null ? "-" : student.program().toString());
        locationLabel.setText(safe(student.location()));
        startDateLabel.setText(student.start_date() == null ? "-" : student.start_date().format(DATE_FORMAT));
        endDateLabel.setText(student.end_date() == null ? "-" : student.end_date().format(DATE_FORMAT));
        enrolledCoursesLabel.setText(formatCourses(student.enrolled_courses()));
        completedCoursesLabel.setText(formatCourses(student.completed_courses()));
    }

    private String formatCourses(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return "-";
        }

        return courses.stream()
                .map(course -> {
                    if (course == null) return "-";
                    try {
                        return course.title();
                    } catch (Exception e) {
                        return course.toString();
                    }
                })
                .collect(Collectors.joining(", "));
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    @FXML
    private void handleClose() {
        closeBtn.getScene().getWindow().hide();
    }

    @FXML
    private void showConflict() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText("Trying to display student information as a different user");
        alert.showAndWait();
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        if (context.getCurrentUser().role() instanceof Student) {
            setStudent((Student) context.getCurrentUser().role());
        } else {
            showConflict();
        }
    }
}