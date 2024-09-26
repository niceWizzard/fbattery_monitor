package com.coderizzard.batterymonitorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coderizzard.batterymonitorer.db.entity.Respondent

@Dao
interface RespondentDao {
    @Query("SELECT * FROM respondent")
    suspend fun getAll() : List<Respondent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRespondent(respondent: Respondent)

}