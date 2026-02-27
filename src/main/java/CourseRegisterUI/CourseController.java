package CourseRegisterUI;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CourseController {
    @FXML private VBox courseListPane;
    @FXML private BorderPane schedulePane;
    @FXML private MenuBar menuBar;

    @FXML
    public void initialize() {
        courseListPane.getChildren().setAll(ComponentLoader.loadSidePanel());
        schedulePane.setCenter(ComponentLoader.loadWeeklyCalendar());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
    }
}