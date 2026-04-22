package CourseRegisterUI.controllers;
import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.util.LoadedView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import javafx.scene.control.Label;
import javafx.stage.Window;

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
    @FXML private Label courseName;
    @FXML private Label courseCode;
    @FXML private Label courseCRN;
    @FXML private Label professorName;
    @FXML private Label departmentName;
    @FXML private Label courseLevel;
    @FXML private Label courseCredits;
    @FXML private Label courseWebEnabled;
    @FXML private BorderPane calanderContainPane;
    @FXML private ScrollPane mainScroll;
    @FXML private GridPane mainGrid;  // Single grid!
    private boolean gridInteractive = false;

    @FXML private Label weekTitle;
    @FXML private Button prevWeek, nextWeek, todayBtn;
    private StackPane selectedCell;

    private AppContext context;
    private LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
    private Course course;
    private LoadedView<WeeklyCalendarController> calendarView;

    @Override
    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

    @FXML
    public void initialize() {
    }


    public void setCourseInfo(Course course) {
        this.course = course;
        if (course == null) return;
        courseName.setText(course.title() != null ? course.title() : "Unknown Title");
        courseCode.setText(course.course_code() != null ? course.course_code() : "Unknown Code");
        courseCRN.setText(course.crn() != null ? course.crn() : "N/A");

        if (course.instructor() != null) {
            professorName.setText(course.instructor().idValue());
        } else {
            professorName.setText("TBD");
        }

        departmentName.setText(course.subject() != null ? course.subject() : "N/A");
        courseLevel.setText(course.level() != null ? course.level() : "N/A");
        courseCredits.setText(course.credit() != null ? String.valueOf(course.credit()) : "0");
        courseWebEnabled.setText(course.web_enabled() != null && course.web_enabled() ? "Yes" : "No");

        try{
            calendarView = ComponentLoader.loadWeeklyCalendar();
            calanderContainPane.setCenter(calendarView.view());
            calendarView.controller().setAppContext(context);
            calendarView.controller().setGridInteractive(false);
            calendarView.controller().displayCourse(course);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
