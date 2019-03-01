package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.isteer.service.model.LocationLog;

import java.util.List;

@Dao
public interface DaoLocationLog {
    @Insert
    void insertAllLocsationLog(List<LocationLog> loc);

    @Insert
    long insertLocsationLog(LocationLog loc);

    @Query("select * from aerp_location_log where update_status = 'Pending';")
    List<LocationLog> getAllLocationLog();

    @Query("Delete from aerp_location_log where loc_key =:loc_key")
    void deleteByKey(int loc_key);
}
