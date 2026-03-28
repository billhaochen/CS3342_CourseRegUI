package CourseRegisterUI.util;

import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JSONDeserializer {
    public static Root JSONToRoot(String filePath) {
        List<User> users = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(filePath));
            JsonNode usersNode = root.path("users");
            JsonNode coursesNode = root.path("courses");
            List<Course> read_courses = objectMapper.convertValue(
                    coursesNode,
                    new TypeReference<List<Course>>() {}
            );

            List<User> read_users = objectMapper.convertValue(
                    usersNode,
                    new TypeReference<List<User>>() {}
            );
            users.addAll(read_users);
            courses.addAll(read_courses);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Root(users, courses);
    }
}
