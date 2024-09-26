package com.coderizzard.batterymonitorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.batterymonitorer.db.entity.BtPercentage

@Dao
interface BtPercentageDao {
    @Query("SELECT * FROM BtPercentage")
    fun getAll() : List<BtPercentage>

    @Insert()
    suspend fun create(info : BtPercentage)

}