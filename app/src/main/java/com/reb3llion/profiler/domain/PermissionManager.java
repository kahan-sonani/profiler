package com.reb3llion.profiler.domain;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.reb3llion.profiler.presenter.ProfilerApplication;

public class PermissionManager {

    public static boolean isDNDPermissionNotGranted(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return !((NotificationManager) ProfilerApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE))
                    .isNotificationPolicyAccessGranted();
        }
        return false;
    }

}
