package com.reb3llion.profiler.presenter.receivers;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.reb3llion.profiler.R;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.domain.business.PermissionManager;
import com.reb3llion.profiler.domain.business.ProfileEventManagement;
import com.reb3llion.profiler.domain.business.ProfileManagement;
import com.reb3llion.profiler.domain.business.VibrationManager;
import com.reb3llion.profiler.domain.usecases.UseCaseStartProfileExecution;
import com.reb3llion.profiler.presenter.enums.MODE;
import com.reb3llion.profiler.presenter.notification.ProfilerNotification;

public class ProfileStartEventBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getString(R.string.ACTION_PROFILE_BROADCAST_RECEIVER_ACTION))) {
            UseCaseStartProfileExecution useCaseStartProfileExecution = new UseCaseStartProfileExecution();
            Bundle bundle = intent.getBundleExtra(ProfileEventManagement.KEY_PROFILE);
            Profile profile = bundle.getParcelable(ProfileEventManagement.KEY_PROFILE);

            if (!(PermissionManager.isDNDPermissionNotGranted() && ProfileManagement.doesProfileNeedDNDPermission(profile))) {
                useCaseStartProfileExecution.applyVolumeSettings(profile, MODE.ADD);
                VibrationManager.vibrate();
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(ProfilerNotification.NOTIFICATION_ID, ProfilerNotification.buildNotification(profile));
            }
            useCaseStartProfileExecution.changeState(profile);
        }
    }
}
