package CourseRegisterUI.controllers;
import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Window;


public class SidePanelController implements ContextAware {
    @FXML private Button addCourseBtn;
    @FXML private VBox courseListPane; // this represents ALL of that pane, not just the course list box
    private AppContext context;
    private CourseController mainController;


    @FXML public void initialize() {
        addCourseBtn.getStyleClass().add("btn-submit");
    }

    public void setMainController(CourseController mainController) {
        this.mainController = mainController;
        this.mainController.setAppContext(this.context); // TODO double check this later
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    @FXML
    private void handleAddCourseDialog(){
        WindowController.showModal(
                courseListPane.getScene().getWindow(),
                "/CourseRegisterUI/AddCourseDialog.fxml",
                "Add New Course",
                context
        );
    }
}
