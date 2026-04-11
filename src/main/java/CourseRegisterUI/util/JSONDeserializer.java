package CourseRegisterUI.util;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONDeserializer {
    public static Root JSONToRoot(String filePath) {
        List<User> users = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            JsonNode root = objectMapper.readTree(new File(filePath));
            JsonNode usersNode = root.path("users");
            JsonNode coursesNode = root.path("courses");
            List<Course> read_courses = objectMapper.convertValue(
                    coursesNode,
                    new TypeReference<List<Course>>() {
                    }
            );

            List<User> read_users = objectMapper.convertValue(
                    usersNode,
                    new TypeReference<List<User>>() {
                    }
            );
            users.addAll(read_users);
            courses.addAll(read_courses);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Root(users, courses);
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

}
