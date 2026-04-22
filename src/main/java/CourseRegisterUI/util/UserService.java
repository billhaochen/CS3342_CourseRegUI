package CourseRegisterUI.util;

import CourseRegisterUI.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    public static String assignStudentEid(Root root, String full_name) {
        List<User> students = root.users().stream().filter(s -> s.role() instanceof Student).toList();
        List<User> existing_students_with_name = students.stream()
                .filter(student_user -> ((Student) student_user.role()).name().equals(full_name)).toList();

        if (!existing_students_with_name.isEmpty()) {
            String last_eid = existing_students_with_name.getLast().getEID();
            int new_eid = Integer.parseInt(last_eid + 1);
            return full_name.toLowerCase().replace(" ", "") + new_eid;
        } else {
            return full_name.toLowerCase().replace(" ", "") + "1";
        }
    }

    public static Optional<User> getStudentByName(Root root, String full_student_name) {
        // This is the Java equivalent of the TypeScript approach for filtering by a predicate
        // Will have to implement ternary operators/null handling later when invoking this function
        List<User> students = root.users().stream().filter(s -> s.role() instanceof Student).toList();

        return students.stream()
                .filter(student_user -> ((Student) student_user.role()).name().equals(full_student_name))
                .findFirst();
    }

    public static Optional<User> getStudentByID(Root root, String student_id) {
        // This is the Java equivalent of the TypeScript approach for filtering by a predicate
        // Will have to implement ternary operators/null handling later when invoking this function
        List<User> students = root.users().stream().filter(s -> s.role() instanceof Student).toList();
        return students.stream()
                .filter(student_user -> ((Student) student_user.role()).student_id().equals(student_id))
                .findFirst();

    }

    public static List<Course> getCoursesFromStudent(User student) {
        List<Course> resulting_courses = new ArrayList<>();
        if (student.role() instanceof Student) {
            List<Course> read_courses = ((Student) student.role()).enrolled_courses();
            resulting_courses.addAll(read_courses);
        }
        return resulting_courses;
    }

    public static List<CourseRow> getCourseRowsFromStudent(User student) {
        List<CourseRow> resulting_courses = new ArrayList<>();
        for (Course course : getCoursesFromStudent(student)) {
            resulting_courses.add(new CourseRow(course));
        }
        return resulting_courses;
    }
}
