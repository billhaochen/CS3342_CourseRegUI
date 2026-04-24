package CourseRegisterUI.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarServiceTest {

    @Test
    void mapDayToColumn_mapsValidDaysIgnoringCase() {
        assertEquals(1, CalendarService.mapDayToColumn("M"));
        assertEquals(2, CalendarService.mapDayToColumn("t"));
        assertEquals(3, CalendarService.mapDayToColumn("W"));
        assertEquals(4, CalendarService.mapDayToColumn("r"));
        assertEquals(5, CalendarService.mapDayToColumn("F"));
        assertEquals(6, CalendarService.mapDayToColumn("S"));
        assertEquals(7, CalendarService.mapDayToColumn("u"));
    }

    @Test
    void mapDayToColumn_returnsMinusOneForNullOrInvalidInput() {
        assertEquals(-1, CalendarService.mapDayToColumn(null));
        assertEquals(-1, CalendarService.mapDayToColumn("X"));
        assertEquals(-1, CalendarService.mapDayToColumn("Mon"));
    }

    @Test
    void mapTimeToRow_returnsExpectedRows() {
        assertEquals(1, CalendarService.mapTimeToRow("08:00"));
        assertEquals(2, CalendarService.mapTimeToRow("09:00"));
        assertEquals(12, CalendarService.mapTimeToRow("19:00"));
    }

    @Test
    void mapTimeToRow_returnsMinusOneForNullOrBlank() {
        assertEquals(-1, CalendarService.mapTimeToRow(null));
        assertEquals(-1, CalendarService.mapTimeToRow(""));
        assertEquals(-1, CalendarService.mapTimeToRow("   "));
    }

    @Test
    void mapTimeToRow_throwsForInvalidTimeFormat() {
        assertThrows(Exception.class, () -> CalendarService.mapTimeToRow("not-a-time"));
    }
}
