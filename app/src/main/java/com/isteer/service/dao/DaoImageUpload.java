package com.isteer.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.isteer.service.model.ImagePathData;
import com.isteer.service.model.MasterItemData;

import java.util.List;

@Dao

public interface DaoImageUpload {

    @Insert
    void insertAllImageUpload(ImagePathData... ImageUpload);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImageUpload(List<ImagePathData> imagePathData);

    @Insert
    void insertOnlySingleRecord(ImagePathData ImagePathData);

    @Query("SELECT * FROM aerp_upload_files")
    List<ImagePathData> fetchAllMasterItemData();


}
