package CourseRegisterUI.util;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
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
}
