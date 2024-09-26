package com.coderizzard.batterymonitorer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class BtPercentage(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val percentage : Int,
    val timestamp : Int,
    val respondentId : Int
)
