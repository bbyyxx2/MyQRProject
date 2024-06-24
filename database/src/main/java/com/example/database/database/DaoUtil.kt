package com.example.database.database

import android.content.Context
import androidx.room.Room

object DaoUtil {
    //数据库名
    private const val dbName: String = "qr_database"

    private val database: AppDatabase? = null

    fun initDatabase(context: Context){
        database?: run {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                dbName
            ).build()
        }
    }

    fun getDatabase(): AppDatabase? {
        return database;
    }

    //混合写法，在数据库第一次调用的时候初始化，但酌情考虑，性能应该有问题
//    fun getDatabase(context : Context): AppDatabase {
//        database?.let {
//            return it
//        } ?: run {
//            return Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                dbName
//            ).build()
//        }
//    }
}