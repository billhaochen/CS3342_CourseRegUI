package CourseRegisterUI.models;

import javafx.beans.property.*;
import java.util.HashMap;
import java.util.Map;

public class CourseRow {
    private final Map<String, Property<?>> properties = new HashMap<>();
    private final Course course;

    public CourseRow(Course c) {
        this.course = c;

        String academicUnit = c.academic_unit();
        String subject = c.subject();
        String courseCode = c.course_code();
        String title = c.title();
        Integer credit = c.credit();
        Boolean webEnabled = c.web_enabled();
        String level = c.level();
        Integer availability = c.availability();
        Integer cap = c.cap();
        Boolean waitlistAvailable = c.waitlist_available();
        String medium = c.medium();
        String meetingTime = c.meeting_time();
        String day = c.day();
        String crn = c.crn();
        String section = c.section();

        properties.put("academic_unit",
                new SimpleStringProperty(academicUnit == null ? "" : academicUnit));
        properties.put("subject",
                new SimpleStringProperty(subject == null ? "" : subject));
        properties.put("course_code",
                new SimpleStringProperty(courseCode == null ? "" : courseCode));
        properties.put("title",
                new SimpleStringProperty(title == null ? "" : title));

        properties.put("credit",
                new SimpleIntegerProperty(credit == null ? 0 : credit));
        properties.put("web_enabled",
                new SimpleBooleanProperty(webEnabled != null && webEnabled));
        properties.put("level",
                new SimpleStringProperty(level == null ? "" : level));
        properties.put("availability",
                new SimpleIntegerProperty(availability == null ? 0 : availability));
        properties.put("cap",
                new SimpleIntegerProperty(cap == null ? 0 : cap));
        properties.put("waitlist_available",
                new SimpleBooleanProperty(waitlistAvailable != null && waitlistAvailable));
        properties.put("medium",
                new SimpleStringProperty(medium == null ? "" : medium));
        properties.put("meeting_time",
                new SimpleStringProperty(meetingTime == null ? "" : meetingTime));
        properties.put("day",
                new SimpleStringProperty(day == null ? "" : day));
        properties.put("crn",
                new SimpleStringProperty(crn == null ? "" : crn));
        properties.put("section",
                new SimpleStringProperty(section == null ? "" : section));
    }
    @SuppressWarnings("unchecked")
    public <T> Property<T> getProperty(String name) {
        return (Property<T>) properties.get(name);
    }

    public Course getCourse() {
        return course;
    }
}

