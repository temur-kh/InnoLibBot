package services;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarObjectCreator {
    public static Calendar createCalendarObject(String date) {
        String[] lines = date.split(".");
        return new GregorianCalendar(Integer.parseInt(lines[2]),Integer.parseInt(lines[1]),Integer.parseInt(lines[0]));
    }
}
