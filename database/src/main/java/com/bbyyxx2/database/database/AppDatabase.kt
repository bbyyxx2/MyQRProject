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

        const val DB_NAME = "qr_database"

        lateinit var instance: AppDatabase

        @JvmStatic
        fun init(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}