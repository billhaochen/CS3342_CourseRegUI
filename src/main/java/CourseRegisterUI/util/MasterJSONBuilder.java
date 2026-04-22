package CourseRegisterUI.util;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static void writeMasterToFile(File file, Root root) throws IOException {
        mapper.registerSubtypes(Student.class, Teacher.class);
        mapper.writeValue(file, root);
    }

    public static String writeLocalToMaster(Root new_root) throws IOException {

        // Create directory with current year/month for organization
        LocalDateTime now = LocalDateTime.now();
        File jsonDir = new File("src/main/resources/json/");

        // Create directory if it doesn't exist
        if (!jsonDir.exists()) {
            jsonDir.mkdirs();
        }

        // Generate detailed timestamp filename
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        String timestamp = now.format(formatter);
        String filename = "master_export_" + timestamp + ".json";

        File outputFile = new File(jsonDir, filename);

        // Show a progress indicator (optional)
        // You could show a loading dialog here if export takes time

        MasterJSONBuilder.writeMasterToFile(outputFile, new_root);
        // Success message with file info
        String successMessage = String.format(
                "File exported successfully!\n\n" +
                        "Filename: %s\n" +
                        "Location: %s\n" +
                        "Size: %d bytes\n" +
                        "Date: %s",
                filename,
                outputFile.getAbsolutePath(),
                outputFile.length(),
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        return successMessage;
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
