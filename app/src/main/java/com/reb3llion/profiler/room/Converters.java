package com.reb3llion.profiler.room;

import androidx.room.TypeConverter;

import com.reb3llion.profiler.room.entities.Profile;

public class Converters {

    @TypeConverter
    public static String TimeToString(Profile.Time time){
        return Profile.toTimeString(time.hour, time.minute);
    }

    @TypeConverter
    public static Profile.Time StringToTime(String time){
        return Profile.ToTimeObject(time);
    }
}
