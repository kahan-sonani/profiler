package com.reb3llion.profiler.presenter.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.usecases.AsyncExecutor;
import com.reb3llion.profiler.domain.usecases.UseCaseGetProfileListDeleteEdit;
import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.List;

public class ListProfileFragmentModel extends AndroidViewModel implements AsyncExecutor.AsyncCallback {

    public SingleEvent<STATUS> onProfileDeleteStatus;
    public SingleEvent<Boolean> dndPermissionRequired;

    public static final String TAG = "ListProfileModel";
    private UseCaseGetProfileListDeleteEdit useCase;
    public ListProfileFragmentModel(Application application){
        super(application);
        useCase = new UseCaseGetProfileListDeleteEdit(application);
        onProfileDeleteStatus = new SingleEvent<>();
        dndPermissionRequired = new SingleEvent<>(useCase.isDNDPermissionNotGranted());
        ProfileManagement.getInstance().setProfiles(useCase.getAllProfiles());
    }

    public void deleteProfile(Profile profile){
        useCase.delete(profile, this);
    }

    public void enableDisableProfile(Profile profile, boolean update){
        useCase.profileEnableDisable(profile, update);
    }
    @Override
    public void onStart() { }

    @Override
    public void onSuccess(STATUS result) {
        onProfileDeleteStatus.postValue(result);
    }

    @Override
    public void onFailure(STATUS error) {
        onProfileDeleteStatus.postValue(error);
    }

    public LiveData<List<Profile>> getAllProfiles(){
        return ProfileManagement.getInstance().getProfiles();
    }

    public void checkForDNDPermission() {
        boolean value = useCase.isDNDPermissionNotGranted();
        if(dndPermissionRequired.getValue() != value)
            dndPermissionRequired.setValue(value);
    }
}
