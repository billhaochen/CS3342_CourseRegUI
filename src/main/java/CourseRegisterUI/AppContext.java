package CourseRegisterUI;

import CourseRegisterUI.models.*;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.JSONDeserializer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppContext {
    private Root courseUserRepository;

    private boolean isAdmin;
    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
    private final ObservableList<CourseRow> courseRows = FXCollections.observableArrayList();
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Course> master_list_courses = FXCollections.observableArrayList();

    private final ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
    private final ObservableList<CourseRow> selectedCourseRows = FXCollections.observableArrayList();
    private final FilteredList<CourseRow> filteredCourseRows;

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
        this.currentUser.set(new User("", "", new SignedOut()));
        this.filteredCourseRows = new FilteredList<>(selectedCourseRows, null);
    }

    public void loadInitialData() {
        String latest_sample_file = "src/main/resources/json/master_export_2026-04-14_12-53-17-378.json";
        File latest_created_master = JSONDeserializer.findLatestMasterExportFile();
        this.courseUserRepository = JSONDeserializer.JSONToRoot(latest_sample_file);
        if (latest_created_master != null) {
            if (!latest_created_master.getPath().isBlank()) {
                this.courseUserRepository = JSONDeserializer.JSONToRoot(latest_created_master.getPath());
            }
        }


        courseRows.setAll(CourseService.loadCourseRowsFromRoot(this.courseUserRepository));
        selectedCourses.setAll(CourseService.loadCoursesForStudent(getCurrentUser()));
        selectedCourseRows.setAll(CourseService.loadCourseRowsForStudent(getCurrentUser()));
        users.addAll(courseUserRepository.users());
        master_list_courses.addAll(courseUserRepository.courses());
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
            this.currentUser.set(new_user);
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
        return currentUser.get();
    }

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }

    public void registerCourses() {
        User user = currentUser.get();

        if (user.role() instanceof Student student) {
            List<Course> updatedCourses = new ArrayList<>(student.enrolled_courses());
            updatedCourses.addAll(selectedCourses);

            Student updatedStudent = new Student(
                    student.name(),
                    student.surname(),
                    student.first_name(),
                    student.student_id(),
                    student.student_eid(),
                    student.password(),
                    student.college(),
                    student.degree(),
                    student.status(),
                    student.program(),
                    student.location(),
                    student.start_date(),
                    student.end_date(),
                    updatedCourses,
                    student.completed_courses()
            );

            User updatedUser = new User(
                    user.id(),
                    user.name(),
                    updatedStudent
            );

            users.remove(user);
            users.add(updatedUser);

            currentUser.set(updatedUser);
        }
    }

    public void addNewUser(User user) {
        users.add(user);
    }

    public Root exportContext() {
        return new Root(this.users, this.master_list_courses);
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

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public FilteredList<CourseRow> getFilteredCourseRows() {
        return filteredCourseRows;
    }

}
