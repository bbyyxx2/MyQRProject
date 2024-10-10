package com.bbyyxx2.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bbyyxx2.database.entities.ScanRecord

@Dao
interface ScanRecordDao {
    @Insert
    fun insertScanRecord(scanRecord: ScanRecord): Long

    @Update
    fun updateScanRecord(scanRecord: ScanRecord)

    //带排序的获取所有记录
    @Query("SELECT * FROM scan_records ORDER BY createTime DESC")
    fun getAllOrderByCreateTime(): List<ScanRecord>

    @Query("SELECT * FROM scan_records WHERE id = :id")
    fun getScanRecordById(id: Long): ScanRecord?

    @Query("Delete FROM scan_records WHERE id = :id")
    fun deleteScanRecordById(id: Long)

    @Delete
    fun deleteScanRecord(scanRecord: ScanRecord)

    @Query("Delete FROM scan_records")
    fun deleteAll()

    @Query("SELECT * FROM scan_records WHERE remark LIKE '%' || :key || '%' ORDER BY createTime DESC")
    fun selectScanRecordList(key: String): List<ScanRecord>
}