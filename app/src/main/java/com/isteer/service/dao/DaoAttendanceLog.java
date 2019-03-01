package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.isteer.service.model.AttendanceLog;

import java.util.List;

@Dao

public interface DaoAttendanceLog {

    @Insert
    void insertAllAttendenceLog(List<AttendanceLog> attendanceLogs);

    @Insert
    long insertAttendenceLog(AttendanceLog attendanceLog);


    @Query("select * from aerp_attendence_log where update_status ='Pending';")
    List<AttendanceLog> getAsyncronizedAttendenceList();
}
