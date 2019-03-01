package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.isteer.service.model.ImagePathData;
import com.isteer.service.model.ServiceCallData;

import java.util.List;

@Dao

public interface DaoServicesCall {

    @Insert
    void insertServiceCall(ServiceCallData... serviceCallData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllServiceCall(List<ServiceCallData> imagePathData);

    @Insert
    void insertOnlySingleRecord(ServiceCallData serviceCallData);

    @Query("SELECT * FROM aerp_servicecall_master")
    List<ServiceCallData> fetchallServiceCall();

    @Query("SELECT * FROM aerp_servicecall_master where status = :status")
    List<ServiceCallData> getPendingServiceCall(int status);

    @Query("UPDATE aerp_servicecall_master SET latitude =:latitude ,longitude =:longitude ,is_syncTo_server =0  WHERE  " +
            "customer_key =:contact_key ")
    int updateCustomerLocation(String contact_key, double latitude, double longitude);

}
