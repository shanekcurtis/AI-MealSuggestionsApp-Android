package com.example.mymealsuggestionapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymealsuggestionapp.model.MealSuggestion

@Database(entities = [MealSuggestion::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {

    abstract val mealDao: MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null
        private const val DATABASE_NAME = "meals_db"

        fun getInstance(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(context: Context): MealDatabase {
            return Room.databaseBuilder(context, MealDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}