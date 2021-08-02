package com.reb3llion.profiler.data.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.reb3llion.profiler.data.repository.room.dao.ProfileDao;
import com.reb3llion.profiler.data.repository.room.dao.VolumeSettingsDao;
import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.data.repository.room.entities.VolumeSettings;

@Database(entities = {Profile.class, VolumeSettings.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProfileDatabase extends RoomDatabase {

    public static final String DB_NAME = "profile_database";
    private static ProfileDatabase mInstance;

    public static synchronized ProfileDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, ProfileDatabase.class, DB_NAME).build();
        }
        return mInstance;
    }

    public abstract ProfileDao ProfileDao();

    public abstract VolumeSettingsDao volumeSettingsDao();

}
