package com.coderizzard.batterymonitorer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.util.Date

@Entity
data class BtPercentage(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val percentage : Int,
    val timestamp : LocalDateTime,
    val respondentId : Int
)
