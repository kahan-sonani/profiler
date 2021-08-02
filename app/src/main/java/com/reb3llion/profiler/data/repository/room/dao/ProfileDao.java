package com.reb3llion.profiler.data.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.reb3llion.profiler.data.repository.room.entities.Profile;
import com.reb3llion.profiler.data.repository.room.tuple.ProfileTuple;

import java.util.List;


@Dao
public interface ProfileDao {

    @Update
    Integer updateProfile(Profile profile);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertProfile(Profile profile);

    @Delete
    Integer deleteProfile(Profile profile);

    @Query("SELECT startTime, endTime, Sun, Mon, Tue, Wed, Thu, Fri, Sat FROM Profile ORDER BY startTime ASC")
    LiveData<List<ProfileTuple>> getAllStartEndTimeWithDays();

    @Query("DELETE from Profile where startTime = :startTime and endTime = :endTime;")
    Integer delete(String startTime, String endTime);

    @Query("SELECT * FROM Profile ORDER BY startTime ASC")
    LiveData<List<Profile>> getAll();

    @Query("UPDATE Profile SET enable = :update where id = :id")
    Integer enableDisableProfile(boolean update, long id);

    @Query("SELECT * from Profile WHERE id = :id")
    LiveData<Profile> getProfile(long id);

    @Query("SELECT * from Profile WHERE id != :id and ((:startTime <= startTime and (:endTime <= endTime and :endTime >= startTime)) or ((:startTime >= startTime and :startTime <= endTime) and :endTime >= endTime));")
    LiveData<List<Profile>> checkForConflict(long id, String startTime, String endTime);

    @Query("UPDATE Profile SET state = :state WHERE id = :id")
    Integer updateProfileState(int state, long id);

}
