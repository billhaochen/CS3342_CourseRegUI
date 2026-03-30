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
        // Sample students
        List<User> exampleStudents = ExampleJSONBuilder.buildSampleStudents();

        return new Root(
                exampleStudents,
                exampleCourses
        );
    }

    public static void writeMasterToFile(File file) throws IOException {
        Root root = buildSampleMaster();
        mapper.registerSubtypes(Student.class, Teacher.class);
        mapper.writeValue(file, root);
    }

    public static void generateExamplesAndMaster() {
        File jsonDir = new File("src/main/resources/json/");
        File outputFile = new File(jsonDir, "exampleCourse.json");
        try {
            ExampleJSONBuilder.writeExampleCourseFile(outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File jsonDir2 = new File("src/main/resources/json/");
        File outputFile2 = new File(jsonDir2, "exampleStudent.json");
        try {
            ExampleJSONBuilder.writeExampleStudentFile(outputFile2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
