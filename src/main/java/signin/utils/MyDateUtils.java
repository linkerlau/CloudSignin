package signin.utils;

import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {

    private static Calendar calendar = Calendar.getInstance();

    public static int getNowDayOfYear(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    @SuppressWarnings("all")
    public static int getDayOfYearWithOptionalDate(String date) {
        String[] dates = date.split("-");
        calendar.set(
                Integer.parseInt(dates[0]),
                Integer.parseInt(dates[1]) - 1,
                Integer.parseInt(dates[2])
        );
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getFirstDayOfWeek(Date date) {
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getYesterdayDayOfYear(Date date) {
        calendar.setTime(date);
        int beforeMonth = calendar.get(Calendar.MONTH);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        int afterMonth = calendar.get(Calendar.MONTH);
        if (afterMonth > beforeMonth) {
            calendar.roll(Calendar.YEAR, -1);
        }
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getNowWeek(Date date) {
        calendar.setTime(date);
        return calendar.getWeeksInWeekYear();
    }

    public static int getDayOfYearOnMon(Date date, int dayOfWeek) {
        calendar.setTime(date);
        int beforeMonth = calendar.get(Calendar.MONTH);
        calendar.roll(Calendar.DAY_OF_YEAR, 0-(dayOfWeek-1));
        int afterMonth = calendar.get(Calendar.MONTH);
        if (afterMonth > beforeMonth) {
            calendar.roll(Calendar.YEAR, -1);
        }
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isMorning() {
        calendar.setTime(new Date());
        return calendar.get(Calendar.AM_PM) == Calendar.AM;
    }

}
