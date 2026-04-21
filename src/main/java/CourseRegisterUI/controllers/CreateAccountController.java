package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CreateAccountController implements ContextAware {

    @FXML
    private VBox createAccountBox;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
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
    private CheckComboBox<String> completedRequisitesComboBox;
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
        Platform.runLater(() -> createAccountBox.requestFocus());
        createAccountBox.setOnMouseClicked(e -> createAccountBox.requestFocus());
        createAccountBox.getStyleClass().add("modern-dialog");
        usernameField.getStyleClass().add("text-field-custom");
        passwordField.getStyleClass().add("text-field-custom");
        studentIdField.getStyleClass().add("text-field-custom");
        phoneField.getStyleClass().add("text-field-custom");
        emailField.getStyleClass().add("text-field-custom");
        submitBtn.getStyleClass().add("btn-submit");
        cancelBtn.getStyleClass().add("link-ghost");
//        idWarningLabel.getStyleClass().add("idWarningClass");
        // Event handlers
        setupEventHandlers();
    }

    private void populateCombos() {
        levelComboBox.setItems(FXCollections.observableArrayList(
                "Freshman", "Sophomore", "Junior", "Senior"
        ));

        collegeComboBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(College.values())
                        .map(Enum::toString)
                        .toList()
        ));

        // Dynamic population for others from AppContext
         programComboBox.setItems(FXCollections.observableArrayList("LOCAL", "INTERNATIONAL","EXCHANGE"));
         majorComboBox.setItems(FXCollections.observableArrayList(
                 Arrays.stream(Major.values())
                         .map(Enum::toString)
                         .toList()
         ));
         completedRequisitesComboBox.getItems().setAll(FXCollections.observableArrayList(
                 context.getCourseRepository().courses().stream().map(Course::title).toList()
         ));
    }

    private void setupEventHandlers() {
        // Real-time validation
        usernameField.textProperty().addListener((obs, old, text) -> validateForm());
        passwordField.textProperty().addListener((obs, old, text) -> validateForm());
        studentIdField.textProperty().addListener((obs, old, text) -> validateForm());
        completedRequisitesComboBox.getCheckModel().getCheckedItems().addListener((javafx.collections.ListChangeListener<String>) change -> {
            int count = completedRequisitesComboBox.getCheckModel().getCheckedItems().size();
            if (count == 0) {
                completedRequisitesComboBox.setTitle("Completed Courses");
            } else {
                completedRequisitesComboBox.setTitle(count + " course(s) selected");
            }
        });
        completedRequisitesComboBox.setTitle("Completed Courses");
        completedRequisitesComboBox.setShowCheckedCount(false);

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

    @FXML
    private void handleSubmit() {
        List<String> selectedCourses = List.copyOf(
                completedRequisitesComboBox.getCheckModel().getCheckedItems()
        );

        if (!this.context.isAdmin()) {
            // Execute Student Account Creation

        } else {
            // Execute Teacher Account Creation
        }
        showSuccessDialog();
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private boolean validateAccount(User account) {
        // Add your validation logic
        return true;
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account created successfully!");
        alert.show();
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        populateCombos();
    }

    @FXML
    private void handleCancel(){
        // by default the user is set to signed out
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}