package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import CourseRegisterUI.util.JSONDeserializer;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Objects;
import java.util.Optional;

import static CourseRegisterUI.util.UserService.*;

public class SignInController implements ContextAware {
    @FXML private Circle sign_in_graph;
    @FXML private VBox rootContainer;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField passwordField;
    @FXML private Button submitButton;
    @FXML private Hyperlink signOutLink;
    @FXML private Hyperlink createAccountLink;
    @FXML private Label idWarningLabel;
    private AppContext context;
    private MainController mainController;

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
        rootContainer.setPrefHeight(450);
        nameField.getStyleClass().add("text-field-custom");
        idField.getStyleClass().add("text-field-custom");
        passwordField.getStyleClass().add("text-field-custom");
        submitButton.getStyleClass().add("btn-submit");
        signOutLink.getStyleClass().add("link-ghost");
        createAccountLink.getStyleClass().add("link-ghost");
        idWarningLabel.getStyleClass().add("idWarningClass");
    }

    public void setMainController(MainController mainController) {
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
        String password = passwordField.getText();

        if (context.getRootUserType().equals(RootUserType.STUDENT)) {
            if (validateStudentCredentials(full_name, id, password)) {
                context.setCurrentUser(full_name, id);
                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.close();
            } else {
                displayWarning();
            }
        } else if(context.getRootUserType().equals(RootUserType.ADMIN)) {
            if (validateAdminCredentials(full_name, id, password)) {
                context.setCurrentUser(full_name, id);
                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.close();
            } else {
                displayWarning();
            }
        }

    }

    // don't delete this, it might be super helpful later
//    public static <A, B> void ifBothPresent(
//            Optional<A> a,
//            Optional<B> b,
//            BiConsumer<A, B> action) {
//        a.ifPresent(av -> b.ifPresent(bv -> action.accept(av, bv)));
//    }


    private boolean validateStudentCredentials(String student_name, String student_id, String password) {
        boolean valid = false;
        if (Objects.equals(student_name, "") || Objects.equals(student_id, "")) {
            return valid;
        }
        Optional<User> name_lookup = getStudentByName(this.context.getCourseRepository(), student_name);
        Optional<User> id_lookup = getStudentByID(this.context.getCourseRepository(), student_id);
        return name_lookup.isPresent()
                && id_lookup.isPresent()
                && name_lookup.get().role() instanceof Student
                && id_lookup.get().role() instanceof Student
                && name_lookup.get().getID().equals(student_id)
                && id_lookup.get().name().equals(student_name)
                && id_lookup.get().getPassword().equals(password)
                && name_lookup.get().getPassword().equals(password);
    }

    private boolean validateAdminCredentials(String admin_name, String admin_id, String password) {
        boolean valid = false;
        if (Objects.equals(admin_name, "") || Objects.equals(admin_id, "")) {
            return valid;
        }
        Optional<User> name_lookup = getAdminByName(this.context.getCourseRepository(), admin_name);
        Optional<User> id_lookup = getAdminByID(this.context.getCourseRepository(), admin_id);
        return name_lookup.isPresent()
                && id_lookup.isPresent()
                && name_lookup.get().role() instanceof Teacher
                && id_lookup.get().role() instanceof Teacher
                && name_lookup.get().getID().equals(admin_id)
                && id_lookup.get().name().equals(admin_name)
                && id_lookup.get().getPassword().equals(password)
                && name_lookup.get().getPassword().equals(password);
    }

    @FXML
    private void handleSignedOut(){
        // by default the user is set to signed out
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateNewAccount(){
        Window owner = nameField.getScene().getWindow();
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
        WindowController.showCreateAccountPopup(owner, this.context);
    }

}
