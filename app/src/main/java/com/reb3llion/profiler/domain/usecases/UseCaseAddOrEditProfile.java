package com.reb3llion.profiler.domain.usecases;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.presenter.enums.MODE;
import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.List;

public class UseCaseAddOrEditProfile extends BaseUseCase {

    public UseCaseAddOrEditProfile(Application application) {
        super(application);
    }

    public void addOrUpdateProfile(Profile profile, MODE mode, AsyncExecutor.AsyncCallback<STATUS> callback) {

        callback.onStart();
        setPlaceHolderLabel(profile);
        STATUS status = ProfileManagement.getProfileStatus(profile);
        if (status != STATUS.NO_ERROR)
            callback.onFailure(new AsyncExecutor.Data<>(status));
        else {
            LiveData<List<Profile>> conflicts = appRepository.checkForConflictOfProfileTimePeriod(profile);
            conflicts.observeForever(new Observer<>() {
                @Override
                public void onChanged(List<Profile> profiles) {
                    if (!profiles.isEmpty()) {
                        STATUS status = STATUS.CUSTOM_MESSAGE;
                        String result = ProfileManagement.getConflictProfiles(profile, profiles);
                        if (result == null)
                            addUpdateLogic(profile, mode, callback);
                        else {
                            status.setCustomMessage(result);
                            callback.onFailure(new AsyncExecutor.Data<>(status));
                        }
                    } else {
                        addUpdateLogic(profile, mode, callback);
                    }
                    conflicts.removeObserver(this);
                }
            });

        }
    }

    private void addUpdateLogic(Profile profile, MODE mode, AsyncExecutor.AsyncCallback<STATUS> callback) {
        new AsyncExecutor().execute(() -> {
            long result = (mode == MODE.UPDATE ? appRepository.updateProfile(profile)
                    : appRepository.addProfile(profile));
            if (result >= 1) {
                if (mode == MODE.UPDATE) {
                    if (profile.getEnable()) {
                        new Handler(Looper.getMainLooper())
                                .post(() -> profileEventManagement.updateProfileEvent(profile));
                    }
                    callback.onSuccess(new AsyncExecutor.Data<>(STATUS.UPDATE_PROFILE_SUCCESSFUL));
                } else {
                    callback.onSuccess(new AsyncExecutor.Data<>(STATUS.ADD_PROFILE_SUCCESSFUL));
                }
            } else
                callback.onFailure(new AsyncExecutor.Data<>(mode == MODE.UPDATE ?
                        STATUS.UPDATE_PROFILE_FAIL
                        : STATUS.ADD_PROFILE_FAIL));
        });
    }

    public void setPlaceHolderLabel(Profile profile) {
        if (profile.getLabel().equals(""))
            profile.setLabel(Profile.PLACEHOLDER_LABEL);
    }
}
