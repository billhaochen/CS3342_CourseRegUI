package CourseRegisterUI.controllers;
import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddCourseController implements ContextAware {
    @FXML private TableView<CourseRow> courseTableView;
    @FXML private TableColumn<CourseRow,  String> academicUnitColumn;
    @FXML private TableColumn<CourseRow,  String> subjectColumn;
    @FXML private TableColumn<CourseRow,  String> courseColumn;
    @FXML private TableColumn<CourseRow,  String> titleColumn;
    @FXML private TableColumn<CourseRow,  Integer> creditColumn;
    @FXML private TableColumn<CourseRow,  Boolean> webColumn;
    @FXML private TableColumn<CourseRow,  String> levelColumn;
    @FXML private TableColumn<CourseRow,  Integer> availabilityColumn;
    @FXML private TableColumn<CourseRow,  Integer> capColumn;
    @FXML private TableColumn<CourseRow, Boolean> waitlistAvailColumn;
    @FXML private TableColumn<CourseRow,  String> mediumColumn;

    @Override
    public void setAppContext(AppContext appContext) {
        courseTableView.setItems(appContext.getCourseRows());
    }


    @FXML
    public void initialize() {
        academicUnitColumn.setCellValueFactory(cell -> cell.getValue().getProperty("academic_unit"));
        subjectColumn     .setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("course_code"));
        titleColumn       .setCellValueFactory(cell -> cell.getValue().getProperty("title"));
        creditColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("credit"));
        webColumn.setCellValueFactory(cell -> cell.getValue().getProperty("web_enabled"));
        levelColumn.setCellValueFactory(cell -> cell.getValue().getProperty("level"));
        availabilityColumn.setCellValueFactory(cell -> cell.getValue().getProperty("availability"));
        capColumn.setCellValueFactory(cell -> cell.getValue().getProperty("cap"));
        waitlistAvailColumn.setCellValueFactory(cell -> cell.getValue().getProperty("waitlist_available"));
        mediumColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("medium"));
    }

//    public void addCourse(Course course) {
//        rows.add(new CourseRow(course));
//    }

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
