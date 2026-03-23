package CourseRegisterUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {
    @FXML private TextField nameField;
    @FXML private TextField idField;
    public enum UserRole {
        STUDENT,
        ADMIN,
        GUEST
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
