package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class CreateAccountController implements ContextAware {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> levelComboBox;
    @FXML
    private ComboBox<String> programComboBox;
    @FXML
    private ComboBox<String> completedRequisitesComboBox;
    @FXML
    private ComboBox<String> collegeComboBox;
    @FXML
    private ComboBox<String> majorComboBox;

    @FXML
    private Button cancelBtn;
    @FXML
    private Button submitBtn;

    private AppContext context;
    private CourseController mainController;

    @FXML
    public void initialize() {
        // Style setup
        setupButtons();

        // Populate dropdowns
        populateCombos();

        // Event handlers
        setupEventHandlers();
    }

    private void setupButtons() {
        cancelBtn.getStyleClass().add("outline-button");
        submitBtn.getStyleClass().add("primary-button");

        cancelBtn.setOnAction(e -> cancelBtn.getScene().getWindow().hide());
        submitBtn.setOnAction(e -> handleSubmit());
    }

    private void populateCombos() {
        levelComboBox.setItems(FXCollections.observableArrayList(
                "Freshman", "Sophomore", "Junior", "Senior"
        ));

        collegeComboBox.setItems(FXCollections.observableArrayList(
                "COLLEGE_OF_BUSINESS", "COLLEGE_OF_COMPUTING",
                "COLLEGE_OF_LIFE_SCIENCES", "COLLEGE_OF_LIBERAL_ARTS"
        ));

        // Dynamic population for others from AppContext
        // programComboBox.setItems(context.getPrograms());
        // majorComboBox.setItems(context.getMajors());
    }

    private void setupEventHandlers() {
        // Real-time validation
        usernameField.textProperty().addListener((obs, old, text) -> validateForm());
        passwordField.textProperty().addListener((obs, old, text) -> validateForm());
        studentIdField.textProperty().addListener((obs, old, text) -> validateForm());

        // Focus styling
        setupFieldFocus(usernameField);
        setupFieldFocus(studentIdField);
    }

    private void setupFieldFocus(TextField field) {
        field.focusedProperty().addListener((obs, old, focused) -> {
            if (focused) {
                field.getStyleClass().add("focused");
            } else {
                field.getStyleClass().remove("focused");
            }
        });
    }

    private void validateForm() {
        boolean isValid = !usernameField.getText().isBlank() &&
                !passwordField.getText().isBlank() &&
                !studentIdField.getText().isBlank();

        submitBtn.setDisable(!isValid);
    }

    private void createStudent() {
        String full_name = usernameField.getText();
        String first_name = full_name.substring(0, full_name.indexOf(" "));
        String surname = full_name.substring(full_name.indexOf(" "));

        String password = passwordField.getText();
        String student_id = studentIdField.getText();
        String email = emailField.getText();
        String phone_number = phoneField.getText();
        String c2 = levelComboBox.getValue();
        College college = College.valueOf(collegeComboBox.getValue());
//        String c2 = majorComboBox.getValue();
//        String c2 = programComboBox.getValue();

//        String name,
//        String surname,
//        String first_name,
//        String student_id,
//        String student_eid,
//        String password,
//        College college,
//        String degree,
//        Status status,
//        Program program,
//        String location,
//        LocalDate start_date,
//        LocalDate end_date,
//        List<Course> enrolled_courses,
//        List<Course> completed_courses
//        validateAccount(new User())
//        return new Student();
    }

    private void handleSubmit() {

        if (!this.context.isAdmin()) {
            // Execute Student Account Creation

        } else {
            // Execute Teacher Account Creation
        }
        showSuccessDialog();
        cancelBtn.fire();  // Close dialog
    }

    private boolean validateAccount(User account) {
        // Add your validation logic
        return true;
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account created successfully!");
        alert.showAndWait();
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }
}