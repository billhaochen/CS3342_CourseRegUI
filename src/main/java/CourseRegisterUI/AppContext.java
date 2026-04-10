package CourseRegisterUI;

import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.JSONDeserializer;

import java.util.ArrayList;

public class AppContext {
    private Root courseUserRepository;
    private User currentUser;

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
    }

    public void loadInitialData() {
        this.courseUserRepository = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-03-30_13-24-32-630.json");
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
