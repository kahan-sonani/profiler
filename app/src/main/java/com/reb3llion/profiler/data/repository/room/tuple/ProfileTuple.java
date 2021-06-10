package com.reb3llion.profiler.data.repository.room.tuple;

import androidx.annotation.NonNull;

import com.reb3llion.profiler.data.repository.room.entities.Profile;

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
        startTime = Profile.PLACEHOLDER_TIME;
        endTime = Profile.PLACEHOLDER_TIME;
    }
}
