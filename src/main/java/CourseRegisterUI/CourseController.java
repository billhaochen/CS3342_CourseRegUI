package CourseRegisterUI;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

import static CourseRegisterUI.ComponentLoader.loadExportButton;

public class CourseController {
    @FXML private HBox userIcon;
    @FXML private HBox exportHBox;
    @FXML private VBox courseListPane;
    @FXML private BorderPane schedulePane;
    @FXML private MenuBar menuBar;

    @FXML
    public void initialize() {
        courseListPane.getChildren().setAll(ComponentLoader.loadSidePanel());
        schedulePane.setCenter(ComponentLoader.loadWeeklyCalendar());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
        userIcon.getChildren().setAll(ComponentLoader.loadUserIcon());
        exportHBox.getChildren().setAll(ComponentLoader.loadExportButton());
    }
}