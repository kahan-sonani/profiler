package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.PermissionManager;
import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.List;

public class UseCaseGetProfileListDeleteEdit extends BaseUseCase{


    private static final String TAG = "UseCaseGetDeleteEdit";

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

    public void profileEnableDisable(Profile profile, boolean update){
        new AsyncExecutor().execute(() -> repository.enableDisableProfile(profile, update));
    }

    public LiveData<List<Profile>> getAllProfiles(){

        return repository.getAllProfiles();
    }

    public boolean isDNDPermissionNotGranted(){
        return PermissionManager.isDNDPermissionNotGranted();
    }
}
