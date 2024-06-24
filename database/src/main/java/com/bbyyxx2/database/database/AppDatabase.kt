package com.bbyyxx2.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bbyyxx2.database.dao.QRRecordDao
import com.bbyyxx2.database.dao.ScanRecordDao
import com.bbyyxx2.database.entities.QRRecord
import com.bbyyxx2.database.entities.ScanRecord

@Database(version = 1, entities = [QRRecord::class, ScanRecord::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun qrRecordDao(): QRRecordDao

    abstract fun scanRecordDao(): ScanRecordDao

    companion object {

        private var instance: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "qr_database"
                ).build().also {
                    instance = it
                }
            }
        }
    }
}