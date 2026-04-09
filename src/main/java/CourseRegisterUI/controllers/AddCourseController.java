package CourseRegisterUI.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddCourseController {
//    @FXML private TableView<CourseInfo> courseTableView;
    @FXML private TableView<?> courseTableView;



    @FXML
    public void handleAddToSchedule() {
        System.out.println("State Confirmation: Add to Schedule Clicked");
    }

    @FXML
    public void handleCancel() {
        Stage stage = (Stage) courseTableView.getScene().getWindow();
        stage.close();
    }

}
