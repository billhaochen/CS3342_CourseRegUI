package CourseRegisterUI.util;

import CourseRegisterUI.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MasterJSONBuilder {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static Root buildSampleMaster() {
        // Sample courses
//        Course c1 = new Course("c1", "Intro to Java", 30);
//        Course c2 = new Course("c2", "Data Structures", 25);
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
                List.of()
        );
    }

    public static void writeMasterToFile(File file) throws IOException {
        Root root = buildSampleMaster();
        mapper.writeValue(file, root);
    }
}
