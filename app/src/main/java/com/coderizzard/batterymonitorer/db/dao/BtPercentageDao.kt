package com.coderizzard.batterymonitorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import kotlinx.coroutines.flow.Flow

@Dao
interface BtPercentageDao {
    @Query("SELECT * FROM BtPercentage ORDER BY timestamp DESC")
    fun getAll() : Flow<List<BtPercentage>>

    @Insert()
    suspend fun create(info : BtPercentage)

}