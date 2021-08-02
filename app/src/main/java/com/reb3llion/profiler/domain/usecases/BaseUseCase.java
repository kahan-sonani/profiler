package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import com.reb3llion.profiler.data.repository.AppRepository;
import com.reb3llion.profiler.domain.business.ProfileEventManagement;

public class BaseUseCase {

    protected AppRepository appRepository;
    protected ProfileEventManagement profileEventManagement;

    public BaseUseCase(Application application) {
        appRepository = new AppRepository(application);
        profileEventManagement = new ProfileEventManagement();
    }


}
