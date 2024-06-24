package com.bbyyxx2.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bbyyxx2.database.entities.ScanRecord

@Dao
interface ScanRecordDao {
    @Insert
    fun insertScanRecord(scanRecord: ScanRecord): Long

    @Query("SELECT * FROM scan_records")
    fun getAll(): List<ScanRecord>

    //带排序的获取所有记录
    @Query("SELECT * FROM scan_records ORDER BY createTime DESC")
    fun getAllOrderByCreateTime(): List<ScanRecord>
}