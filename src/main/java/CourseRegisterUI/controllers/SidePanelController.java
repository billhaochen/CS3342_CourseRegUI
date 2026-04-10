package CourseRegisterUI.controllers;
import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Window;


public class SidePanelController {
    @FXML private Button addCourseBtn;
    @FXML private VBox courseListPane;
    private AppContext context;

    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    @FXML public void initialize() {
        addCourseBtn.getStyleClass().add("btn-submit");
    }
    @FXML
    private void handleAddCourseDialog(){
        Window owner = courseListPane.getScene().getWindow();
        WindowController.showAddCourse(owner, this.context);
    }
}
