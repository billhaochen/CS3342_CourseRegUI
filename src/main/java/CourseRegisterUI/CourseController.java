package CourseRegisterUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CourseController {
    @FXML private HBox userIcon;
    @FXML private HBox exportButton;
    @FXML private VBox courseListPane;
    @FXML private BorderPane schedulePane;
    @FXML private MenuBar menuBar;

    @FXML
    public void initialize() {
        courseListPane.getChildren().setAll(ComponentLoader.loadSidePanel());
        schedulePane.setCenter(ComponentLoader.loadWeeklyCalendar());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
        userIcon.getChildren().setAll(ComponentLoader.loadUserIcon());
        exportButton.getChildren().setAll(ComponentLoader.loadExportButton());
    }
}