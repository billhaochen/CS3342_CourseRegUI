package CourseRegisterUI.models;

import javafx.beans.property.*;
import java.util.HashMap;
import java.util.Map;

public class CourseRow {
    private final Map<String, Property<?>> properties = new HashMap<>();
    private final Course course;

    public CourseRow(Course c) {
        this.course = c;
        properties.put("academic_unit", new SimpleStringProperty(c.academic_unit()));
        properties.put("subject", new SimpleStringProperty(c.subject()));
        properties.put("course_code", new SimpleStringProperty(c.course_code()));
        properties.put("title", new SimpleStringProperty(c.title()));
        properties.put("credit", new SimpleIntegerProperty(c.credit()));
        properties.put("web_enabled", new SimpleBooleanProperty(c.web_enabled()));
        properties.put("level", new SimpleStringProperty(c.level()));
        properties.put("availability", new SimpleIntegerProperty(c.availability()));
        properties.put("cap", new SimpleIntegerProperty(c.cap()));
        properties.put("waitlist_available", new SimpleBooleanProperty(c.waitlist_available()));
        properties.put("medium", new SimpleStringProperty(c.medium()));
        properties.put("meeting_time", new SimpleStringProperty(c.meeting_time()));
        properties.put("day", new SimpleStringProperty(c.day()));
        properties.put("crn", new SimpleStringProperty(c.crn()));
        properties.put("section", new SimpleStringProperty(c.section()));
//        properties.put("instructor_id", new SimpleObjectProperty<Teacher>(c.instructor_id()));
    }
    @SuppressWarnings("unchecked")
    public <T> Property<T> getProperty(String name) {
        return (Property<T>) properties.get(name);
    }

    public Course getCourse() {
        return course;
    }
}

