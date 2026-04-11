package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.JSONDeserializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class SignInController implements ContextAware {
    @FXML private Circle sign_in_graph;
    @FXML private VBox rootContainer;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private Button submitButton;
    @FXML private Hyperlink signOutLink;
    @FXML private Hyperlink createAccountLink;
    @FXML private Label idWarningLabel;
    private UserRole userRole = UserRole.STUDENT;
    private AppContext context;
    private CourseController mainController;

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    public enum UserRole {
        STUDENT,
        ADMIN,
        GUEST
    }

    @FXML public void initialize() {
        Platform.runLater(() -> rootContainer.requestFocus());
        rootContainer.setOnMouseClicked(e -> rootContainer.requestFocus());
        rootContainer.getStyleClass().add("modern-dialog");
        rootContainer.setPrefWidth(300);
        rootContainer.setPrefHeight(400);
        nameField.getStyleClass().add("text-field-custom");
        idField.getStyleClass().add("text-field-custom");
        submitButton.getStyleClass().add("btn-submit");
        signOutLink.getStyleClass().add("link-ghost");
        createAccountLink.getStyleClass().add("link-ghost");
        idWarningLabel.getStyleClass().add("idWarningClass");
    }

    public void setMainController(CourseController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void displayWarning() {
        if (idField.getText() == null || idField.getText().isBlank()) {
            idWarningLabel.setText("Name or ID cannot be blank");
            idWarningLabel.setVisible(true);
            idWarningLabel.setManaged(true);
        } else {
            idWarningLabel.setText("Incorrect Credentials");
            idWarningLabel.setVisible(true);
            idWarningLabel.setManaged(true);
        }
    }


    @FXML
    private void handleSubmit(){
        String full_name = nameField.getText();
        String id = idField.getText();
        if (validateStudentCredentials(full_name, id)) {
            context.setCurrentUser(full_name, id);
            mainController.updateUserInfo(full_name, id);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } else {
            displayWarning();
        }
    }

    // don't delete this, it might be super helpful later
//    public static <A, B> void ifBothPresent(
//            Optional<A> a,
//            Optional<B> b,
//            BiConsumer<A, B> action) {
//        a.ifPresent(av -> b.ifPresent(bv -> action.accept(av, bv)));
//    }

    @FXML
    private boolean validateStudentCredentials(String student_name, String student_id) {
        boolean valid = false;
        if (Objects.equals(student_name, "") || Objects.equals(student_id, "")) {
            return valid;
        }
        Optional<User> name_lookup = JSONDeserializer.getStudentByName(this.context.getCourseRepository(), student_name);
        Optional<User> id_lookup = JSONDeserializer.getStudentByID(this.context.getCourseRepository(), student_id);
        return name_lookup.isPresent()
                && id_lookup.isPresent()
                && name_lookup.get().role() instanceof Student
                && id_lookup.get().role() instanceof Student
                && name_lookup.get().getID().equals(student_id)
                && id_lookup.get().name().equals(student_name);
    }

    @FXML
    private void handleSignedOut(){
        // by default the user is set to signed out
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateNewAccount(){

    }

}
