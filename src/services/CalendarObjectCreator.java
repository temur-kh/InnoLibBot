package services;

import java.util.Calendar;
import java.util.Date;
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
        String line = "";
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        line += (day.length() == 1 ? Constants.ZERO + day : day) + Constants.DASH;
        line += (month.length() == 1 ? Constants.ZERO + month : month) + Constants.DASH;
        line += (year.length() == 1 ? Constants.ZERO + year : year);
        return line;
    }

    public static Calendar convertToCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        return calendar;
    }

    public static Date convertToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
