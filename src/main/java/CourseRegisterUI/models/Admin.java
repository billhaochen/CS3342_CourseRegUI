package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("admin")
public record Admin(
        // TODO implement later
        String name,
        String password,
        String id,
        String email,
        String phone,
        String title,
        String eid,
        List<Course> courses

) implements Role {

     // So Jackson doesn't write a new ID field in here
    public String idValue() {
        return id;
    }

    public String passwordValue() {
        return password;
    }

    public String eidValue() {
        return eid;
    }

    public void setCourses(List<Course> courses) {
        this.courses.clear();
        this.courses.addAll(courses);
    }
}
