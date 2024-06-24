package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.entities.QRRecord

@Dao
interface QRRecordDao {
    @Insert
    fun insertQRRecord(qrRecord: QRRecord): Long

    @Query("SELECT * FROM qr_records")
    fun getAll(): List<QRRecord>

    @Query("SELECT * FROM qr_records ORDER BY createTime DESC")
    fun getAllOrderByCreateTime(): List<QRRecord>
}