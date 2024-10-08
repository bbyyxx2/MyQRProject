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
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
            } catch (e: Exception){
                Log.d("Database", "Migration failed :" + e.message);
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Database", "Migration from 1 to 2")
                database.execSQL("ALTER TABLE qr_records ADD COLUMN remark TEXT not null default '' ")
                Log.d("Database", "Migration success")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Database", "Migration from 2 to 3")
                database.execSQL("ALTER TABLE qr_records ADD COLUMN red INTEGER not null default 0 ")
                database.execSQL("ALTER TABLE qr_records ADD COLUMN green INTEGER not null default 0 ")
                database.execSQL("ALTER TABLE qr_records ADD COLUMN blue INTEGER not null default 0 ")
                Log.d("Database", "Migration success")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("Database", "Migration from 3 to 4")
                database.execSQL("ALTER TABLE scan_records ADD COLUMN remark TEXT not null default '' ")
                Log.d("Database", "Migration success")
            }
        }
    }
}