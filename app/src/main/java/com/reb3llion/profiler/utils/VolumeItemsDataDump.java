package com.reb3llion.profiler.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.reb3llion.profiler.R;

public class VolumeItemsDataDump {

    public static final int size = 5;
    public static final String[] volumeHeaders = {"Media", "Ringtone" ,"Call", "Notification", "Alarm"};
    public static final int[] volumeItemIcons = {R.drawable.ic_baseline_music_note_24,
                                    R.drawable.ic_baseline_ring_volume_24,
                                    R.drawable.ic_baseline_phone_24,
                                    R.drawable.ic_baseline_notifications_active_24,
                                    R.drawable.ic_baseline_alarm_24};
}
