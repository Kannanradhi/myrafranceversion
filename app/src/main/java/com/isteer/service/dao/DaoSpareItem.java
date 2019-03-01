package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.isteer.service.model.SpareItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bright on 27/4/2018.
 */
@Dao
public interface DaoSpareItem {

    @Insert
    void insertSpareItemData(ArrayList<SpareItemData> spareItemData);

    @Insert
    void insertSpareItemDataList(List<SpareItemData> spareItemDatas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertOnlySingleRecord(SpareItemData spareItemData);

    @Update
    int UpdateSparePartes(SpareItemData spareItemData);

    @Query("SELECT * FROM spare_item")
    List<SpareItemData> fetchAllSpareItemData();

    @Query("SELECT * FROM spare_item WHERE service_key =:scall_key ")
    List<SpareItemData> fetchProductsById(String scall_key);

    @Query("SELECT count(*) from spare_item WHERE service_key=:scsp_key")
    boolean isOldEntry(String scsp_key);

    @Update
    void updateSpareItemData(SpareItemData spareItemData);

    @Delete
    void deleteSpareItemData(SpareItemData spareItemData);

    @Query("DELETE FROM spare_item")
    void clearSpareItemDataTable();

    @Query("DELETE FROM spare_item WHERE scl_key=:wl_key AND service_key=:seviceCallId AND scsp_key=:spare_code")
    void removeAddedProduct(String wl_key, String seviceCallId, String spare_code);

    @Query("UPDATE spare_item SET scl_key=:scall_id WHERE entry_key=-1 ")
    void updateTempProduct(String scall_id);

    @Query("UPDATE spare_item SET is_synced_to_server=0 WHERE service_key=:servicecallId AND service_key=:temp_id ")
    void saveSpareItem(String servicecallId, String temp_id);

    @Query("UPDATE spare_item SET scl_key =:scl_key WHERE service_key=:dummy_key")
    void upadteWorkLogKey(String dummy_key, String scl_key);

    @Query("UPDATE spare_item SET scsp_key =:scsp_key WHERE service_key=:dummy_key")
    void upadteSpareKey(String dummy_key, String scsp_key);

    @Query("DELETE FROM spare_item WHERE is_synced_to_server=2")
    void removeTempProduct();


}
