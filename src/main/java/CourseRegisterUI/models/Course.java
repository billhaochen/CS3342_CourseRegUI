package CourseRegisterUI.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record Course(
        String academic_unit,
        String subject,
        String course_code,
        String title,
        College college,
        String crn,
        String section, // singular but for searches can aggregate to a list
        Integer credit,
        String campus, // enum
        Boolean web_enabled,
        String level, // enum
        Integer availability,
        Integer cap,
        Boolean waitlist_available,
        LocalDate start_date,
        LocalDate end_date,
        String start_time,
        String end_time,
        String day,
        String building, // enum
        String room,
        Teacher instructor,
        String medium, // enum
        String meeting_time,
        List<Course> prerequisites,
        List<String> waitlist
) {
    public void setWaitlist(List<String> users) {
        this.waitlist.addAll(users);
    }
}
