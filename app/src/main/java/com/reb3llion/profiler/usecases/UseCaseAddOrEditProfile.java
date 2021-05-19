package com.reb3llion.profiler.usecases;

import android.app.Application;

import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.utils.AsyncExecutor;
import com.reb3llion.profiler.utils.STATUS;

import java.util.Objects;

public class UseCaseAddOrEditProfile extends BaseUseCase{
    public UseCaseAddOrEditProfile(Application application) {
        super(application);
    }

    public void addProfile(Profile profile, AsyncExecutor.AsyncCallback callback){
        if(!profile.isProfileValid()) {
            if (callback != null)
                callback.onFailure(STATUS.INVALID_CREDENTIALS);
        }
        else {
            new AsyncExecutor().execute(() -> {
                if (callback == null)
                    repository.addProfile(profile);
                else {
                    callback.onStart();
                    if (repository.addProfile(profile) >= 1)
                        callback.onSuccess(STATUS.ADD_PROFILE_SUCCESSFUL);
                    else
                        callback.onFailure(STATUS.ADD_PROFILE_FAIL);
                }

            });
        }
    }

    public void updateProfile(Profile profile, AsyncExecutor.AsyncCallback callback){
        if(!profile.isProfileValid()) {
            if (callback != null)
                callback.onFailure(STATUS.INVALID_CREDENTIALS);
        }
        else {
            new AsyncExecutor().execute(() -> {
                if (callback == null)
                    repository.updateProfile(profile);
                else {
                    callback.onStart();
                    if (repository.updateProfile(profile) >= 1)
                        callback.onSuccess(STATUS.UPDATE_PROFILE_SUCCESSFUL);
                    else
                        callback.onFailure(STATUS.UPDATE_PROFILE_FAIL);
                }
            });
        }
    }

}
