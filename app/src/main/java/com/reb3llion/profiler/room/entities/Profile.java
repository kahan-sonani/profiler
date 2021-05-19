package com.reb3llion.profiler.room.entities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.reb3llion.profiler.BR;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile extends BaseObservable {

    public static final String TAG = "Profile.class";
    public static final String MEDIA = "Media";
    public static final String NOTIFICATION = "Notification";
    public static final String RINGTONE = "Ringtone";
    public static final String CALL = "Call";
    public static final String ALARM = "Alarm";

    public static final String SUN = "Sun";
    public static final String MON = "Mon";
    public static final String TUE = "Tue";
    public static final String WED = "Wed";
    public static final String THU = "Thu";
    public static final String FRI = "Fri";
    public static final String SAT = "Sat";

    public static final String PLACEHOLDER_START_TIME = "Start Time";
    public static final String PLACEHOLDER_END_TIME = "End Time";
    public static final String tableName = "Profile";

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Profile(){

        startTime = new Time(-1, -1);
        endTime = new Time(-1, -1);

        Sun = Sat = false;
        Mon = Tue = Wed = Thu = Fri = true;

        media = notification = call = ringtone = alarm = 0;
        mediaSelected = callSelected = notificationSelected = ringtoneSelected = alarmSelected = false;

        enable = false;

    }

    public Profile(Profile profile){

        id = profile.id;
        startTime = new Time(profile.startTime.hour, profile.startTime.minute);
        endTime = new Time(profile.endTime.hour, profile.endTime.minute);

        Sun = profile.Sun;
        Sat = profile.Sat;
        Mon = profile.Mon;
        Tue = profile.Tue;
        Wed = profile.Wed;
        Thu = profile.Thu;
        Fri = profile.Fri;

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
        dnd = profile.dnd;
    }
    @NonNull
    private Time startTime;
    @NonNull
    private Time endTime;

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
    private boolean dnd;


    @Bindable
    public boolean getDnd(){
        return dnd;
    }

    public void setDnd(boolean value){
        this.dnd = value;
        notifyPropertyChanged(BR.dnd);
    }
    @Bindable
    public boolean getEnable(){
        return enable;
    }

    public void setEnable(boolean enable){
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
    @Bindable
    public boolean getMon() {
        return Mon;
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
    @Bindable
    public boolean getFri() {
        return Fri;
    }
    @Bindable
    public boolean getSat() {
        return Sat;
    }

    public void setStartTime(Time startTime) {
        this.startTime.hour = startTime.hour;
        this.startTime.minute = startTime.minute;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable
    public Time getStartTime() {
        return startTime;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Bindable
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime.hour = endTime.hour;
        this.endTime.minute = endTime.minute;
        notifyPropertyChanged(BR.endTime);
    }

    public int getId() {
        return id;
    }

    public void setMon(boolean mon) {
        Mon = mon;
        notifyPropertyChanged(BR.mon);
    }
    @Bindable
    public int getAlarm() {
        return alarm;
    }

    public void setFri(boolean fri) {
        Fri = fri;
        notifyPropertyChanged(BR.fri);
    }
    @Bindable
    public int getCall() {
        return call;
    }

    public void setSun(boolean sun) {
        Sun = sun;
        notifyPropertyChanged(BR.sun);
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

    @Ignore
    public void setVolume(String name, float value){

        switch (name){
            case MEDIA: setMedia((int) value);break;
            case NOTIFICATION:setNotification((int) value);break;
            case CALL:setCall((int) value);break;
            case RINGTONE:setRingtone((int) value);break;
            case ALARM:setAlarm((int) value);break;

        }
    }

    @Ignore
    public boolean isStartTimeSpecified(){
        return startTime.hour != -1 && startTime.minute != -1;
    }

    @Ignore
    public boolean isEndTimeSpecified(){
        return endTime.hour != -1 && endTime.minute != -1;
    }

    @Ignore
    public boolean isNoDaySelected(){
        return !(Sun || Mon || Tue || Wed || Thu || Fri || Sat);
    }

    @Ignore
    public boolean isNoVolumeSelected(){
        return !(mediaSelected || notificationSelected || callSelected || ringtoneSelected || alarmSelected);
    }

    @Ignore
    public static String toTimeString(int hour, int minute){
        return String.format("%s:%s", hour, minute);
    }

    @Ignore
    public static Time ToTimeObject(String time){
        Time temp = new Time();
        temp.hour = Integer.parseInt(time.split(":")[0]);
        temp.minute = Integer.parseInt(time.split(":")[1]);
        return temp;
    }


    @Ignore
    public boolean isTimeValid(){
        if(startTime.hour > endTime.hour)
            return false;
        else if(startTime.hour == endTime.hour){
            return startTime.minute < endTime.minute;
        }
        return true;
    }
    public static class Time{
        public Time(int hour, int minute){
            this.hour = hour;
            this.minute = minute;
        }

        public  Time(){
            this.hour = -1;
            this.minute = -1;
        }

        public int hour, minute;
    }

    @Ignore
    public boolean isProfileValid(){
        return isEndTimeSpecified()
                        && isStartTimeSpecified()
                        && isTimeValid()
                        && !isNoVolumeSelected()
                        && !isNoDaySelected();
    }

    @Ignore
    public String getStartTimeString(){
        return toTimeString(startTime.hour, startTime.minute);
    }

    @Ignore
    public String getEndTimeString(){
        return toTimeString(endTime.hour, endTime.minute);
    }

    @Ignore
    public boolean areBothContentSame(Profile profile){
        return
                profile.startTime.hour == startTime.hour
                && profile.startTime.minute == startTime.minute
                && profile.endTime.hour == endTime.hour
                && profile.endTime.minute == endTime.minute
                && profile.Sun == Sun
                && profile.Mon == Mon
                && profile.Tue == Tue
                && profile.Wed == Wed
                && profile.Thu == Thu
                && profile.Fri == Fri
                && profile.Sat == Sat;
    }

}
