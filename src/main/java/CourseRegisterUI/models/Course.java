package CourseRegisterUI.models;

import java.time.LocalDate;
import java.util.ArrayList;
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
        String instructor_id,
        String medium, // enum
        String meeting_time,
        List<Course> prerequisites,
        List<String> waitlist
) {
    public void setWaitlist(List<String> users) {
        this.waitlist.clear();
        this.waitlist.addAll(users);
    }
    public void setPrerequisites(List<Course> courses) {
        this.prerequisites.clear();
        this.prerequisites.addAll(courses);
    }

    // in Course.java
    public static Course factoryMinimal(
            String academicUnit,
            String subject,
            String courseCode,
            String title,
            College college,
            Integer credit,
            String campus,
            String level,
            LocalDate startDate,
            LocalDate endDate,
            String startTime,
            String endTime,
            String day
    ) {
        return new Course(
                academicUnit,
                subject,
                courseCode,
                title,
                college,
                /* crn        */ null,
                /* section    */ null,
                credit,
                campus,
                /* webEnabled */ Boolean.TRUE,
                level,
                /* availability       */ null,
                /* cap                */ null,
                /* waitlistAvailable  */ Boolean.FALSE,
                startDate,
                endDate,
                startTime,
                endTime,
                day,
                /* building    */ null,
                /* room        */ null,
                /* instructorId*/ null,
                /* medium      */ "English",
                /* meetingTime */ buildMeetingTime(startTime, endTime, day),
                /* prerequisites */ new ArrayList<>(),
                /* waitlist      */ new ArrayList<>()
        );
    }

    private static String buildMeetingTime(String startTime, String endTime, String day) {
        if (startTime == null || endTime == null || day == null) {
            return "";
        }
        return day + " " + startTime + "-" + endTime;
    }
}
