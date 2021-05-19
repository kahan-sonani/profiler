package com.reb3llion.profiler.usecases;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.repository.AppRepository;

public abstract class BaseUseCase {

    protected AppRepository repository;

    public BaseUseCase(Application application){
        repository = new AppRepository(application);
    }

}
