package CourseRegisterUI.models;

import java.util.List;

public record Root(
        List<User> users,
        List<Course> courses
        ) {
}
