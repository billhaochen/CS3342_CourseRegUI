package CourseRegisterUI.controllers;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.models.Course;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.Label;

import java.io.IOException;

public class CourseInfoController {
    @FXML private Label courseName;
    @FXML private Label courseCode;
    @FXML private Label courseCRN;
    @FXML private Label professorName;
    @FXML private Label departmentName;
    @FXML private Label courseLevel;
    @FXML private Label courseCredits;
    @FXML private Label courseWebEnabled;
    @FXML private BorderPane calanderContainPane;
    public void setCourseInfo(Course course) {
        if (course == null) return;

        courseName.setText(course.title() != null ? course.title() : "Unknown Title");
        courseCode.setText(course.course_code() != null ? course.course_code() : "Unknown Code");
        courseCRN.setText(course.crn() != null ? course.crn() : "N/A");

        if (course.instructor() != null) {
            professorName.setText(course.instructor().getName());
        } else {
            professorName.setText("TBD");
        }

        departmentName.setText(course.subject() != null ? course.subject() : "N/A");
        courseLevel.setText(course.level() != null ? course.level() : "N/A");
        courseCredits.setText(course.credit() != null ? String.valueOf(course.credit()) : "0");
        courseWebEnabled.setText(course.web_enabled() != null && course.web_enabled() ? "Yes" : "No");


//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseRegisterUI/WeeklyCalendar.fxml"));
//            Parent calendarView = loader.load();
//            WeeklyCalendarController calController = loader.getController();
//            calanderContainPane.getChildren().setAll(calendarView);
//            AnchorPane.setTopAnchor(calendarView, 0.0);
//            AnchorPane.setBottomAnchor(calendarView, 0.0);
//            AnchorPane.setLeftAnchor(calendarView, 0.0);
//            AnchorPane.setRightAnchor(calendarView, 0.0);
//            calanderContainPane.setCenter(ComponentLoader.loadWeeklyCalendar());
        try{
            FXMLLoader loader = ComponentLoader.getWeeklyCalendarLoader();

            Parent calendarView = loader.load();

            WeeklyCalendarController calController = loader.getController();

            calController.setInteractive(false);
            calanderContainPane.setCenter(calendarView);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
