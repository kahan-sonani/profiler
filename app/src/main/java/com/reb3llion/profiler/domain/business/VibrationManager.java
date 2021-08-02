package com.reb3llion.profiler.domain.business;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.reb3llion.profiler.presenter.ProfilerApplication;

public class VibrationManager {

    public static void vibrate() {
        Vibrator vibrator = (Vibrator) ProfilerApplication.getAppContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(500);
    }
}
