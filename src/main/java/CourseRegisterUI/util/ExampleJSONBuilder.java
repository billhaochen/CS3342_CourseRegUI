package CourseRegisterUI.util;

import CourseRegisterUI.models.College;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleJSONBuilder {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT).registerModule(new JavaTimeModule());

    public static List<Course> buildSampleCourses() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course(
          "Accountancy",
                "AC",
                "3202",
                "Corporate Accounting I",
                College.COLLEGE_OF_BUSINESS,
                "10857",
                "S01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                4,
                20,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "15:00",
                "17:50",
                "R",
                "YEUNG",
                "B4702",
                new Teacher("Kim Tae Wook"),
                "English"
        );
        Course c2 = new Course(
                "Computer Science",
                "CS",
                "1102",
                "Introduction to Computer Studies",
                College.COLLEGE_OF_COMPUTING,
                "10342",
                "C01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                0,
                120,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "16:00",
                "18:50",
                "T",
                "LI",
                "6606",
                new Teacher("Liu Chen"),
                "English"
        );
        Course c3 = new Course(
                "Chemistry",
                "CHEM",
                "1101",
                "Introduction to Chemistry",
                College.COLLEGE_OF_LIFE_SCIENCES,
                "11760",
                "C01",
                3,
                "Main Campus",
                Boolean.TRUE,
                "B",
                20,
                40,
                Boolean.FALSE,
                LocalDate.of(2026, 1, 12),
                LocalDate.of(2026, 4, 18),
                "09:00",
                "10:50",
                "R",
                "YEUNG",
                "LT-6",
                new Teacher("Chan Michael C."),
                "English"
        );
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        return courses;
    }

    public static void writeExampleCourseFile(File file) throws IOException {
        mapper.writeValue(file, buildSampleCourses());
    }
}
