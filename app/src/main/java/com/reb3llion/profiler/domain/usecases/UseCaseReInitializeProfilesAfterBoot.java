package com.reb3llion.profiler.domain.usecases;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.PermissionManager;
import com.reb3llion.profiler.domain.business.ProfileManagement;

import java.util.List;

public class UseCaseReInitializeProfilesAfterBoot extends BaseUseCaseWithProfileManagement {

    public void reInitializeProfiles(List<Profile> profiles) {
        new AsyncExecutor().execute(() -> {
            for (int i = 0; i < profiles.size(); i++) {
                Profile profile = profiles.get(i);
                if (profile.getEnable() && !(PermissionManager.isDNDPermissionNotGranted()
                        && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
                    profileEventManagement.addProfileEventAfterBoot(profile);
                }
            }
        });
    }
}
