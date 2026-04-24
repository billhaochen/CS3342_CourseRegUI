package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static CourseRegisterUI.util.CalendarService.mapDayToColumn;
import static CourseRegisterUI.util.CalendarService.mapTimeToRow;

public class CourseInfoController implements ContextAware {
    @FXML
    private Label courseName;
    @FXML
    private Label courseCode;
    @FXML
    private Label courseCRN;
    @FXML
    private Label professorName;
    @FXML
    private Label departmentName;
    @FXML
    private Label courseLevel;
    @FXML
    private Label courseCredits;
    @FXML
    private Label courseWebEnabled;
    @FXML
    private ScrollPane mainScroll;
    @FXML
    private GridPane courseCalendarGrid;  // Single grid!

    @FXML
    private Label weekTitle;
    @FXML
    private Button prevWeek, nextWeek, todayBtn;
    private StackPane selectedCell;
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
    private Hyperlink editButton;

    @FXML
    private Button joinWaitlistButton;
    @FXML
    private Button unregisterButton;

    private LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
    private Course course;
    private AppContext context;

    @FXML
    public void initialize() {
        mainScroll.getStyleClass().add("main-scroll");
        mainScroll.setFitToWidth(true);
        mainScroll.setPannable(true);  // Smooth scroll with mouse drag
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Platform.runLater(() -> {
            refreshCourseCalendar();
            // FIXED: Set explicit height so ScrollPane knows content size
            courseCalendarGrid.setPrefHeight(500);  // Minimum visible height
            mainScroll.requestLayout();
        });
        joinWaitlistButton.getStyleClass().add("btn-submit");
        unregisterButton.getStyleClass().add("link-ghost");
    }

    public void setCourseInfo(Course course) {
        this.course = course;
        if (course == null) return;

        courseName.setText(course.title() != null ? course.title() : "Unknown Title");
        courseCode.setText(course.course_code() != null ? course.course_code() : "Unknown Code");
        courseCRN.setText(course.crn() != null ? course.crn() : "N/A");

        if (course.instructor_id() != null) {
            professorName.setText(course.instructor_id());
        } else {
            professorName.setText("TBD");
        }

        departmentName.setText(course.subject() != null ? course.subject() : "N/A");
        courseLevel.setText(course.level() != null ? course.level() : "N/A");
        courseCredits.setText(course.credit() != null ? String.valueOf(course.credit()) : "0");
        courseWebEnabled.setText(course.web_enabled() != null && course.web_enabled() ? "Yes" : "No");
        courseTable.setItems(
                FXCollections.observableArrayList(
                        course.prerequisites().stream()
                                .map(CourseRow::new)
                                .toList()
                )
        );

        subjectColumn.setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseNumberColumn.setCellValueFactory(cell -> cell.getValue().getProperty("course_code"));
        titleColumn.setCellValueFactory(cell -> cell.getValue().getProperty("title"));

        academicUnitColumn.setCellValueFactory(cell -> cell.getValue().getProperty("academic_unit"));
        subjectColumn.setCellValueFactory(cell -> cell.getValue().getProperty("subject"));
        courseColumn.setCellValueFactory(cell -> cell.getValue().getProperty("course_code"));
        titleColumn.setCellValueFactory(cell -> cell.getValue().getProperty("title"));

        renderCourseCalendar(course);
    }

    private void updateTitle() {
        LocalDate end = weekStart.plusDays(6);
        weekTitle.setText(weekStart.format(DateTimeFormatter.ofPattern("MMM dd")) +
                " - " + end.format(DateTimeFormatter.ofPattern("MMM dd")));
    }

    private void refreshCourseCalendar() {
        courseCalendarGrid.getChildren().clear();
        courseCalendarGrid.getColumnConstraints().clear();
        courseCalendarGrid.getRowConstraints().clear();

        setupColumns(courseCalendarGrid);
        addDayHeaders(courseCalendarGrid);

        String[] times = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        for (int row = 1; row <= times.length; row++) {
            addTimeRow(courseCalendarGrid, row, times[row - 1]);
        }

        courseCalendarGrid.setPrefHeight(45 * (times.length + 1));
        courseCalendarGrid.setPrefWidth(500);
        updateTitle();
    }

