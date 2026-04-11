package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.util.CourseService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class AddCourseController implements ContextAware {
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
    private AppContext context;

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        courseTableView.setItems(appContext.getCourseRows());
    }


    @FXML
    public void initialize() {
        courseTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

//    public void addCourse(Course course) {
//        rows.add(new CourseRow(course));
//    }

    @FXML
    private void showConflict() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText("One or more selected courses overlap.");
        alert.showAndWait();
    }



    @FXML
    public void handleAddToSchedule() {
        ObservableList<CourseRow> selectedRows =
                courseTableView.getSelectionModel().getSelectedItems();

        ObservableList<CourseRow> proposedRows = FXCollections.observableArrayList();
        proposedRows.addAll(selectedRows);
        proposedRows.addAll(context.getSelectedCourseRows());

        if (CourseService.validateCourses(proposedRows)) {
            context.getSelectedCourses().addAll(
                    selectedRows.stream()
                            .map(CourseRow::getCourse)
                            .toList()
            );
            context.getSelectedCourseRows().addAll(selectedRows);
            System.out.println("State Confirmation: Add to Schedule Clicked");

            Stage stage = (Stage) courseTableView.getScene().getWindow();
            stage.close();
        } else {
            showConflict();
        }
    }

    @FXML
    public void handleCancel() {
        Stage stage = (Stage) courseTableView.getScene().getWindow();
        stage.close();
    }
}
