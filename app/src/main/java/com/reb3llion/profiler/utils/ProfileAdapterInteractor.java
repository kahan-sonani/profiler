package com.reb3llion.profiler.utils;

import com.reb3llion.profiler.room.entities.Profile;

public interface ProfileAdapterInteractor {

    void onClick(int index);

    void onEnableDisableProfile(Profile profile, boolean udpate);
}
