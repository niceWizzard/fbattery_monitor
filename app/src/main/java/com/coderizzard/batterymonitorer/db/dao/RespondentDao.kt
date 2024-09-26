package com.coderizzard.batterymonitorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.coderizzard.batterymonitorer.db.entity.Respondent

@Dao
interface RespondentDao {
    @Query("SELECT * FROM Respondent")
    fun getRespondents(): List<Respondent>

    @Insert
    suspend fun createRespondent(respondent: Respondent)



}