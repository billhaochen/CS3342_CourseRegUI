package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import javafx.animation.TranslateTransition;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminCourseController implements ContextAware, MainController {
    @FXML private Button createCourseBtn;
    @FXML private TextField searchField;

    @FXML private TableView<CourseRow> courseTable;

    @FXML
    private TableView<CourseRow> courseTableView;
    @FXML
    private TableColumn<CourseRow, String> academicUnitColumn;
    @FXML
    private TableColumn<CourseRow, String> subjectColumn;
    @FXML
    private TableColumn<CourseRow, String> courseColumn;
    @FXML
    private TableColumn<CourseRow, String> titleColumn;
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

    @FXML private Hyperlink userNameAndId;
    @FXML private BorderPane contentBox;
    private AppContext context;
    private FilteredList<CourseRow> filteredList;

    @FXML
    public void initialize() {
        userNameAndId.getStyleClass().add("link-ghost");
        createCourseBtn.getStyleClass().add("btn-submit");
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

        courseTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CourseRow selectedRow = courseTableView.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    Course selectedCourse = selectedRow.getCourse();
                    if (selectedCourse != null) {
                        javafx.stage.Window owner = courseTableView.getScene().getWindow();
                        WindowController.showCourseInfoPopup(owner, context, selectedCourse);
                    }
                }
            }
        });
    }

    private void applyFilters() {
        filteredList.setPredicate(row -> {
            Course c = row.getCourse();

            String courseTitle = searchField.getText();
            if (courseTitle != null && !courseTitle.isBlank()) {
                String s = courseTitle.toLowerCase();
                if (!c.title().toLowerCase().contains(s) && !c.course_code().toLowerCase().contains(s)) {
                    return false;
                }
            }
            return true;
        });
    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        courseTableView.setItems(context.getCourseRows());

        context.currentUserProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser == null) {
                userNameAndId.setText("Not Signed In");
            } else {
                userNameAndId.setText(newUser.name() + " | " + newUser.getID());
            }
        });
        filteredList = new FilteredList<>(context.getCourseRows(), p -> true);
        courseTableView.setItems(filteredList);

        SortedList<CourseRow> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(courseTableView.comparatorProperty());
        courseTableView.setItems(sortedData);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        updateUserInfo();
    }

    @FXML
    public void updateUserInfo() {
        User curr_user = context.getCurrentUser();
        userNameAndId.setText(curr_user.name() + " | " + curr_user.getID());
    }

    private void slideOutAndNavigate(Runnable nextAction) {

        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), contentBox);
        slideOut.setByX(-800);

        slideOut.setOnFinished(e -> nextAction.run());
        slideOut.play();
    }

    @FXML
    public void handleViewStudent() {
        slideOutAndNavigate(() -> {
            Stage stage = (Stage) contentBox.getScene().getWindow();
            WindowController.switchToStudentMainView(stage, context);
        });
    }

    @FXML
    public void handleUserInfo() {
        Stage stage = (Stage) userNameAndId.getScene().getWindow();
        if (context.getCurrentUser().role() instanceof SignedOut) {
            WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In", context);
        } else if (context.getCurrentUser().role() instanceof Student) {
            WindowController.showStudentInfoDialog(stage, this.context);
        } else {
            WindowController.showAdminInfoDialog(stage, this.context);
        }
    }

    @FXML
    public void handleAddCourseDialog() {
        Stage stage = (Stage) userNameAndId.getScene().getWindow();
        WindowController.showCourseCreatePopup(stage, context);
    }
}
