package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Admin;
import CourseRegisterUI.models.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

public class AdminInfoDialogController implements ContextAware {
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label eidLabel;
    @FXML
    private Label coursesLabel;
    @FXML
    private Button closeBtn;
    private AppContext context;

    public void setAdmin(Admin admin) {
        if (admin == null) {
            return;
        }

        fullNameLabel.setText(safe(admin.name()));
        idLabel.setText(safe(admin.id()));
        eidLabel.setText(safe(admin.eidValue()));
        emailLabel.setText(admin.email());
        phoneLabel.setText(safe(admin.phone()));
        coursesLabel.setText(formatCourses(admin.courses()));
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
        if (context.getCurrentUser().role() instanceof Admin) {
            setAdmin((Admin) context.getCurrentUser().role());
        } else {
            showConflict();
        }
    }
}
