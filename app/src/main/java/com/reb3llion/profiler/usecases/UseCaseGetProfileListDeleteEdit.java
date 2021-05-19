package com.reb3llion.profiler.usecases;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.room.tuple.ProfileTuple;
import com.reb3llion.profiler.utils.AsyncExecutor;
import com.reb3llion.profiler.utils.STATUS;


import java.util.List;

public class UseCaseGetProfileListDeleteEdit extends BaseUseCase{


    public UseCaseGetProfileListDeleteEdit(Application application) {
        super(application);
    }

    public void delete(Profile profile, AsyncExecutor.AsyncCallback callback){
        new AsyncExecutor().execute(() -> {
            callback.onStart();
            if(repository.deleteProfile(profile) >= 1)
                callback.onSuccess(STATUS.DELETE_PROFILE_SUCCESSFUL);
            else
                callback.onFailure(STATUS.DELETE_PROFILE_FAIL);
        });
    }

    public void profileEnableDisable(Profile profile, boolean update , AsyncExecutor.AsyncCallback callback){
        new AsyncExecutor().execute(() -> repository.enableDisableProfile(profile, update));
    }

    public LiveData<List<Profile>> getAllProfiles(){
        return repository.getAllProfiles();
    }

}
