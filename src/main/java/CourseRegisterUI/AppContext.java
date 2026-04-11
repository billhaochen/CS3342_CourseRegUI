package CourseRegisterUI;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.CourseRow;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.JSONDeserializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AppContext {
    private Root courseUserRepository;
    private User currentUser;
    private final ObservableList<CourseRow> courseRows = FXCollections.observableArrayList();

    private final ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
    private final ObservableList<CourseRow> selectedCourseRows= FXCollections.observableArrayList();

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
    }

    public void loadInitialData() {
        this.courseUserRepository = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-04-11_14-29-27-641.json");
        courseRows.setAll(CourseService.loadCourseRowsFromRoot(this.courseUserRepository));
    }

    public ObservableList<CourseRow> getCourseRows() {
        return courseRows;
    }

    public Root getCourseRepository() {
        return courseUserRepository;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ObservableList<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public ObservableList<CourseRow> getSelectedCourseRows() {
        return selectedCourseRows;
    }

    public void clearSelectedCourses() {
        selectedCourses.clear();
    }

}
