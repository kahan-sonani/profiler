package com.reb3llion.profiler.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.reb3llion.profiler.room.dao.ProfileDao;
import com.reb3llion.profiler.room.entities.Profile;

@Database(entities = {Profile.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProfileDatabase extends RoomDatabase {

    public static final String DB_NAME = "profile_database";
    private static ProfileDatabase mInstance;

    public static synchronized ProfileDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context,ProfileDatabase.class, DB_NAME).build();
        }
        return mInstance;
    }
    public abstract ProfileDao ProfileDao();

}
