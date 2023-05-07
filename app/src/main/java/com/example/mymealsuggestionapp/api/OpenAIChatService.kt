package com.example.mymealsuggestionapp.api

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.example.mymealsuggestionapp.model.MealSuggestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.seconds
import java.lang.System

@OptIn(BetaOpenAI::class, ExperimentalTime::class)
suspend fun openAIChatService(mealSuggestionFlow: MutableStateFlow<List<MealSuggestion>>): Unit = runBlocking {

    // Obtain OpenAI Key from environment variable
    //val apiKey = System.getenv("OPENAI_API_KEY")

    val generatedMealSuggestionList: List<MealSuggestion>

    val config = OpenAIConfig(
        //TODO: FIX
        //token = apiKey, (NOT WORKING)
        token = "OPENAI_API_KEY",
        timeout = Timeout(socket = 60.seconds),
        // additional configurations...
    )

    val openAI = OpenAI(config)

    @OptIn(BetaOpenAI::class)
    var chatCompletionRequest = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.System,
                content = "You are now a professional chef, generate a list of meal suggestions based on the given list of ingredients. When listing out the possible meals, format your response like the following (DO NOT print \\n in your response): “Meal Name: {meal name} \\n Ingredients used: \\n {measurement and name of ingredient 1} \\n {measurement and name of ingredient 2} \\n {measurement and name of ingredient 3}, etc (showing each individual ingredient on a new line under the label ‘Ingredients used:’’), Preparation Instructions: \\n {step 1} \\n {step 2} \\n {step 3}, etc (showing each individual step on a new line under the label ‘Preparation Instructions:’"
            ),
            ChatMessage(
                role = ChatRole.User,
                content = "Here is the list of ingredients: Flour, Eggs, Milk, Salt"
            )
        ),
        temperature = 0.0
    )

    @OptIn(BetaOpenAI::class)
    val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

    val content = extractContent(completion)

    println("Generated Meal Suggestions.")
    generatedMealSuggestionList = parseMealSuggestions(content)

    mealSuggestionFlow.value = generatedMealSuggestionList

    // Print output list of MealSuggestion objects for testing
    for (mealSuggestion in generatedMealSuggestionList) {
        println(mealSuggestion.toString())
    }

}

@OptIn(BetaOpenAI::class)
fun extractContent(completion: ChatCompletion): String {
    // Find the index of the "content=" string in the API response
    val contentIndex = completion.toString().indexOf("content=")

    // Find the index of the first "Meal Name: " after "content=" to get the starting index of the desired content
    val startIndex = completion.toString().indexOf("Meal Name: ", contentIndex)

    // Find the index of "name=null" in the API response
    val endIndex = completion.toString().lastIndexOf("name=null")

    // Extract the desired content from the API response using the start and end indices
    val extractedContent = completion.toString().substring(startIndex, endIndex).trim()

    return extractedContent
}

fun parseMealSuggestions(content: String): List<MealSuggestion> {
    val mealSuggestions = mutableListOf<MealSuggestion>()
    var mealName = ""
    val ingredients = ArrayList<String>()
    val preparation = ArrayList<String>()

    content.lines().forEach { line ->
        when {
            line.startsWith("Meal Name: ") -> {
                if (mealName.isNotEmpty()) {

                    // Initialize empty arrays to determine size for array
                    val ingredientsArray = arrayOfNulls<String>(ingredients.size)
                    val preparationArray = arrayOfNulls<String>(preparation.size)

                    // Prepare the MealSuggestion object to add to list
                    val mealToAdd = MealSuggestion(
                        id = 0,
                        name = mealName,
                        ingredients = ingredients.toArray(ingredientsArray),
                        preparationInstructions = preparation.toArray(preparationArray),
                        isFavourite = false
                    )

                    // Add the MealSuggestion object to the list of Meal Suggestions
                    mealSuggestions.add(mealToAdd)

                    // Clear out the current ingredients and preparation ArrayLists
                    ingredients.clear()
                    preparation.clear()

                }
                mealName = line.substring("Meal Name: ".length)
            }
            line.startsWith("Ingredients used:") || line.startsWith("Preparation Instructions:") -> {
                // Do nothing, skip these lines
            }

            line.startsWith("1.") || line.startsWith("1 ") -> { // The start of the preparation steps
                ingredients.addAll(preparation) // Move previous lines to ingredients list
                preparation.clear() // Clear the preparation list before adding new steps
                preparation.add(line.trim())
            }
            line.isNotEmpty() -> {
                if (preparation.isNotEmpty()) {
                    preparation.add(line.trim())
                } else {
                    ingredients.add(line.trim())
                }
            }
        }
    }
    // Add the last MealSuggestion
    if (mealName.isNotEmpty()) {

        // Initialize empty arrays to determine size of arrays
        val ingredientsArray = arrayOfNulls<String>(ingredients.size)
        val preparationArray = arrayOfNulls<String>(preparation.size)

        // Prepare the MealSuggestion object to add to list
        val mealToAdd = MealSuggestion(
            id = 0,
            name = mealName,
            ingredients = ingredients.toArray(ingredientsArray),
            preparationInstructions = preparation.toArray(preparationArray),
            isFavourite = false
        )

        // Add the MealSuggestion object to the list of Meal Suggestions
        mealSuggestions.add(mealToAdd)
    }

    return mealSuggestions
}