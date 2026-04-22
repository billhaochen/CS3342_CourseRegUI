package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.util.List;

@JsonTypeName("student")
public record Student(
        String name,
        String surname,
        String first_name,
        String student_id,
        String student_eid,
        String password,
        College college,
        String degree,
        Status status,
        Program program,
        String location,
        LocalDate start_date,
        LocalDate end_date,
        List<Course> enrolled_courses,
        List<Course> completed_courses,
        Major major
) implements Role {
    public Student {
        enrolled_courses = enrolled_courses == null ? List.of() : enrolled_courses;
        completed_courses = completed_courses == null ? List.of() : completed_courses;
    }

    public static Student factoryCreate(
            String name,
            String surname,
            String firstName,
            String studentId,
            String studentEid,
            String password
    ) {
        return new Student(
                name,
                surname,
                firstName,
                studentId,
                studentEid,
                password,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(),
                List.of(),
                null
        );
    }
    public String idValue() {
        return student_id;
    }
    public String passwordValue() {
        return password;
    }

    public String eidValue() {
        return student_eid;
    }

}
