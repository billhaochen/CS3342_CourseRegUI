package CourseRegisterUI.controllers;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Window;


public class SidePanelController {
    @FXML private VBox courseListPane;
    @FXML
    private void handleAddCourseDialog(){
        Window owner = courseListPane.getScene().getWindow();
        WindowController.showAddCourse(owner);
    }
}
