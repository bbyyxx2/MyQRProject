package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_records")
data class QRRecord(
    @PrimaryKey(autoGenerate = true) val id:Long,
    val image:ByteArray,
    val content:String,
    val createTime:Long
) {
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
