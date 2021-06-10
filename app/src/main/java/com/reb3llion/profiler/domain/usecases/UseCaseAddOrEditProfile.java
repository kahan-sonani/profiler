package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.presenter.enums.STATUS;

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
