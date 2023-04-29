package com.example.mymealsuggestionapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mymealsuggestionapp.model.Ingredient
import com.example.mymealsuggestionapp.model.MealSuggestion
import java.util.ArrayList

class FragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val ingredientList: MutableList<Ingredient> = ArrayList()
    private val mealSuggestionList: MutableList<MealSuggestion> = ArrayList()
    fun getIngredientList(): List<Ingredient> {
        return ingredientList
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredientList.add(ingredient)
    }

    fun getMealSuggestionList(): List<MealSuggestion> {
        return mealSuggestionList
    }

    fun addMealSuggestion(mealSuggestion: MealSuggestion) {
        mealSuggestionList.add(mealSuggestion)
    }
}