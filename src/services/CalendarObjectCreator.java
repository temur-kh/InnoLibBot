package services;

import org.omg.CORBA.INTERNAL;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class used to create an object of class Calendar from String
 */
public class CalendarObjectCreator {
    public static Calendar createCalendarObject(String date) {
        String[] lines = date.split(Constants.DASH);
        return new GregorianCalendar(Integer.parseInt(lines[2]), Integer.parseInt(lines[1]), Integer.parseInt(lines[0]));
    }

    public static String createCalendarLine(Calendar calendar) {
        String date = "";
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        date += (day.length() == 1 ? Constants.ZERO + day : day) + Constants.DASH;
        date += (month.length() == 1 ? Constants.ZERO + month : month) + Constants.DASH;
        date += (year.length() == 1 ? Constants.ZERO + year : year);
        return date;
    }
}
