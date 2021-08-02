package com.reb3llion.profiler.domain.usecases;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.reb3llion.profiler.data.repository.room.entities.Profile;

public class UseCaseGetProfile extends BaseUseCaseWithDataBaseAccess {

    public UseCaseGetProfile(Application application) {
        super(application);
    }

    public void getProfile(long id, AsyncExecutor.AsyncCallback<Profile> profileAsyncCallback) {
        new AsyncExecutor().execute(() -> {
            profileAsyncCallback.onStart();
            LiveData<Profile> liveData = repository.getProfile(id);
            liveData.observeForever(new Observer<Profile>() {
                @Override
                public void onChanged(Profile profile) {
                    if (profile == null)
                        profileAsyncCallback.onFailure(new AsyncExecutor.Data<>(null));
                    else
                        profileAsyncCallback.onSuccess(new AsyncExecutor.Data<>(profile));
                    liveData.removeObserver(this);
                }
            });

        });
    }
}
