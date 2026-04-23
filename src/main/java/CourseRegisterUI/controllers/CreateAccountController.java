package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.MasterJSONBuilder;
import CourseRegisterUI.util.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static CourseRegisterUI.ComponentLoader.showErrorAlert;
import static CourseRegisterUI.ComponentLoader.showSuccessAlert;

public class CreateAccountController implements ContextAware {

    @FXML
    private VBox createAccountBox;

    @FXML
    private TextField nameField;
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
    private CheckComboBox<String> completedRequisitesComboBox;
    @FXML
    private ComboBox<String> collegeComboBox;
    @FXML
    private ComboBox<String> majorComboBox;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> degreeComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private Button cancelBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private GridPane studentForm;
    @FXML
    private GridPane adminForm;

    @FXML
    private TextField adminNameField;
    @FXML
    private TextField adminPasswordField;
    @FXML
    private TextField adminIdField;
    @FXML
    private TextField adminEmailField;
    @FXML
    private TextField adminPhoneField;
    @FXML
    private ComboBox<String> titleComboBox;


    private AppContext context;
    private CourseController mainController;

    @FXML
    public void initialize() {
        Platform.runLater(() -> createAccountBox.requestFocus());
        createAccountBox.setOnMouseClicked(e -> createAccountBox.requestFocus());
        createAccountBox.getStyleClass().add("modern-dialog");
        nameField.getStyleClass().add("text-field-custom");
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
        List<String> colleges = new ArrayList<>(Arrays.stream(College.values())
                .map(Enum::toString)
                .toList());
        colleges.addFirst(null);

        List<String> majors = new ArrayList<>(Arrays.stream(Major.values())
                .map(Enum::toString)
                .toList());
        majors.addFirst(null);

        List<String> statuses = new ArrayList<>(Arrays.stream(Status.values())
                .map(Enum::toString)
                .toList());
        majors.addFirst(null);


        levelComboBox.setItems(FXCollections.observableArrayList(
                null, "Freshman", "Sophomore", "Junior", "Senior"
        ));

        collegeComboBox.setItems(FXCollections.observableArrayList(
                colleges
        ));

        // Dynamic population for others from AppContext
        programComboBox.setItems(FXCollections.observableArrayList(null, "LOCAL", "INTERNATIONAL", "EXCHANGE"));
        majorComboBox.setItems(FXCollections.observableArrayList(
                majors
        ));
        completedRequisitesComboBox.getItems().setAll(FXCollections.observableArrayList(
                context.getCourseRepository().courses().stream().map(Course::title).toList()
        ));

        degreeComboBox.setItems(FXCollections.observableArrayList(
                null, "Bachelor's Degree", "Associate Degree", "Master's Degree", "PhD", "Doctorate"
        ));
        statusComboBox.setItems(FXCollections.observableArrayList(
                statuses
        ));
        locationComboBox.setItems(FXCollections.observableArrayList(
                null, "Main Campus", "Satellite Campus"
        ));

        titleComboBox.setItems(FXCollections.observableArrayList(
                null, "Prof.", "Dr."
        ));
    }

