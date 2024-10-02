package com.bbyyxx2.database.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bbyyxx2.database.dao.QRRecordDao
import com.bbyyxx2.database.dao.ScanRecordDao
import com.bbyyxx2.database.entities.QRRecord
import com.bbyyxx2.database.entities.ScanRecord

@Database(version = 2, entities = [QRRecord::class, ScanRecord::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun qrRecordDao(): QRRecordDao

    abstract fun scanRecordDao(): ScanRecordDao

    companion object {

        const val DB_NAME = "qr_database"

        lateinit var instance: AppDatabase

        @JvmStatic
        fun init(context: Context) {
            try {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).addMigrations(MIGRATION_1_2).build()
            } catch (e: Exception){
                Log.d("Migration", "Migration failed :" + e.message);
//                if (e.message?.contains("migration") == true){
//
//                } else{
//                    e.printStackTrace()
//                }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Migration", "Migration from 1 to 2")
                database.execSQL("ALTER TABLE qr_records ADD COLUMN remark TEXT not null default '' ")
            }
        }
    }
}