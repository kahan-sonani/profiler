package com.reb3llion.profiler.utils;

import android.content.Context;
import android.widget.Toast;

public class DisplayAppToast {

    public static final String appName = "Profiler";

    public static void display(Context context, String message) {
        Toast.makeText(context,
                appName + ": " + message,
                Toast.LENGTH_LONG).show();
    }

}
