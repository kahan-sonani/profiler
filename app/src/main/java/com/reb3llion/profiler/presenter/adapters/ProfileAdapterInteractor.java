package com.reb3llion.profiler.presenter.adapters;

import com.reb3llion.profiler.data.repository.room.entities.Profile;

public interface ProfileAdapterInteractor {

    void onClick(int index);

    void onEnableDisableProfile(Profile profile, boolean update);

}
