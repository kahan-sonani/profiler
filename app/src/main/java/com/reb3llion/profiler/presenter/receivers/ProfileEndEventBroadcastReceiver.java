package com.reb3llion.profiler.presenter.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.PermissionManager;
import com.reb3llion.profiler.domain.business.ProfileEventManagement;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.business.VibrationManager;
import com.reb3llion.profiler.domain.usecases.UseCaseEndProfileExecution;

public class ProfileEndEventBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getString(R.string.ACTION_PROFILE_BROADCAST_RECEIVER_ACTION))) {
            Profile profile = intent.getBundleExtra(ProfileEventManagement.KEY_PROFILE)
                    .getParcelable(ProfileEventManagement.KEY_PROFILE);
            UseCaseEndProfileExecution useCaseEndProfileExecution = new UseCaseEndProfileExecution();
            if (!(PermissionManager.isDNDPermissionNotGranted() && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
                useCaseEndProfileExecution.applyVolumeSettingsFromPreference(profile);
                VibrationManager.vibrate();
                useCaseEndProfileExecution.addEvent(profile);
            }
            useCaseEndProfileExecution.changeState(profile);
        }
    }
}
