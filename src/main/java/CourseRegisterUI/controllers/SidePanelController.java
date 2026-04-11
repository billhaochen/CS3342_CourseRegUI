package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Window;


public class SidePanelController implements ContextAware {
    @FXML
    private Button addCourseBtn;
    @FXML
    private VBox courseListPane;
    @FXML
    private TableView<CourseRow> courseTable;
    @FXML
    private TableColumn<CourseRow, String> subjectColumn;
    @FXML
    private TableColumn<CourseRow, String> courseNumberColumn;
    @FXML
    private TableColumn<CourseRow, String> titleColumn;
    @FXML
    private TableColumn<CourseRow, String> academicUnitColumn;
    @FXML
    private TableColumn<CourseRow, String> courseColumn;
    @FXML
    private TableColumn<CourseRow, Integer> creditColumn;
    @FXML
    private TableColumn<CourseRow, Boolean> webColumn;
    @FXML
    private TableColumn<CourseRow, String> levelColumn;
    @FXML
    private TableColumn<CourseRow, Integer> availabilityColumn;
    @FXML
    private TableColumn<CourseRow, Integer> capColumn;
    @FXML
    private TableColumn<CourseRow, Boolean> waitlistAvailColumn;
    @FXML
    private TableColumn<CourseRow, String> mediumColumn;
    @FXML
    private TableColumn<CourseRow, String> sectionColumn;
    @FXML
    private TableColumn<CourseRow, String> crnColumn;
    @FXML
    private TableColumn<CourseRow, String> dayColumn;
    @FXML
    private TableColumn<CourseRow, String> meetingColumn;


    // this represents ALL of that pane, not just the course list box
    private AppContext context;
    private CourseController mainController;


    @FXML
    public void initialize() {
        addCourseBtn.getStyleClass().add("btn-submit");
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        subjectColumn.setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseNumberColumn.setCellValueFactory(cell -> cell.getValue().getProperty("course_code"));
        titleColumn.setCellValueFactory(cell -> cell.getValue().getProperty("title"));

        academicUnitColumn.setCellValueFactory(cell -> cell.getValue().getProperty("academic_unit"));
        subjectColumn.setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseColumn.setCellValueFactory(cell -> cell.getValue().getProperty("course_code"));
        titleColumn.setCellValueFactory(cell -> cell.getValue().getProperty("title"));
        sectionColumn.setCellValueFactory(cell -> cell.getValue().getProperty("section"));
        creditColumn.setCellValueFactory(cell -> cell.getValue().getProperty("credit"));
        crnColumn.setCellValueFactory(cell -> cell.getValue().getProperty("crn"));
        dayColumn.setCellValueFactory(cell -> cell.getValue().getProperty("day"));
        meetingColumn.setCellValueFactory(cell -> cell.getValue().getProperty("meeting_time"));
        webColumn.setCellValueFactory(cell -> cell.getValue().getProperty("web_enabled"));
        levelColumn.setCellValueFactory(cell -> cell.getValue().getProperty("level"));
        availabilityColumn.setCellValueFactory(cell -> cell.getValue().getProperty("availability"));
        capColumn.setCellValueFactory(cell -> cell.getValue().getProperty("cap"));
        waitlistAvailColumn.setCellValueFactory(cell -> cell.getValue().getProperty("waitlist_available"));
        mediumColumn.setCellValueFactory(cell -> cell.getValue().getProperty("medium"));
    }

    public void setMainController(CourseController mainController) {
        this.mainController = mainController;
        this.mainController.setAppContext(this.context); // TODO double check this later
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        courseTable.setItems(appContext.getSelectedCourseRows());
    }

    @FXML
    private void handleAddCourseDialog() {
        WindowController.showModal(
                courseListPane.getScene().getWindow(),
                "/CourseRegisterUI/AddCourseDialog.fxml",
                "Add New Course",
                context
        );
    }
}
