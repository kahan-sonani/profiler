package com.reb3llion.profiler.domain.usecases;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.PermissionManager;
import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.List;

public class UseCaseGetProfileListDeleteEdit extends BaseUseCase {


    private static final String TAG = "UseCaseGetDeleteEdit";

    public UseCaseGetProfileListDeleteEdit(Application application) {
        super(application);
    }

    public void delete(Profile profile, AsyncExecutor.AsyncCallback<STATUS> callback) {
        new AsyncExecutor().execute(() -> {
            callback.onStart();
            if (appRepository.deleteProfile(profile) >= 1) {
                callback.onSuccess(new AsyncExecutor.Data<>(STATUS.DELETE_PROFILE_SUCCESSFUL));
                new Handler(Looper.getMainLooper()).post(() -> profileEventManagement.deleteProfileEvent(profile));

            } else
                callback.onFailure(new AsyncExecutor.Data<>(STATUS.DELETE_PROFILE_FAIL));
        });
    }

    public void profileEnableDisable(Profile profile, boolean update) {
        Log.i("hii", profile.getState().getInt() + "");
        new AsyncExecutor().execute(() -> {
            appRepository.enableDisableProfile(profile, update);
            if (update)
                new Handler(Looper.getMainLooper())
                        .post(() -> profileEventManagement.addProfileEvent(profile));
            else
                new Handler(Looper.getMainLooper())
                        .post(() -> profileEventManagement.removeProfileEvent(profile));
        });

    }

    public LiveData<List<Profile>> getAllProfiles() {

        return appRepository.getAllProfilesLiveData();
    }

    public void getAllProfiles(AsyncExecutor.AsyncCallback<List<Profile>> profilesCallback) {
        LiveData<List<Profile>> profilesLiveData = appRepository.getAllProfilesLiveData();
        profilesLiveData.observeForever(new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if (profiles == null)
                    profilesCallback.onFailure(new AsyncExecutor.Data<>(null));
                else
                    profilesCallback.onSuccess(new AsyncExecutor.Data<>(profiles));
                profilesLiveData.removeObserver(this);
            }
        });
    }

    public boolean isDNDPermissionNotGranted() {
        return PermissionManager.isDNDPermissionNotGranted();
    }
}
