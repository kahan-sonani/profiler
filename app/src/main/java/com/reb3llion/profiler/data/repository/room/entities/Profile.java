package com.reb3llion.profiler.data.repository.room.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.reb3llion.profiler.BR;
import com.reb3llion.profiler.data.repository.room.Converters;
import com.reb3llion.profiler.domain.business.ProfileExecutionState;
import com.reb3llion.profiler.domain.business.ProfileNotRunningExecutionState;

@Entity
public class Profile extends BaseObservable implements Parcelable {

    public static final String TAG = "Profile.class";
    public static final String MEDIA = "Media";
    public static final String NOTIFICATION = "Notification";
    public static final String RINGTONE = "Ringtone";
    public static final String CALL = "Call";
    public static final String ALARM = "Alarm";
    public static final String DND = "DND";

    public static final String SUN = "Sun";
    public static final String MON = "Mon";
    public static final String TUE = "Tue";
    public static final String WED = "Wed";
    public static final String THU = "Thu";
    public static final String FRI = "Fri";
    public static final String SAT = "Sat";

    public static final String PLACEHOLDER_TIME = "--:--";
    public static final String PLACEHOLDER_LABEL = "No Label";
    public static final String tableName = "Profile";

    @PrimaryKey(autoGenerate = true)
    private long id;
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {

        @Override
        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
    private String label;
    private String startTime;
    private String endTime;
    private boolean Sun;
    private boolean Mon;
    private boolean Tue;
    private boolean Wed;
    private boolean Thu;
    private boolean Fri;
    private boolean Sat;
    private boolean mediaSelected;
    private boolean notificationSelected;
    private boolean callSelected;
    private boolean ringtoneSelected;
    private boolean alarmSelected;
    private int media;
    private int notification;
    private int call;
    private int ringtone;
    private int alarm;
    private boolean enable;
    private int dndPreference;
    private ProfileExecutionState state;

    public Profile() {

        label = PLACEHOLDER_LABEL;
        startTime = PLACEHOLDER_TIME;
        endTime = PLACEHOLDER_TIME;

        Sun = Sat = false;
        Mon = Tue = Wed = Thu = Fri = true;

        media = notification = ringtone = alarm = 0;
        call = 1;
        mediaSelected = callSelected = notificationSelected = ringtoneSelected = alarmSelected = false;

        enable = false;
        dndPreference = 1;
        state = new ProfileNotRunningExecutionState();
    }

    private Profile(Parcel in) {

        id = in.readLong();
        label = in.readString();
        startTime = in.readString();
        endTime = in.readString();

        Sun = in.readByte() != 0;
        Mon = in.readByte() != 0;
        Tue = in.readByte() != 0;
        Wed = in.readByte() != 0;
        Thu = in.readByte() != 0;
        Fri = in.readByte() != 0;
        Sat = in.readByte() != 0;

        mediaSelected = in.readByte() != 0;
        notificationSelected = in.readByte() != 0;
        callSelected = in.readByte() != 0;
        alarmSelected = in.readByte() != 0;
        ringtoneSelected = in.readByte() != 0;

        media = in.readInt();
        notification = in.readInt();
        call = in.readInt();
        alarm = in.readInt();
        ringtone = in.readInt();

        enable = in.readByte() != 0;
        dndPreference = in.readInt();
        state = Converters.stateToObject(in.readInt());
    }

    public Profile(Profile profile) {

        id = profile.id;
        label = profile.label;
        startTime = profile.startTime;
        endTime = profile.endTime;

        Sun = profile.Sun;
        Mon = profile.Mon;
        Tue = profile.Tue;
        Wed = profile.Wed;
        Thu = profile.Thu;
        Fri = profile.Fri;
        Sat = profile.Sat;

        media = profile.media;
        mediaSelected = profile.mediaSelected;
        notification = profile.notification;
        notificationSelected = profile.notificationSelected;
        call = profile.call;
        callSelected = profile.callSelected;
        ringtone = profile.ringtone;
        ringtoneSelected = profile.ringtoneSelected;
        alarm = profile.alarm;
        alarmSelected = profile.alarmSelected;

        enable = profile.enable;
        dndPreference = profile.dndPreference;
        state = profile.state;
    }

    @Bindable
    public ProfileExecutionState getState() {
        return state;
    }

    public void setState(ProfileExecutionState state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    @Bindable
    public int getDndPreference() {
        return dndPreference;
    }

    public void setDndPreference(int preference) {
        dndPreference = preference;
        notifyPropertyChanged(BR.dndPreference);
    }

    @Bindable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        notifyPropertyChanged(BR.label);
    }

    @Bindable
    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    @Bindable
    public boolean getMediaSelected() {
        return mediaSelected;
    }

    public void setMediaSelected(boolean mediaSelected) {
        this.mediaSelected = mediaSelected;
        notifyPropertyChanged(BR.mediaSelected);
    }
    @Bindable
    public boolean getNotificationSelected() {
        return notificationSelected;
    }

    public void setNotificationSelected(boolean notificationSelected) {
        this.notificationSelected = notificationSelected;
        notifyPropertyChanged(BR.notificationSelected);
    }
    @Bindable
    public boolean getCallSelected() {
        return callSelected;
    }

    public void setCallSelected(boolean callSelected) {
        this.callSelected = callSelected;
        notifyPropertyChanged(BR.callSelected);
    }
    @Bindable
    public boolean getRingtoneSelected() {
        return ringtoneSelected;
    }

    public void setRingtoneSelected(boolean ringtoneSelected) {
        this.ringtoneSelected = ringtoneSelected;
        notifyPropertyChanged(BR.ringtoneSelected);
    }

    @Bindable
    public boolean getAlarmSelected() {
        return alarmSelected;
    }

    public void setAlarmSelected(boolean alarmSelected) {
        this.alarmSelected = alarmSelected;
        notifyPropertyChanged(BR.alarmSelected);
    }

    @Bindable
    public boolean getSun() {
        return Sun;
    }

    public void setSun(boolean sun) {
        this.Sun = sun;
        notifyPropertyChanged(BR.sun);
    }

    @Bindable
    public boolean getMon() {
        return Mon;
    }

    public void setMon(boolean mon) {
        this.Mon = mon;
        notifyPropertyChanged(BR.mon);
    }

    @Bindable
    public boolean getTue() {
        return Tue;
    }

    @Bindable
    public boolean getWed() {
        return Wed;
    }

    @Bindable
    public boolean getThu() {
        return Thu;
    }

    public void setStartTime(@NonNull String startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @NonNull
    @Bindable
    public String getStartTime() {
        return startTime;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Bindable
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(@NonNull String endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    public long getId() {
        return id;
    }

    @Bindable
    public boolean getFri() {
        return Fri;
    }

    @Bindable
    public int getAlarm() {
        return alarm;
    }

    public void setFri(boolean fri) {
        this.Fri = fri;
        notifyPropertyChanged(BR.fri);
    }

    @Bindable
    public int getCall() {
        return call;
    }

    @Bindable
    public boolean getSat() {
        return Sat;
    }

    @Bindable
    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
        notifyPropertyChanged(BR.media);
    }
    @Bindable
    public int getNotification() {
        return notification;
    }

    public void setSat(boolean sat) {
        Sat = sat;
        notifyPropertyChanged(BR.sat);
    }
    @Bindable
    public int getRingtone() {
        return ringtone;
    }

    public void setThu(boolean thu) {
        Thu = thu;
        notifyPropertyChanged(BR.thu);
    }

    public void setTue(boolean tue) {
        Tue = tue;
        notifyPropertyChanged(BR.tue);
    }

    public void setNotification(int notification) {
        this.notification = notification;
        notifyPropertyChanged(BR.notification);
    }

    public void setWed(boolean wed) {
        Wed = wed;
        notifyPropertyChanged(BR.wed);
    }


    public void setAlarm(int alarm) {
        this.alarm = alarm;
        notifyPropertyChanged(BR.alarm);
    }

    public void setCall(int call) {
        this.call = call;
        notifyPropertyChanged(BR.call);
    }

    public void setRingtone(int ringtone) {
        this.ringtone = ringtone;
        notifyPropertyChanged(BR.ringtone);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(label);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeByte((byte) (Sun ? 1 : 0));
        dest.writeByte((byte) (Mon ? 1 : 0));
        dest.writeByte((byte) (Tue ? 1 : 0));
        dest.writeByte((byte) (Wed ? 1 : 0));
        dest.writeByte((byte) (Thu ? 1 : 0));
        dest.writeByte((byte) (Fri ? 1 : 0));
        dest.writeByte((byte) (Sat ? 1 : 0));
        dest.writeByte((byte) (mediaSelected ? 1 : 0));
        dest.writeByte((byte) (notificationSelected ? 1 : 0));
        dest.writeByte((byte) (callSelected ? 1 : 0));
        dest.writeByte((byte) (alarmSelected ? 1 : 0));
        dest.writeByte((byte) (ringtoneSelected ? 1 : 0));
        dest.writeInt(media);
        dest.writeInt(notification);
        dest.writeInt(call);
        dest.writeInt(alarm);
        dest.writeInt(ringtone);
        dest.writeByte((byte) (enable ? 1 : 0));
        dest.writeInt(dndPreference);
        dest.writeInt(state.getInt());
    }

    public static class Time {

        public static final String FORMAT24H = "HH:mm";
        public static final String FORMAT12H = "hh:mm";
        public static final String FORMAT_AM_PM_12H = "hh:mm a";

        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public int hour, minute;

        public static Time ToTimeObject(String time){
            Time temp = new Time(-1, -1);
            temp.hour = Integer.parseInt(time.split(":")[0]);
            temp.minute = Integer.parseInt(time.split(":")[1]);
            return temp;
        }

        public static boolean isTimeSpecified(String time){
            return PLACEHOLDER_TIME.compareTo(time) != 0;
        }
    }
}
