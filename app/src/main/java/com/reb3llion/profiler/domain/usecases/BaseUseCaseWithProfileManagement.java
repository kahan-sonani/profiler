package com.reb3llion.profiler.domain.usecases;

import com.reb3llion.profiler.domain.business.ProfileEventManagement;

public abstract class BaseUseCaseWithProfileManagement {

    protected ProfileEventManagement profileEventManagement;

    public BaseUseCaseWithProfileManagement() {
        profileEventManagement = new ProfileEventManagement();
    }
}
