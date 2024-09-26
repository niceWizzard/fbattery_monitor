package com.coderizzard.batterymonitorer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coderizzard.batterymonitorer.db.dao.BtPercentageDao
import com.coderizzard.batterymonitorer.db.dao.RespondentDao
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import com.coderizzard.batterymonitorer.db.entity.Respondent

@Database(entities = [Respondent::class, BtPercentage::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun respondentDao(): RespondentDao
    abstract fun btPercetageDao() : BtPercentageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}