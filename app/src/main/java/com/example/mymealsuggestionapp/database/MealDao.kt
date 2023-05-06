package com.example.mymealsuggestionapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymealsuggestionapp.model.MealSuggestion
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealSuggestion: MealSuggestion)

    @Query("Select * From MealSuggestion")
    fun getSavedMealSuggestions(): Flow<List<MealSuggestion>>

    @Delete
    fun deleteMeal(mealSuggestion: MealSuggestion)
}