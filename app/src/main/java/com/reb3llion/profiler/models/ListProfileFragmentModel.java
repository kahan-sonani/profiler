package com.reb3llion.profiler.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.reb3llion.profiler.room.entities.Profile;
import com.reb3llion.profiler.usecases.UseCaseGetProfileListDeleteEdit;
import com.reb3llion.profiler.utils.AsyncExecutor;
import com.reb3llion.profiler.utils.STATUS;
import com.reb3llion.profiler.utils.SingleEvent;

import java.util.List;

public class ListProfileFragmentModel extends AndroidViewModel implements AsyncExecutor.AsyncCallback {

    public SingleEvent<STATUS> onProfileDeleteStatus;

    public LiveData<List<Profile>> profileList;

    public static final String TAG = "ListProfileModel";
    private UseCaseGetProfileListDeleteEdit useCase;
    public ListProfileFragmentModel(Application application){
        super(application);
        onProfileDeleteStatus = new SingleEvent<>();
        useCase = new UseCaseGetProfileListDeleteEdit(application);
        profileList = useCase.getAllProfiles();
    }

    public void deleteProfile(Profile profile){
        useCase.delete(profile, this);
    }

    public void enableDisableProfile(Profile profile, boolean update){
        useCase.profileEnableDisable(profile, update, null);
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
}
