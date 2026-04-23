package CourseRegisterUI.controllers;

public class AdminLandingController {
    @FXML private Label teacherNameLabel;

    @FXML private Button calendarButton;
    @FXML private Button viewAsStudentButton;
    @FXML private Button filterButton;
    @FXML private Button visibilityButton;
    @FXML private Button editButton;
    @FXML private Button uploadButton;

    @FXML private TextField searchField;

    @FXML private TableView<CourseRow> courseTable;

    @FXML private TableColumn<CourseRow, String> bookmarkColumn;
    @FXML private TableColumn<CourseRow, String> academicUnitColumn;
    @FXML private TableColumn<CourseRow, String> subjectColumn;
    @FXML private TableColumn<CourseRow, String> courseColumn;
    @FXML private TableColumn<CourseRow, String> titleColumn;
    @FXML private TableColumn<CourseRow, String> creditColumn;
    @FXML private TableColumn<CourseRow, String> webColumn;
    @FXML private TableColumn<CourseRow, String> levelColumn;
    @FXML private TableColumn<CourseRow, String> availColumn;
    @FXML private TableColumn<CourseRow, String> capColumn;
    @FXML private TableColumn<CourseRow, String> waitlistColumn;
    @FXML private TableColumn<CourseRow, String> mediumColumn;
}
