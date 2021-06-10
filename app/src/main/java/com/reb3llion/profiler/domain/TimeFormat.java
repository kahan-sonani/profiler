package com.reb3llion.profiler.domain;

import com.reb3llion.profiler.data.repository.room.entities.Profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class TimeFormat {

    public static SimpleDateFormat timeStringFormat24h = new SimpleDateFormat(Profile.Time.FORMAT24H, Locale.US);
    public static SimpleDateFormat timeStringAmPm = new SimpleDateFormat(Profile.Time.FORMAT_AM_PM_12H, Locale.US);
    public static SimpleDateFormat timeStringWithoutAmPm12h = new SimpleDateFormat(Profile.Time.FORMAT12H, Locale.US);

    public static String getTimeStringWithTypography(String time){
        if(time.equals(Profile.PLACEHOLDER_TIME))
            return Profile.PLACEHOLDER_TIME;
        else {
            try {
                return timeStringAmPm.format(Objects.requireNonNull(timeStringFormat24h.parse(time)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Profile.PLACEHOLDER_TIME;
    }

    public static String getTimeString12h(String time){
        if(time.equals(Profile.PLACEHOLDER_TIME))
            return Profile.PLACEHOLDER_TIME;
        else {
            try {
                return timeStringWithoutAmPm12h.format(Objects.requireNonNull(timeStringFormat24h.parse(time)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Profile.PLACEHOLDER_TIME;
    }

    public static String getTimeString(int hour, int minute){
        return String.format(Locale.US, "%02d:%02d", hour, minute);
    }

    public static int getHour(String time){
        return Integer.parseInt(time.split(":")[0]);
    }

    public static int getMinute(String time){
        return Integer.parseInt(time.split(":")[1]);
    }

    public static String[] splitTypographyAndTime(String time){
        return time.split(" ");
    }

}
