package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.isteer.service.model.WorkLogData;
import com.isteer.service.model.WorkLogData;

import java.util.List;

/**
 * Created by Bright on 27/4/2018.
 */
@Dao
public interface DaoWorkLog {

    @Insert
    long insertWorkLogData(WorkLogData workLogData);

    @Insert
    void insertWorkLogDataList(List<WorkLogData> workLogDatas);

    @Insert
    long insertOnlySingleRecord(WorkLogData workLogData);

    @Query("SELECT * FROM work_log where service_key =:service_key")
    List<WorkLogData> fetchAllWorkLogData(int service_key);


    @Query("SELECT * FROM work_log WHERE service_key =:scall_key")
    List<WorkLogData> fetchWorkLogBySCallId(String scall_key);


    @Query("SELECT count(*) from work_log WHERE scl_key=:scl_key ")
    boolean isOldEntry(String scl_key);

    @Update
    int updateWorkLogData(WorkLogData workLogData);

    @Delete
    void deleteWorkLogData(WorkLogData workLogData);

    @Query("DELETE FROM work_log WHERE is_synced_server= 1")
    void clearWorkLogTable();

    @Query("UPDATE work_log SET scl_key=:scl_key, is_synced_server= 1 WHERE scl_key=:dummyKey")
    void updateWorklogKey(String dummyKey, String scl_key);

    @Query("SELECT * FROM work_log WHERE is_synced_server=0 ")
    List<WorkLogData> getLocalWorkLogDatas();

}
