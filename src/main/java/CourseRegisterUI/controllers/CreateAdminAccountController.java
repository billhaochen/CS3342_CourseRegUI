package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Admin;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.MasterJSONBuilder;
import CourseRegisterUI.util.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static CourseRegisterUI.ComponentLoader.showErrorAlert;
import static CourseRegisterUI.ComponentLoader.showSuccessAlert;

public class CreateAdminAccountController implements ContextAware {
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField idField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> titleComboBox;
    @FXML
    private VBox createAccountBox;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button submitBtn;

    private AppContext context;

    @FXML
    public void initialize() {
        Platform.runLater(() -> createAccountBox.requestFocus());
        createAccountBox.setOnMouseClicked(e -> createAccountBox.requestFocus());
        createAccountBox.getStyleClass().add("modern-dialog");
        nameField.getStyleClass().add("text-field-custom");
        passwordField.getStyleClass().add("text-field-custom");
        phoneField.getStyleClass().add("text-field-custom");
        emailField.getStyleClass().add("text-field-custom");
        submitBtn.getStyleClass().add("btn-submit");
        cancelBtn.getStyleClass().add("link-ghost");
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        populateComboBox();
    }

    private void populateComboBox() {
        titleComboBox.setItems(FXCollections.observableArrayList(
                null, "Prof.", "Dr."
        ));
    }

    @FXML
    private void handleCancel() {
        // by default the user is set to signed out
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account created successfully!");
        alert.show();
    }

    private boolean allNonBlank(String... values) {
        return Arrays.stream(values).allMatch(v -> v != null && !v.isBlank());
    }

    private boolean isMinimumStudentFormValid() {
        return allNonBlank(
                nameField.getText(),
                passwordField.getText(),
                idField.getText()
        );
    }

    private boolean isFullFormValid() {
        return allNonBlank(
                nameField.getText(),
                passwordField.getText(),
                idField.getText(),
                emailField.getText(),
                phoneField.getText(),
                titleComboBox.getValue()
        );
    }

    private void validateAdminCreation() {
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

    private void createAdmin() {
        String name = nameField.getText();
        String password = passwordField.getText();
        String adminId = idField.getText();
        String email = emailField.getText();
        String phone_number = phoneField.getText();
        String title = titleComboBox.getValue();
        String adminEid = UserService.assignAdminEid(context.exportContext(), name);

        Admin createdAdmin = new Admin(
                name,
                password,
                adminId,
                email,
                phone_number,
                title,
                adminEid,
                List.of()
        );

        User new_user = new User(adminId, name, createdAdmin);
        context.setCurrentUser(new_user);
        context.addNewUser(new_user);
        validateAdminCreation();
    }

    @FXML
    private void handleSubmit() {

        if (!isMinimumStudentFormValid()) {
            showErrorAlert("Please fill in name, password, and student ID.");
            return;
        }

        try {
            if (isFullFormValid()) {
                createAdmin();
                showSuccessDialog();
                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.close();
            } else {
                showErrorAlert("Please fill out all of the fields");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            showErrorAlert("Incorrect format for inputted fields: Please enter your full name correctly");
        }
    }
}
