package com.reb3llion.profiler.data.repository.room.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VolumeSettings {

    @PrimaryKey
    private long id;

    private int media;
    private int notification;
    private int call;
    private int ringtone;
    private int alarm;

    private int dndPreference;

    public VolumeSettings() {

        media = notification = call = ringtone = alarm = -1;
        dndPreference = 1;
    }

    public void setDNDMode(int currentInterruptionFilter) {
        dndPreference = currentInterruptionFilter;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int streamVolume) {
        alarm = streamVolume;
    }

    public int getCall() {
        return call;
    }

    public void setCall(int streamVolume) {
        call = streamVolume;
    }

    public int getDndPreference() {
        return dndPreference;
    }

    public void setDndPreference(int dndPreference) {
        this.dndPreference = dndPreference;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int streamVolume) {
        media = streamVolume;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int streamVolume) {
        notification = streamVolume;
    }

    public int getRingtone() {
        return ringtone;
    }

    public void setRingtone(int streamVolume) {
        ringtone = streamVolume;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