    private void setupEventHandlers() {
        // Real-time validation
        nameField.textProperty().addListener((obs, old, text) -> validateForm());
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
        setupFieldFocus(nameField);
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
        boolean isValid = !nameField.getText().isBlank() &&
                !passwordField.getText().isBlank() &&
                !studentIdField.getText().isBlank();

        submitBtn.setDisable(!isValid);
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private boolean allFilled(Object... values) {
        return Arrays.stream(values).allMatch(Objects::nonNull);
    }

    private void createBasicStudent() {
        String full_name = nameField.getText();
        String first_name = full_name.substring(0, full_name.indexOf(" "));
        String surname = full_name.substring(full_name.indexOf(" "));

        String password = passwordField.getText();
        String student_id = studentIdField.getText();
//        String email = emailField.getText();
//        String phone_number = phoneField.getText();
//        String level = levelComboBox.getValue();
//        List<String> completed_courses = completedRequisitesComboBox.getItems();
//        College college = College.valueOf(collegeComboBox.getValue());
//        Major major = Major.valueOf(majorComboBox.getValue());
//        Program program = Program.valueOf(programComboBox.getValue());

        String student_eid = UserService.assignStudentEid(context.exportContext(), full_name);
        Student created_student = Student.factoryCreate(
                full_name,
                surname,
                first_name,
                student_id,
                student_eid,
                password
        );

        User new_user = new User(student_id, full_name, created_student);

        context.setCurrentUser(new_user);
        context.addNewUser(new_user);
        validateStudentCreation();

    }

    private void createFullStudent() {
        String full_name = nameField.getText();
        String first_name = full_name.substring(0, full_name.indexOf(" "));
        String surname = full_name.substring(full_name.indexOf(" "));

        String password = passwordField.getText();
        String student_id = studentIdField.getText();
        String email = emailField.getText();
        String phone_number = phoneField.getText();
        String level = levelComboBox.getValue();
        List<String> completed_courses = completedRequisitesComboBox.getCheckModel().getCheckedItems();
        College college = College.valueOf(collegeComboBox.getValue());
        Major major = Major.valueOf(majorComboBox.getValue());
        Program program = Program.valueOf(programComboBox.getValue());
        String location = locationComboBox.getValue();
        Status status = Status.valueOf(statusComboBox.getValue());
        String degree = degreeComboBox.getValue();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        String student_eid = UserService.assignStudentEid(context.exportContext(), full_name);
        Student created_student = new Student(
                full_name,
                surname,
                first_name,
                student_id,
                student_eid,
                password,
                college,
                degree,
                status,
                program,
                location,
                start,
                end,
                new ArrayList<Course>(),
                CourseService.coursesFromTitles(context.exportContext(), completed_courses),
                major,
                new ArrayList<Course>()

        );

        User new_user = new User(student_id, full_name, created_student);

        context.setCurrentUser(new_user);
        context.addNewUser(new_user);
        validateStudentCreation();

    }

    private boolean allNonBlank(String... values) {
        return Arrays.stream(values).allMatch(v -> v != null && !v.isBlank());
    }

    private boolean allNonNull(Object... values) {
        return Arrays.stream(values).allMatch(Objects::nonNull);
    }

    private boolean isNonBlank(String value) {
        return value != null && !value.isBlank();
    }

    private boolean isNonNull(Object value) {
        return value != null;
    }

    private boolean isNonEmpty(java.util.Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    private boolean isMinimumStudentFormValid() {
        return allNonBlank(
                nameField.getText(),
                passwordField.getText(),
                studentIdField.getText()
        );
    }

    private boolean isFullStudentFormValid() {
        return allNonBlank(
                nameField.getText(),
                passwordField.getText(),
                studentIdField.getText(),
                emailField.getText(),
                phoneField.getText(),
                levelComboBox.getValue(),
                collegeComboBox.getValue(),
                majorComboBox.getValue(),
                programComboBox.getValue()
        ) && isNonEmpty(completedRequisitesComboBox.getCheckModel().getCheckedItems());
    }

    private void validateStudentCreation() {
        boolean validated = true;


        if (validated) {
            try {

//            MasterJSONBuilder.writeLocalToMaster(MasterJSONBuilder.buildSampleMaster());
                String result = MasterJSONBuilder.writeLocalToMaster(context.exportContext());
                showSuccessAlert(result);

            } catch (Exception e) {
                e.printStackTrace();

                String errorMessage = "Failed to export JSON file:\n" + e.getMessage();
                showErrorAlert(errorMessage);
            }
        }
    }

    @FXML
    private void handleSubmit() {

        if (!isMinimumStudentFormValid()) {
            showErrorAlert("Please fill in name, password, and student ID.");
            return;
        }

        try {
            if (isFullStudentFormValid()) {
                createFullStudent();
            } else {
                createBasicStudent();
            }
            showSuccessDialog();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception exception) {
//            exception.printStackTrace();
            showErrorAlert("Incorrect format for inputted fields: Please enter your full name correctly");
        }
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
        if (context.getRootUserType().equals(RootUserType.STUDENT)) {
            studentForm.setVisible(true);
        } else if (context.getRootUserType().equals(RootUserType.ADMIN)) {
            studentForm.setVisible(false);
            adminForm.setVisible(true);
        }
    }

    @FXML
    private void handleCancel() {
        // by default the user is set to signed out
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
//        WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In", context);
    }
}