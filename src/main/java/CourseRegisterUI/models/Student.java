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
        List<Course> completed_courses
) implements Role {}
