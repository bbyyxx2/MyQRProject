package com.bbyyxx2.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "qr_records")
data class QRRecord(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    val image:ByteArray,
    val content:String,
    val createTime:Long
) {
    @Ignore
    constructor(image: ByteArray, content: String, createTime: Long) : this(0L, image, content, createTime)

    @Ignore
    constructor(content: String) : this(0L, byteArrayOf(), content, System.currentTimeMillis())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QRRecord

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
