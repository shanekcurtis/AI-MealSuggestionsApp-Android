package com.example.mymealsuggestionapp.model

data class MealSuggestion(
    val name: String,
    val ingredients: Array<String>,
    val preparationInstructions: Array<String>,

) {

    val isFavorite = false
}