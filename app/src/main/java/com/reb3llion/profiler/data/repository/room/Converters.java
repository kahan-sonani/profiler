package com.reb3llion.profiler.data.repository.room;

import androidx.room.TypeConverter;

import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.domain.business.ProfileNotRunningExecutionState;
import com.reb3llion.profiler.domain.business.ProfileRunningExecutionState;

public class Converters {

    @TypeConverter
    public static ProfileExecutionState stateToObject(int state) {
        return state == ProfileExecutionState.RUNNING ? new ProfileRunningExecutionState()
                : new ProfileNotRunningExecutionState();
    }

    @TypeConverter
    public static int ObjectToState(ProfileExecutionState state) {
        return state.getInt();
    }
}
