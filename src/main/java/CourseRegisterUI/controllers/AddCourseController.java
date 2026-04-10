package CourseRegisterUI.controllers;
import CourseRegisterUI.AppContext;
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


public class AddCourseController {
    @FXML private TableView<CourseRow> courseTableView;
    @FXML private TableColumn<CourseRow,  String> academicUnitColumn;
    @FXML private TableColumn<CourseRow,  String> subjectColumn;
    @FXML private TableColumn<CourseRow,  String> courseColumn;
    @FXML private TableColumn<CourseRow,  String> titleColumn;
    @FXML private TableColumn<CourseRow,  Integer> creditColumn;
    @FXML private TableColumn<CourseRow,  String> mediumColumn;

    private final ObservableList<CourseRow> rows = FXCollections.observableArrayList();
    private AppContext context;

    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }


    @FXML
    public void initialize() {
        academicUnitColumn.setCellValueFactory(cell -> cell.getValue().getProperty("academicUnit"));
        subjectColumn     .setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("courseCode"));
        titleColumn       .setCellValueFactory(cell -> cell.getValue().getProperty("title"));
        creditColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("credit"));
        mediumColumn      .setCellValueFactory(cell -> cell.getValue().getProperty("medium"));
        courseTableView.setItems(rows);
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
