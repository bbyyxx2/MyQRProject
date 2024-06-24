package com.example.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.QRRecordDao
import com.example.database.dao.ScanRecordDao
import com.example.database.entities.QRRecord
import com.example.database.entities.ScanRecord

@Database(version = 1, entities = [QRRecord::class, ScanRecord::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun qrRecordDao(): QRRecordDao

    abstract fun scanRecordDao(): ScanRecordDao

}