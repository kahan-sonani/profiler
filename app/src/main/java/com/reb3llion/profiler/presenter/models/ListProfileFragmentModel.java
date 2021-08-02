package com.reb3llion.profiler.presenter.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.usecases.AsyncExecutor;
import com.reb3llion.profiler.domain.usecases.UseCaseDisableEnabledProfilesWithoutPermission;
import com.reb3llion.profiler.domain.usecases.UseCaseGetProfileListDeleteEdit;
import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.List;
import java.util.Objects;

public class ListProfileFragmentModel extends AndroidViewModel implements AsyncExecutor.AsyncCallback<STATUS> {

    public SingleEvent<STATUS> onProfileDeleteStatus;
    private final UseCaseGetProfileListDeleteEdit useCase;
    public SingleEvent<Boolean> dndPermissionRequired;

    public static final String TAG = "ListProfileModel";
    private final UseCaseDisableEnabledProfilesWithoutPermission useCaseUpdateEventsForProfiles;
    public LiveData<List<Profile>> profiles;

    public ListProfileFragmentModel(Application application) {
        super(application);
        useCase = new UseCaseGetProfileListDeleteEdit(application);
        onProfileDeleteStatus = new SingleEvent<>();
        dndPermissionRequired = new SingleEvent<>(useCase.isDNDPermissionNotGranted());
        profiles = useCase.getAllProfiles();
        useCaseUpdateEventsForProfiles = new UseCaseDisableEnabledProfilesWithoutPermission(application);
    }

    public void deleteProfile(Profile profile) {
        useCase.delete(profile, this);
    }

    public void enableDisableProfile(Profile profile, boolean update){
        useCase.profileEnableDisable(profile, update);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onSuccess(AsyncExecutor.Data<STATUS> result) {
        onProfileDeleteStatus.postValue(result.getData());
    }

    @Override
    public void onFailure(AsyncExecutor.Data<STATUS> error) {
        onProfileDeleteStatus.postValue(error.getData());
    }

    public Profile getProfileAt(int index) {
        return Objects.requireNonNull(profiles.getValue()).get(index);
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return profiles;
    }

    public void checkForDNDPermission() {
        boolean value = useCase.isDNDPermissionNotGranted();
        if (dndPermissionRequired.getValue() != value)
            dndPermissionRequired.setValue(value);
    }

    public void removeEventsForProfiles() {
        useCaseUpdateEventsForProfiles.removeEventsForProfiles(profiles.getValue());
    }
}
