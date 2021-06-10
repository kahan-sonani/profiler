package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import com.reb3llion.profiler.data.repository.AppRepository;

public abstract class BaseUseCase {

    protected AppRepository repository;

    public BaseUseCase(Application application){
        repository = new AppRepository(application);
    }
}
