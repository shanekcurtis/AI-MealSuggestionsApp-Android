package com.example.mymealsuggestionapp.database

import com.example.mymealsuggestionapp.model.MealSuggestion
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun insertMeal(mealSuggestion: MealSuggestion)

    suspend fun deleteMeal(mealSuggestion: MealSuggestion)

    fun getSavedMealSuggestions(): Flow<List<MealSuggestion>>
}