    private void setupColumns(GridPane grid) {
        ColumnConstraints timeCol = new ColumnConstraints();
        timeCol.setPrefWidth(80);
        timeCol.setMinWidth(70);
        grid.getColumnConstraints().add(timeCol);

        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPrefWidth(115);
            col.setMinWidth(100);
            grid.getColumnConstraints().add(col);
        }
    }

    private void addDayHeaders(GridPane grid) {
        Label timeHeader = new Label("Time");
        timeHeader.getStyleClass().add("time-header");
        GridPane.setHgrow(timeHeader, Priority.ALWAYS);
        grid.add(timeHeader, 0, 0);

        LocalDate date = weekStart;
        for (int col = 1; col <= 7; col++) {
            VBox dayHeader = new VBox(2);
            dayHeader.setAlignment(Pos.CENTER);

            Label dayName = new Label(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            Label dayNum = new Label(date.format(DateTimeFormatter.ofPattern("dd")));

            dayName.getStyleClass().add("day-header-dayname");
            dayNum.getStyleClass().add("day-header-daynum");

            dayHeader.getChildren().addAll(dayName, dayNum);
            dayHeader.getStyleClass().add("day-header");
            GridPane.setHgrow(dayHeader, Priority.ALWAYS);

            grid.add(dayHeader, col, 0);
            date = date.plusDays(1);
        }
    }

    private void addTimeRow(GridPane grid, int row, String time) {
        RowConstraints rConst = new RowConstraints();
        rConst.setPrefHeight(45);
        rConst.setMinHeight(45);
        rConst.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().add(rConst);

        Label timeLabel = new Label(time + ":00");
        timeLabel.setPrefHeight(45);
        timeLabel.setMinHeight(45);
        timeLabel.setMaxWidth(Double.MAX_VALUE);
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.getStyleClass().add("time-label");
        GridPane.setMargin(timeLabel, new Insets(0, 0, 0, 4));

        grid.add(timeLabel, 0, row);

        for (int col = 1; col <= 7; col++) {
            StackPane cell = new StackPane();
            cell.setPrefHeight(45);
            cell.setMinHeight(45);
            cell.setMinWidth(80);
            cell.getStyleClass().add("calendar-cell");
            grid.add(cell, col, row);
        }
    }

    private void clearCourseBlocks() {
        courseCalendarGrid.getChildren().removeIf(node ->
                node.getStyleClass().contains("course-block"));
    }

    private StackPane createCourseBlock(Course course) {
        StackPane block = new StackPane();
        block.getStyleClass().add("course-block");
        block.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        block.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        Label label = new Label(
                course.subject() + " " + course.course_code() + "\n" +
                        course.title() + "\n" +
                        course.start_time() + " - " + course.end_time()
        );
        label.setWrapText(true);
        label.getStyleClass().add("course-block-label");

        block.getChildren().add(label);
        // In popup, you probably don't want clicks to open another dialog,
        // so no click handler here.
        return block;
    }

    private void renderCourseCalendar(Course course) {
        refreshCourseCalendar();

        if (course == null || course.start_date() == null || course.end_date() == null) {
            return;
        }

        LocalDate weekEnd = weekStart.plusDays(6);

        LocalDate courseStart = course.start_date();
        LocalDate courseEnd = course.end_date();

        boolean courseRunsThisWeek =
                !courseEnd.isBefore(weekStart) && !courseStart.isAfter(weekEnd);

        if (!courseRunsThisWeek) {
            return;
        }

        int dayCol = mapDayToColumn(course.day());
        if (dayCol == -1) {
            return;
        }

        int startRow = mapTimeToRow(course.start_time());
        int endRow = mapTimeToRow(course.end_time());
        if (startRow == -1 || endRow == -1) {
            return;
        }

        StackPane block = createCourseBlock(course);
        courseCalendarGrid.add(block, dayCol, startRow);
        GridPane.setRowSpan(block, Math.max(1, endRow - startRow + 1));
    }

    @FXML
    private void handlePrevWeek() {
        weekStart = weekStart.minusWeeks(1);
        refreshCourseCalendar();
        renderCourseCalendar(course);
    }

    @FXML
    private void handleNextWeek() {
        weekStart = weekStart.plusWeeks(1);
        refreshCourseCalendar();
        renderCourseCalendar(course);
    }

    @FXML
    private void handleToday() {
        weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        refreshCourseCalendar();
        renderCourseCalendar(course);
    }

    @FXML
    private void showConflict(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schedule Conflict");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean takenConflicts() {
        if (context.getCurrentUser().role() instanceof Student) {
            List<Course> already_taken = ((Student) context.getCurrentUser().role()).completed_courses();
            List<Course> enrolled = ((Student) context.getCurrentUser().role()).enrolled_courses();
            List<Course> waitlisted = ((Student) context.getCurrentUser().role()).waitlisted_courses();
            return CourseService.courseInCourses(waitlisted, course) || CourseService.courseInCourses(already_taken, course) || CourseService.courseInCourses(enrolled, course);
        }
        return false;
    }

    @FXML
    private void handleJoinWaitlist() {
        if (!course.waitlist_available()) {
            showConflict("This course does not have a waitlist");
        } else if (context.getCurrentUser().role() instanceof SignedOut) {
            showConflict("You must sign in first");
        } else if (takenConflicts()) {
            showConflict("You can't hold a course you have already taken, enrolled, or already waitlisted");
        } else {
            if (context.getCurrentUser().role() instanceof Student) {
                if (context.addUserToWaitlist(course)) {
                    try {
                        String result = MasterJSONBuilder.writeLocalToMaster(context.exportContext());
                        showSuccessAlert("Successfully registered for waitlist");
                    } catch (IOException exception) {
                        showConflict("Was able to register for waitlist but unable to save changes");
                    }
                } else {
                    showConflict("Failed to register for the waitlist");
                }
            } else {
                showConflict("Only students can register for waitlists");
            }
        }
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register for Waitlist");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @FXML
    private void handleRemoveCourse() {
        if (!(context.getCurrentUser().role() instanceof SignedOut)) {
            if (context.getCurrentUser().role() instanceof Admin) {
                try {
                    context.unregisterCourse(course);
                    showSuccessAlert("Successfully removed course");
                    Stage stage = (Stage) courseLevel.getScene().getWindow();
                    stage.close();
                    String result = MasterJSONBuilder.writeLocalToMaster(context.exportContext());
                } catch (IOException exception) {
                    showConflict("Failed to remove course");
                }
            } else {
                showConflict("Teachers don't have a schedule");
            }
        } else {
            showConflict("Sign in first");
        }

    }

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
        if (context.getRootUserType().equals(RootUserType.ADMIN)) {
            editButton.setVisible(true);
        }
    }

    @FXML
    public void handleEdit() {
        Stage stage = (Stage) courseName.getScene().getWindow();
        WindowController.showCourseEditInfoPopup(stage, context, course);
    }
}
