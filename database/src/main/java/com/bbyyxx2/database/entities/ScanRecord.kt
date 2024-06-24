package com.bbyyxx2.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "scan_records")
data class ScanRecord(
    //不加默认值在kotlin中就是必填项，特性
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    val content:String,
    val createTime:Long
) {
    //但ava 编译器无法识别 Kotlin 的默认参数特性，所以需要单独写一个构造函数，0L在存入后会重新自增赋值
    @Ignore
    constructor(content: String, createTime: Long) : this(0L, content, createTime)
}
