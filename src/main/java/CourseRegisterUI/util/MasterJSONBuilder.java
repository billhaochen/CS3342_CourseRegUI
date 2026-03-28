package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MasterJSONBuilder {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).registerModule(new JavaTimeModule());

    public static Root buildSampleMaster() {
        // Sample courses
        List<Course> exampleCourses = ExampleJSONBuilder.buildSampleCourses();
//
//        // Sample users
//        User s1 = new User(
//                "u1",
//                "Alice",
//                new StudentRole("s001", 3.8)
//        );
//
//        User t1 = new User(
//                "u2",
//                "Bob",
//                new TeacherRole("t001", "MWF 2–3pm")
//        );

        return new Root(
                List.of(),
                exampleCourses
        );
    }

    public static void writeMasterToFile(File file) throws IOException {
        Root root = buildSampleMaster();
        mapper.writeValue(file, root);
    }
}
