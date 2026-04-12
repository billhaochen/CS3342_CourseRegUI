package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.util.CourseService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
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

    @FXML private ComboBox<String> courseNameComboBox;
    @FXML private TextField courseNameTextField;
    @FXML private CheckBox creditCheckBox;
    @FXML private TextField creditTextField;
    @FXML private RadioButton exactCreditButton;
    @FXML private CheckBox programCheckBox;
    @FXML private ComboBox<String> programComboBox;
    @FXML private CheckBox mediumCheckBox;
    @FXML private ComboBox<String> mediumComboBox;

    private AppContext context;
    private FilteredList<CourseRow> filteredList;

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        filteredList = new FilteredList<>(context.getCourseRows(), p -> true);
        courseTableView.setItems(filteredList);

        SortedList<CourseRow> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(courseTableView.comparatorProperty());
        courseTableView.setItems(sortedData);

        attachFilters();
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

        courseTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CourseRow selectedRow = courseTableView.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    Course selectedCourse = selectedRow.getCourse();
                    if (selectedCourse != null) {
                        javafx.stage.Window owner = courseTableView.getScene().getWindow();
                        WindowController.showCourseInfoPopup(owner, selectedCourse);
                    }
                }
            }
        });

//        programComboBox.setItems(FXCollections.observableArrayList(
//                Arrays.stream(College.values()).map(Enum::toString)
//        ));

        mediumComboBox.setItems(FXCollections.observableArrayList(
                "English",
                "Chinese"
        ));

        creditTextField.disableProperty().bind(creditCheckBox.selectedProperty().not());
        exactCreditButton.disableProperty().bind(creditCheckBox.selectedProperty().not());

        programComboBox.disableProperty().bind(programCheckBox.selectedProperty().not());
        mediumComboBox.disableProperty().bind(mediumCheckBox.selectedProperty().not());
    }

    @FXML
    private void showConflict() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText("One or more selected courses overlap.");
        alert.showAndWait();
    }

    private void applyFilters() {
        filteredList.setPredicate(row -> {
            Course c = row.getCourse();

            String nameText = courseNameTextField.getText();
            String nameChoice = courseNameComboBox.getValue();

            if (nameText != null && !nameText.isBlank()) {
                String s = nameText.toLowerCase();
                if (!c.title().toLowerCase().contains(s) && !c.course_code().toLowerCase().contains(s)) {
                    return false;
                }
            }

            if (nameChoice != null && !nameChoice.isBlank()) {
                if (!c.title().equalsIgnoreCase(nameChoice)) {
                    return false;
                }
            }

            if (creditCheckBox.isSelected()) {
                if (creditTextField.getText() == null || creditTextField.getText().isBlank()) {
                    return false;
                }

                try {
                    int credit = Integer.parseInt(creditTextField.getText().trim());
                    if (exactCreditButton.isSelected()) {
                        if (!c.credit().equals(credit)) return false;
                    } else {
                        if (c.credit() < credit) return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            if (programCheckBox.isSelected()) {
                String program = programComboBox.getValue();
                if (program != null && !program.isBlank()) {
                    if (!c.college().toString().equalsIgnoreCase(program)) return false;
                }
            }

            if (mediumCheckBox.isSelected()) {
                String medium = mediumComboBox.getValue();
                if (medium != null && !medium.isBlank()) {
                    if (!c.medium().equalsIgnoreCase(medium)) return false;
                }
            }

            return true;
        });
    }

    private void attachFilters() {
        courseNameTextField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        courseNameComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        creditCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        creditTextField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        exactCreditButton.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        programCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        programComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        mediumCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        mediumComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    public void loadFilters() {
        courseNameComboBox.setItems(FXCollections.observableArrayList(
                context.getCourseRows().stream()
                        .map(CourseRow::getCourse)
                        .map(Course::title)
                        .distinct()
                        .sorted()
                        .toList()
        ));

        programComboBox.setItems(FXCollections.observableArrayList(
                context.getCourseRows().stream()
                        .map(CourseRow::getCourse)
                        .map(c -> c.college().toString())
                        .distinct()
                        .sorted()
                        .toList()
        ));

        mediumComboBox.setItems(FXCollections.observableArrayList(
                context.getCourseRows().stream()
                        .map(CourseRow::getCourse)
                        .map(Course::medium)
                        .distinct()
                        .sorted()
                        .toList()
        ));
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
