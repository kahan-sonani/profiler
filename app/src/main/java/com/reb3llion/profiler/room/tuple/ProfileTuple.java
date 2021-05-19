package com.reb3llion.profiler.room.tuple;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.reb3llion.profiler.room.entities.Profile;

public class ProfileTuple {

    @NonNull
    public String startTime;

    @NonNull
    public String endTime;

    public boolean Sun;
    public boolean Mon;
    public boolean Tue;
    public boolean Wed;
    public boolean Thu;
    public boolean Fri;
    public boolean Sat;

    public ProfileTuple(){
        Sun = Mon = Tue = Wed = Thu = Fri = Sat = false;
        startTime = Profile.toTimeString(-1, -1);
        endTime = Profile.toTimeString(-1, -1);
    }
}
