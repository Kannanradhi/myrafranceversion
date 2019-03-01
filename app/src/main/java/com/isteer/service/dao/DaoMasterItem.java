package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.isteer.service.model.MasterItemData;

import java.util.List;

/**
 * Created by Bright on 27/4/2018.
 */
@Dao
public interface DaoMasterItem {

    @Insert
    void insertAllMasterItemData(MasterItemData... masterItemData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMasterItemDataList(List<MasterItemData> masterItemDatas);

    @Insert
    void insertOnlySingleRecord(MasterItemData masterItemData);

    @Query("SELECT * FROM master_item")
    List<MasterItemData> fetchAllMasterItemData();

    @Query("SELECT * FROM master_item  WHERE remarks LIKE :hint ")
    List<MasterItemData> fetchLikeProduct(String hint);

    @Query("SELECT TYPE FROM master_item")
    List<String> fetchAllBrand();

    @Query("SELECT count(*) from master_item WHERE mi_key=:id ")
    boolean isOldEntry(String id);

    @Query("SELECT * FROM master_item WHERE is_synced_to_server=0 ")
    List<MasterItemData> getLocalMasterItemDatas();

    @Query("SELECT * FROM master_item ")
    MasterItemData getSingleMasterItemData();

    @Query("SELECT mi_key FROM master_item where short_name =:short_name ")
    String getMikey(String short_name);

    @Update
    void updateMasterItemData(MasterItemData masterItemData);

    @Delete
    void deleteMasterItemData(MasterItemData masterItemData);

    @Query("DELETE FROM master_item")
    void clearMasterItemDataTable();


}
