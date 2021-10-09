package com.reb3llion.profiler.presenter.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.usecases.AsyncExecutor;
import com.reb3llion.profiler.domain.usecases.UseCaseGetProfileListDeleteEdit;
import com.reb3llion.profiler.domain.usecases.UseCaseReInitializeProfilesAfterBoot;
import com.reb3llion.profiler.presenter.ProfilerApplication;
import com.reb3llion.profiler.utils.DisplayAppToast;

import java.util.List;

public class ProfilesReInitializeOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast.makeText(context, "called", Toast.LENGTH_SHORT).show();
            UseCaseGetProfileListDeleteEdit useCase = new UseCaseGetProfileListDeleteEdit(ProfilerApplication.getApplication());
            useCase.getAllProfiles(new AsyncExecutor.AsyncCallback<List<Profile>>() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(AsyncExecutor.Data<List<Profile>> result) {
                    List<Profile> list = result.getData();
                    UseCaseReInitializeProfilesAfterBoot useCaseReInitializeProfilesAfterBoot
                            = new UseCaseReInitializeProfilesAfterBoot();

                    if (!list.isEmpty())
                        useCaseReInitializeProfilesAfterBoot.reInitializeProfiles(list);
                }

                @Override
                public void onFailure(AsyncExecutor.Data<List<Profile>> error) {
                    DisplayAppToast.display(context, context.getString(R.string.error_fetch_profiles));
                }
            });
        }
    }
}
