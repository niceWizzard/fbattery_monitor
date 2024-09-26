package com.coderizzard.batterymonitorer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Respondent (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name ="first_name") val firstName : String,
    @ColumnInfo(name ="last_name") val lastName : String,
    @ColumnInfo(name = "year") val year : Int,
    @ColumnInfo(name = "program") val program : String,
    @ColumnInfo(name = "section") val section : String,
)

data class RespondentWithBtPercent(
    @Embedded val respondent: Respondent,
    @Relation(
        parentColumn = "id",
        entityColumn = "respondentId"
    )
    val btPercentages : List<BtPercentage>
)