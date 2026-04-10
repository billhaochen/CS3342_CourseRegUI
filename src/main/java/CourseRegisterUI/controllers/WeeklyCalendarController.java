package CourseRegisterUI.controllers;

import CourseRegisterUI.models.Course;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Window;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;


public class WeeklyCalendarController {
    @FXML private ScrollPane mainScroll;
    @FXML private GridPane mainGrid;  // Single grid!
    @FXML private Label weekTitle;
    @FXML private Button prevWeek, nextWeek, todayBtn;

    private LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
    private boolean gridInteractive = true;
    @FXML public void initialize() {
//        mainScroll.setStyle("-fx-scrollbar-width: 8; -fx-background-color: transparent;");
        mainScroll.getStyleClass().add("main-scroll");
        mainScroll.setFitToWidth(true);
        mainScroll.setPannable(true);  // Smooth scroll with mouse drag
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Platform.runLater(() -> {
            refreshCalendar();
            // FIXED: Set explicit height so ScrollPane knows content size
            mainGrid.setPrefHeight(500);  // Minimum visible height
            mainScroll.requestLayout();
        });
    }

    private void updateTitle() {
        LocalDate end = weekStart.plusDays(6);
        weekTitle.setText(weekStart.format(DateTimeFormatter.ofPattern("MMM dd")) +
                " - " + end.format(DateTimeFormatter.ofPattern("MMM dd")));
    }

    private void refreshCalendar() {
        mainGrid.getChildren().clear();
        mainGrid.getColumnConstraints().clear();
        mainGrid.getRowConstraints().clear();

        setupColumns();
        addDayHeaders();

        String[] times = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        for (int row = 1; row <= times.length; row++) {
            addTimeRow(row, times[row-1]);
        }

        // CRITICAL: Set explicit size so ScrollPane works
        mainGrid.setPrefHeight(45 * (times.length + 1));  // Header + 12 rows
        mainGrid.setPrefWidth(800);  // Minimum width
        updateTitle();
    }

    private void addTimeRow(int row, String time) {
        RowConstraints rConst = new RowConstraints();
        rConst.setPrefHeight(45);
        rConst.setMinHeight(45);
        rConst.setVgrow(Priority.ALWAYS);

        mainGrid.getRowConstraints().add(rConst);

        // FIXED: Add LEFT padding + margin
        Label timeLabel = new Label(time + ":00");
        timeLabel.setPrefHeight(45);
        timeLabel.setMinHeight(45);
        timeLabel.setMaxWidth(Double.MAX_VALUE);
        timeLabel.setAlignment(Pos.CENTER);
//        timeLabel.setStyle("""
//    -fx-background-color: #f8f9fa;
//    -fx-border-color: #dee2e6 #dee2e6 #dee2e6 #dee2e6;
//    -fx-border-width: 1;
//    -fx-padding: 8 8 8 12;
//    -fx-font-size: 12;
//    """);
        timeLabel.getStyleClass().add("time-label");

        GridPane.setMargin(timeLabel, new Insets(0, 0, 0, 4));  // ← Extra 4px margin
        mainGrid.add(timeLabel, 0, row);

        // Cells (unchanged)
        for (int col = 1; col <= 7; col++) {
            StackPane cell = createScheduleCell(weekStart.plusDays(col-1), row-1);
            cell.setPrefHeight(45);
            cell.setMinHeight(45);
            mainGrid.add(cell, col, row);
        }
    }


