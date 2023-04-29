package com.example.mymealsuggestionapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mymealsuggestionapp.model.Ingredient
import java.util.ArrayList

class ViewModel : ViewModel() {
    private var ingredientList: MutableList<Ingredient> = ArrayList()
    fun getIngredientList(): List<Ingredient> {
        return ingredientList
    }

    fun setIngredientList(ingredientList: MutableList<Ingredient>) {
        this.ingredientList = ingredientList
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredientList.add(ingredient)
    }

    fun removeIngredient(ingredient: Ingredient) {
        ingredientList.remove(ingredient)
    }
}