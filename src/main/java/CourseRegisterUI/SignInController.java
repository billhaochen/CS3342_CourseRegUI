package CourseRegisterUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignInController {
    @FXML private VBox rootContainer;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private Button submitButton;
    @FXML private Hyperlink signOutLink;
    @FXML private Hyperlink createAccountLink;


    public enum UserRole {
        STUDENT,
        ADMIN,
        GUEST
    }

    @FXML public void initialize() {
        rootContainer.getStyleClass().add("modern-dialog");
        nameField.getStyleClass().add("text-field-custom");
        idField.getStyleClass().add("text-field-custom");
        submitButton.getStyleClass().add("btn-submit");
        signOutLink.getStyleClass().add("link-ghost");
        createAccountLink.getStyleClass().add("link-ghost");
    }

    private UserRole userRole = UserRole.STUDENT;
    @FXML
    private void handleSubmit(){
        System.out.println("User Name: " + nameField.getText());
        System.out.println("Student ID: " + idField.getText());
    }

    @FXML
    private void handleSignedOut(){
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateNewAccount(){

    }
}
