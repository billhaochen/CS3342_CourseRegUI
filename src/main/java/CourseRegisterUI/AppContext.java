package CourseRegisterUI;

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

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
    }

    public void loadInitialData() {
        this.courseUserRepository = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-03-30_13-24-32-630.json");
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

}
