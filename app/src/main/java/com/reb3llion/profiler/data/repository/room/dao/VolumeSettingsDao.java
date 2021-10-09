package com.reb3llion.profiler.data.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.reb3llion.profiler.data.repository.room.entities.VolumeSettings;

@Dao
public interface VolumeSettingsDao {

    @Query("SELECT * from VolumeSettings WHERE id = :id")
    LiveData<VolumeSettings> getVolumeSettings(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertVolumeSettings(VolumeSettings volumeSettings);

    @Delete
    Integer deleteVolumeSettings(VolumeSettings volumeSettings);
}
