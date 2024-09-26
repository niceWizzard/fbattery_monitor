package com.coderizzard.batterymonitorer.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class BtStatusInfo(
    val percentage : Int,
    val timestamp : LocalDateTime,
    val respondentId : Int,
    val temperature : Int,
    @PrimaryKey(autoGenerate = true) val id : Int=0,
)
