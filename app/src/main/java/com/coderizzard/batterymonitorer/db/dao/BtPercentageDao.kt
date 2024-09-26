package com.coderizzard.batterymonitorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.batterymonitorer.db.entity.BtStatusInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface BtPercentageDao {
    @Query("SELECT * FROM BtStatusInfo ORDER BY timestamp DESC LIMIT 200")
    fun getAll() : Flow<List<BtStatusInfo>>

    @Query("SELECT * FROM BtStatusInfo WHERE timestamp > :time ORDER BY timestamp DESC ")
    fun getAllOlderThan(time : String) : List<BtStatusInfo>
    @Insert()
    suspend fun create(info : BtStatusInfo)

}