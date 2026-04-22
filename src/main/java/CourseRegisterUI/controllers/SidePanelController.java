package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class SidePanelController implements ContextAware {
    @FXML
    private Button addCourseBtn;
    @FXML
    private ScrollPane courseListPane;
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

    @FXML private ComboBox<String> academicUnitComboBox;
    @FXML private TextField academicUnitTextField;
    @FXML private TextField search_by_text_field;
    @FXML private CheckBox creditCheckBox;
    @FXML private TextField creditTextField;
    @FXML private RadioButton exactCreditButton;
    @FXML private CheckBox programCheckBox;
    @FXML private ComboBox<College> programComboBox;
    @FXML private CheckBox mediumCheckBox;
    @FXML private ComboBox<String> mediumComboBox;

    // this represents ALL of that pane, not just the course list box
    private AppContext context;

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;

        courseTable.setItems(context.getFilteredCourseRows());

        attachFilters();

        context.getSelectedCourseRows().addListener((ListChangeListener<CourseRow>) change -> {
            while (change.next()) {
                loadFilters();
            }
        });
    }


    @FXML
    public void initialize() {
        courseListPane.setFitToWidth(true);
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

        courseTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CourseRow selectedRow = courseTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    Course selectedCourse = selectedRow.getCourse();
                    if (selectedCourse != null) {
                        javafx.stage.Window owner = courseTable.getScene().getWindow();
                        WindowController.showCourseInfoPopup(owner, context, selectedCourse);
                    }
                }
            }
        });
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

    private void applyFilters(Observable obs) {
        context.getFilteredCourseRows().setPredicate(this::matchesAllFilters);
    }

    private boolean matchesAllFilters(CourseRow row) {
        Course c = row.getCourse();

        String courseTitle = search_by_text_field.getText();
        if (courseTitle != null && !courseTitle.isBlank()) {
            String s = courseTitle.toLowerCase();
            if (!c.title().toLowerCase().contains(s) && !c.course_code().toLowerCase().contains(s)) {
                return false;
            }
        }

        String nameText = academicUnitTextField.getText();
        String nameChoice = academicUnitComboBox.getValue();

        if (nameText != null && !nameText.isBlank()) {
            String s = nameText.toLowerCase();
            if (!c.academic_unit().toLowerCase().contains(s) && !c.course_code().toLowerCase().contains(s)) {
                return false;
            }
        }

        if (nameChoice != null && !nameChoice.isBlank()) {
            if (!c.academic_unit().equalsIgnoreCase(nameChoice)) {
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
            College selectedCollege = programComboBox.getValue();
            if (selectedCollege != null) {
                if (!c.college().equals(selectedCollege)) {
                    return false;
                }
            }
        }

        if (mediumCheckBox.isSelected()) {
            String medium = mediumComboBox.getValue();
            if (medium != null && !medium.isBlank()) {
                if (!c.medium().equalsIgnoreCase(medium)) return false;
            }
        }

        return true;
    }

    private void attachFilters() {
        loadFilters();

        search_by_text_field.textProperty().addListener((this::applyFilters));
        academicUnitComboBox.valueProperty().addListener((this::applyFilters));
        academicUnitTextField.textProperty().addListener((this::applyFilters));

        creditCheckBox.selectedProperty().addListener((this::applyFilters));
        creditTextField.textProperty().addListener((this::applyFilters));
        exactCreditButton.selectedProperty().addListener((this::applyFilters));

        programCheckBox.selectedProperty().addListener((this::applyFilters));
        programComboBox.valueProperty().addListener((this::applyFilters));

        mediumCheckBox.selectedProperty().addListener((this::applyFilters));
        mediumComboBox.valueProperty().addListener((this::applyFilters));
    }

    public void loadFilters() {
        List<String> academicUnits = new ArrayList<>(context.getSelectedCourseRows().stream()
                .map(courseRow -> courseRow.getCourse().academic_unit())
                .distinct()
                .sorted()
                .toList());
        academicUnits.addFirst(null);

        academicUnitComboBox.setItems(FXCollections.observableArrayList(academicUnits));

        programComboBox.setItems(FXCollections.observableArrayList(
                Stream.concat(Stream.of((College) null), Arrays.stream(College.values()))
                        .toList()
        ));

        mediumComboBox.setItems(FXCollections.observableArrayList(
                null,
                "English",
                "Chinese"
        ));
    }
}
