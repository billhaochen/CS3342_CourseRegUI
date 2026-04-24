package CourseRegisterUI.util;

public class CalendarService {
    public static int mapDayToColumn(String day) {
        if (day == null) return -1;

        return switch (day.toUpperCase()) {
            case "M" -> 1;
            case "T" -> 2;
            case "W" -> 3;
            case "R" -> 4;
            case "F" -> 5;
            case "S" -> 6;
            case "U" -> 7;
            default -> -1;
        };
    }

    public static int mapTimeToRow(String time) {
        if (time == null || time.isBlank()) return -1;

        java.time.LocalTime localTime = java.time.LocalTime.parse(time);
        return localTime.getHour() - 8 + 1;
    }
}
