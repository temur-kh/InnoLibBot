package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class used to deal with dates and time.
 */
public class DateTime {
    public static Calendar createCalendarObject(String date) {
        String[] lines = date.split(Constants.DASH);
        return new GregorianCalendar(Integer.parseInt(lines[2]), Integer.parseInt(lines[1]), Integer.parseInt(lines[0]));
    }

    public static Calendar todayCalendar() {
        Calendar ex = new GregorianCalendar();
        return new GregorianCalendar(ex.get(Calendar.YEAR), ex.get(Calendar.MONTH), ex.get(Calendar.DAY_OF_MONTH));
    }

    public static Date todayDate() {
        return convertToDate(todayCalendar());
    }

    public static Date now() {
        return new Date();
    }

    public static Date tomorrowDate() { return daysAddedDate(DateTime.todayDate(), 1); }

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

    public static Date daysAddedDate(Date date, int numberOfDays) {
//        if (date.getSeconds() != 0 && date.getMinutes() != 0 && date.getHours() != 0) {
//            date.setSeconds(0);
//            date.setMinutes(0);
//            date.setHours(0);
//        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, numberOfDays); //minus number would decrement the days
        return cal.getTime();
    }

    public static int daysUntilToday(Calendar calendar) {
        Calendar today = todayCalendar();
        long diff = Math.abs(today.getTimeInMillis() - calendar.getTimeInMillis());
        return (int) diff / (24 * 60 * 60 * 1000);
    }
}
