package com.example.mymealsuggestionapp.database

import com.example.mymealsuggestionapp.database.MealDao
import com.example.mymealsuggestionapp.model.MealSuggestion
import com.example.mymealsuggestionapp.database.MealsRepository
import kotlinx.coroutines.flow.Flow

class MealRepositoryImpl(private val mealDao: MealDao) : MealsRepository {

    override suspend fun insertMeal(mealSuggestion: MealSuggestion) {
        mealDao.insertMeal(mealSuggestion)
    }

    override suspend fun deleteMeal(mealSuggestion: MealSuggestion) {
        mealDao.deleteMeal(mealSuggestion)
    }

    override fun getSavedMealSuggestions(): Flow<List<MealSuggestion>> {
        return mealDao.getSavedMealSuggestions()
    }
}