package com.bbyyxx2.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bbyyxx2.database.entities.QRRecord

@Dao
interface QRRecordDao {
    @Insert
    fun insertQRRecord(qrRecord: QRRecord): Long

    @Update
    fun updateQRRecord(qrRecord: QRRecord)

    @Query("SELECT * FROM qr_records ORDER BY createTime DESC")
    fun getAllOrderByCreateTime(): List<QRRecord>

    @Query("SELECT * FROM qr_records WHERE id = :id")
    fun getQRRecordById(id: Long): QRRecord?

    @Query("DELETE FROM qr_records WHERE id = :id")
    fun deleteQRRecordById(id: Long)

    @Delete
    fun deleteQRRecord(qrRecord: QRRecord)

    @Query("DELETE FROM qr_records")
    fun deleteAll()

    @Query("SELECT * FROM qr_records WHERE content LIKE '%' || :key || '%' OR remark LIKE '%' || :key || '%' ORDER BY createTime DESC")
    fun selectQRRecordList(key: String): List<QRRecord>
}