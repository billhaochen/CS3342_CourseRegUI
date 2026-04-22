package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Stream;


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

    @FXML
    private ComboBox<String> academicUnitComboBox;
    @FXML
    private TextField academicUnitTextField;
    @FXML
    private TextField search_by_text_field;
    @FXML
    private CheckBox creditCheckBox;
    @FXML
    private TextField creditTextField;
    @FXML
    private RadioButton exactCreditButton;
    @FXML
    private CheckBox programCheckBox;
    @FXML
    private ComboBox<College> programComboBox; // TODO fix this filter
    @FXML
    private CheckBox mediumCheckBox;
    @FXML
    private ComboBox<String> mediumComboBox;

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
                        WindowController.showCourseInfoPopup(owner, context, selectedCourse);
                    }
                }
            }
        });

        creditTextField.disableProperty().bind(creditCheckBox.selectedProperty().not());
        exactCreditButton.disableProperty().bind(creditCheckBox.selectedProperty().not());

        programComboBox.disableProperty().bind(programCheckBox.selectedProperty().not());
        mediumComboBox.disableProperty().bind(mediumCheckBox.selectedProperty().not());
    }

    @FXML
    private void showConflict(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void applyFilters() {
        filteredList.setPredicate(row -> {
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
        });
    }

    private void attachFilters() {
        loadFilters();

        search_by_text_field.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        academicUnitComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        academicUnitTextField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        creditCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        creditTextField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        exactCreditButton.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        programCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        programComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        mediumCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        mediumComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    public void loadFilters() {
        List<String> course_titles = new ArrayList<>(context.getCourseRows().stream()
                .map(courseRow -> courseRow.getCourse().academic_unit())
                .distinct()
                .sorted()
                .toList());
        course_titles.addFirst(null);

        academicUnitComboBox.setItems(FXCollections.observableArrayList(course_titles));

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

    /**
     * this is going to assume that every course here is in the same semester for now
     *
     * @param a
     * @param b
     * @return
     */
    private boolean timeConflicts(Course a, Course b) {
        boolean dateOverlap = !a.end_date().isBefore(b.start_date())
                && !b.end_date().isBefore(a.start_date());

        boolean sameDay = a.day() != null && a.day().equals(b.day());

        boolean timeOverlap = a.start_time().compareTo(b.end_time()) < 0
                && b.start_time().compareTo(a.end_time()) < 0;

        return dateOverlap && sameDay && timeOverlap;
    }

    private boolean takenConflicts(User user, List<Course> courses) {
        if (user.role() instanceof Student) {
            List<Course> already_taken = ((Student) user.role()).completed_courses();
            return courses.stream().anyMatch(already_taken::contains);
        }
        return false;
    }

    private boolean prerequisiteConflicts(User user, List<Course> courses) {
        if (!(user.role() instanceof Student student)) {
            return false;
        }

        // Making sure each set of courses of a pre-requisite is a subset of the completed courses
        // Then map this function over the list of courses a user wants to add
        Set<Course> completedCourses = new HashSet<>(student.completed_courses());

        return courses.stream()
                .anyMatch(course -> !completedCourses.containsAll(course.prerequisites()));
    }

    private boolean validateCourses(ObservableList<CourseRow> rows) {
        List<Course> addedCourses = rows.stream()
                .map(CourseRow::getCourse)
                .toList();

        if (takenConflicts(context.getCurrentUser(), addedCourses)) {
            showConflict("You have already taken one of these courses");
            return false;
        }

        if (prerequisiteConflicts(context.getCurrentUser(), addedCourses)) {
            showConflict("You do not have the required pre-requisites");
            return false;
        }

        for (int i = 0; i < addedCourses.size(); i++) {
            for (int j = i + 1; j < addedCourses.size(); j++) {
                if (timeConflicts(addedCourses.get(i), addedCourses.get(j))) {
                    showConflict("Two or more of these courses have a time conflict");
                    return false;
                }
            }
        }

        // TODO account for other validation like program and pre-requisite validation
        return true;
    }


    @FXML
    public void handleAddToSchedule() {
        ObservableList<CourseRow> selectedRows =
                courseTableView.getSelectionModel().getSelectedItems();

        ObservableList<CourseRow> proposedRows = FXCollections.observableArrayList();
        proposedRows.addAll(selectedRows);
        proposedRows.addAll(context.getSelectedCourseRows());

        if (validateCourses(proposedRows)) {
            context.getSelectedCourses().addAll(
                    selectedRows.stream()
                            .map(CourseRow::getCourse)
                            .toList()
            );
            context.getSelectedCourseRows().addAll(selectedRows);
            context.registerCourses();
            System.out.println("State Confirmation: Add to Schedule Clicked");

            Stage stage = (Stage) courseTableView.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleCancel() {
        Stage stage = (Stage) courseTableView.getScene().getWindow();
        stage.close();
    }
}
