package CourseRegisterUI.util;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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
        rows.addAll(JSONDeserializer.getCoursesFromStudent(student));
        return rows;
    }

    public static ObservableList<CourseRow> loadCourseRowsForStudent(User student) {
        ObservableList<CourseRow> rows = FXCollections.observableArrayList();
        rows.addAll(JSONDeserializer.getCourseRowsFromStudent(student));
        return rows;
    }

    /**
     * this is going to assume that every course here is in the same semester for now
     * @param a
     * @param b
     * @return
     */
    public static boolean conflicts(Course a, Course b) {
        boolean dateOverlap = !a.end_date().isBefore(b.start_date())
                && !b.end_date().isBefore(a.start_date());

        boolean sameDay = a.day() != null && a.day().equals(b.day());

        boolean timeOverlap = a.start_time().compareTo(b.end_time()) < 0
                && b.start_time().compareTo(a.end_time()) < 0;

        return dateOverlap && sameDay && timeOverlap;
    }

    public static boolean validateCourses(ObservableList<CourseRow> rows) {
        List<Course> courses = rows.stream()
                .map(CourseRow::getCourse)
                .toList();

        for (int i = 0; i < courses.size(); i++) {
            for (int j = i + 1; j < courses.size(); j++) {
                if (conflicts(courses.get(i), courses.get(j))) {
                    return false;
                }
            }
        }
        // TODO account for other validation like program and pre-requisite validation
        return true;
    }
}
