package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import static CourseRegisterUI.util.UserService.getCourseRowsFromStudent;
import static CourseRegisterUI.util.UserService.getCoursesFromStudent;

public class CourseService {
    public static ObservableList<CourseRow> loadCourseRowsFromRoot(Root data) {
        ObservableList<CourseRow> rows = FXCollections.observableArrayList();

        for (Course course : data.getCourses()) {
            rows.add(new CourseRow(course));
        }

        return rows;
    }

    // Honestly these two functions can be combined into the JSONDeserializer class but this makes it easier to follow
    public static ObservableList<Course> loadCoursesForStudent(User student) {
        ObservableList<Course> rows = FXCollections.observableArrayList();
        rows.addAll(getCoursesFromStudent(student));
        return rows;
    }

    public static ObservableList<CourseRow> loadCourseRowsForStudent(User student) {
        ObservableList<CourseRow> rows = FXCollections.observableArrayList();
        rows.addAll(getCourseRowsFromStudent(student));
        return rows;
    }

    public static List<Course> coursesFromTitles(Root root, List<String> courses) {
        return root.courses().stream().filter(course -> courses.contains(course.title())).toList();
    }
}
