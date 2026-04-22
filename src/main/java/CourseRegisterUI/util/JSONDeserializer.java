package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONDeserializer {
    public static File findLatestMasterExportFile() {
        File jsonDir = new File("src/main/resources/json/");

        File[] matchingFiles = jsonDir.listFiles((dir, name) ->
                name.startsWith("master_export_") && name.endsWith(".json")
        );

        if (matchingFiles == null || matchingFiles.length == 0) {
            return null;
        }

        return java.util.Arrays.stream(matchingFiles)
                .max(java.util.Comparator.comparing(File::getName))
                .orElse(null);
    }
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
}
