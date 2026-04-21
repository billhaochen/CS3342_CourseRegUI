package CourseRegisterUI;

import CourseRegisterUI.models.*;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.JSONDeserializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;

public class AppContext {
    private Root courseUserRepository;
    private User currentUser;
    private boolean isAdmin;
    private final ObservableList<CourseRow> courseRows = FXCollections.observableArrayList();

    private final ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
    private final ObservableList<CourseRow> selectedCourseRows= FXCollections.observableArrayList();
    private final FilteredList<CourseRow> filteredCourseRows;

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
        this.currentUser = new User("", "", new SignedOut());
        this.filteredCourseRows = new FilteredList<>(selectedCourseRows, null);
    }

    public void loadInitialData() {
        this.courseUserRepository = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-04-11_16-38-16-535.json");
        courseRows.setAll(CourseService.loadCourseRowsFromRoot(this.courseUserRepository));
        selectedCourses.setAll(CourseService.loadCoursesForStudent(getCurrentUser()));
        selectedCourseRows.setAll(CourseService.loadCourseRowsForStudent(getCurrentUser()));
    }

    public ObservableList<CourseRow> getCourseRows() {
        return courseRows;
    }

    public Root getCourseRepository() {
        return courseUserRepository;
    }

    public void setCurrentUser(String full_name, String id) {
        User new_user = this.courseUserRepository.users().stream().filter(
                user -> user.id().equals(id) && user.name().equals(full_name)
        ).findFirst().orElse(null);

        if (new_user != null) {
            this.currentUser = new_user;
            reloadData();
        } else {
            System.out.println("There was an issue with finding the credentials in the list of Users ");
        }
    }

    private void reloadData() {
        selectedCourses.setAll(CourseService.loadCoursesForStudent(getCurrentUser()));
        selectedCourseRows.setAll(CourseService.loadCourseRowsForStudent(getCurrentUser()));
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

    public boolean isAdmin() { return this.isAdmin; }

    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public FilteredList<CourseRow> getFilteredCourseRows() { return filteredCourseRows; }

}