    private void setupColumns() {
        // FIXED: Wider time column (80px)
        ColumnConstraints timeCol = new ColumnConstraints();
        timeCol.setPrefWidth(80);  // ← Increased from 60px
        timeCol.setMinWidth(70);
        mainGrid.getColumnConstraints().add(timeCol);

        // 7 days (adjust to fit remaining space)
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPrefWidth(115);  // ← Slightly smaller days
            col.setMinWidth(100);
            mainGrid.getColumnConstraints().add(col);
        }
    }

    private void addDayHeaders() {
        // Time header
        Label timeHeader = new Label("Time");
//        timeHeader.setStyle("-fx-background-color: #495057; -fx-text-fill: white; -fx-padding: 12 8; -fx-font-weight: bold; -fx-font-size: 12;");
        timeHeader.getStyleClass().add("time-header");
        GridPane.setHgrow(timeHeader, Priority.ALWAYS);
        mainGrid.add(timeHeader, 0, 0);

        // Day headers
        LocalDate date = weekStart;
        for (int col = 1; col <= 7; col++) {
            VBox dayHeader = new VBox(2);
            dayHeader.setAlignment(Pos.CENTER);

            Label dayName = new Label(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            Label dayNum = new Label(date.format(DateTimeFormatter.ofPattern("dd")));

//            dayName.setStyle("-fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: white;");
//            dayNum.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
            dayName.getStyleClass().add("day-header-dayname");
            dayNum.getStyleClass().add("day-header-daynum");

            dayHeader.getChildren().addAll(dayName, dayNum);
//            dayHeader.setStyle("-fx-background-color: #007bff; -fx-padding: 6; -fx-border-color: #dee2e6; -fx-border-width: 0 0 1 1;");
            dayHeader.getStyleClass().add("day-header");
            GridPane.setHgrow(dayHeader, Priority.ALWAYS);

            mainGrid.add(dayHeader, col, 0);
            date = date.plusDays(1);
        }
    }

    private StackPane createScheduleCell(LocalDate date, int hour) {
        StackPane cell = new StackPane();
        cell.setPrefHeight(45);
        cell.setMinHeight(45);
        cell.setMinWidth(80);

        final int finalHour = hour;
        final LocalDate finalDate = date;

        // ✅ FULL BORDER ON ALL STATES (top, right, bottom, left)
        String baseStyle = """
        -fx-background-color: white;
        -fx-border-color: #dee2e6 #dee2e6 #dee2e6 #dee2e6;
        -fx-border-width: 1;
        -fx-padding: 2;
        """;

//        cell.setStyle(baseStyle);
        cell.getStyleClass().add("calendar-cell");

//        cell.setOnMouseEntered(e ->
//                cell.setStyle(baseStyle.replace("white", "#f8f9fa"))
//        );
//
//        cell.setOnMouseExited(e ->
//                cell.setStyle(baseStyle)
//        );

        cell.setOnMouseClicked(e -> {
            if(gridInteractive){
                System.out.println("Clicked: " + finalDate + " " + finalHour + ":00");
                Course c = getCourseAt(finalDate,finalHour);
                if(c!=null){
                    Window owner = mainScroll.getScene().getWindow();
                    WindowController.requestCourseInfo(owner,c);
                }
                //Test
                Window owner = mainScroll.getScene().getWindow();
                WindowController.requestCourseInfo(owner,c);

                String selectedStyle = baseStyle.replace("white", "#cce7ff")
                        .replace("#dee2e6", "#007bff")
                        .replace("1;", "2;");
                cell.setStyle(selectedStyle);
//            cell.getStyleClass().add("calendar-cell:selected");
            }

        });

        return cell;
    }
    //to get the course information at certain time?
    public Course getCourseAt(LocalDate d, int t){
        //TODO
        //test
        return getTestCourse();


//        return null;
    }
    private Course getTestCourse(){
        Course testCourse = new Course(
                "Engineering",      // academic_unit
                "CS",               // subject
                "3402",             // course_code
                "Database Systems", // title
                null,               // college
                "12345",            // crn
                "A01",              // section
                3,                  // credit
                "Main Campus",      // campus
                true,               // web_enabled
                "Undergraduate",    // level
                30, 60, true,       // availability, cap, waitlist
                LocalDate.now(),    // start_date
                LocalDate.now().plusMonths(4), // end_date
                "10:00", "11:30",   // start/end time
                "MWF",              // day
                "Building A", "101",// building, room
                null,               // instructor
                "English"           // medium
        );
        return testCourse;
    }

    public void setInteractive(boolean b) {
        gridInteractive = b;
    }

}
