package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.domain.business.ProfileManagement;

import java.util.List;

public class UseCaseDisableEnabledProfilesWithoutPermission extends BaseUseCase {

    public UseCaseDisableEnabledProfilesWithoutPermission(Application application) {
        super(application);
    }

    public void removeEventsForProfiles(List<Profile> list) {
        if (list != null && !list.isEmpty()) {
            new AsyncExecutor().execute(() -> {
                for (int i = 0; i < list.size(); i++) {
                    final Profile profile = list.get(i);
                    if (profile.getEnable() && ProfileManagement.doesProfileNeedDNDPermission(profile)) {
                        profileEventManagement.removeProfileEvent(profile);
                        appRepository.enableDisableProfile(profile, false);
                        if (profile.getState().getInt() == ProfileExecutionState.RUNNING)
                            appRepository.updateProfileState(profile, ProfileExecutionState.NOT_RUNNING);
                    }
                }
            });
        }
    }
}
