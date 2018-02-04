package services;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class used to create an object of class Calendar from String
 */
public class CalendarObjectCreator {
    public static Calendar createCalendarObject(String date) {
        String[] lines = date.split(".");
        return new GregorianCalendar(Integer.parseInt(lines[2]),Integer.parseInt(lines[1]),Integer.parseInt(lines[0]));
    }
}
