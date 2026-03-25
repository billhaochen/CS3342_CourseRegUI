package CourseRegisterUI.models;

import java.util.Date;

public record Course(
        // TODO fill in later
        String academic_unit,
        String subject,
        String course_code,
        String title,

        String crn,
        String section,
        Integer credit,
        String campus,
        Boolean web_enabled,
        String level,
        Integer availability,
        Integer cap,
        Boolean waitlist_available,
        Date start_date,
        Date end_date,
        String day,
        String building,
        String room,
        Teacher instructor,
        String medium
) {}
