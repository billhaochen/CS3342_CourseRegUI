package CourseRegisterUI.models;

import javafx.beans.property.*;
import java.util.HashMap;
import java.util.Map;

public class CourseRow {
    private final Map<String, Property<?>> properties = new HashMap<>();

    public CourseRow(Course c) {
        properties.put("academic_unit", new SimpleStringProperty(c.academic_unit()));
        properties.put("subject", new SimpleStringProperty(c.subject()));
        properties.put("course_code", new SimpleStringProperty(c.course_code()));
        properties.put("title", new SimpleStringProperty(c.title()));
        properties.put("credit", new SimpleIntegerProperty(c.credit()));
        properties.put("medium", new SimpleStringProperty(c.medium()));
    }

    @SuppressWarnings("unchecked")
    public <T> Property<T> getProperty(String name) {
        return (Property<T>) properties.get(name);
    }
}