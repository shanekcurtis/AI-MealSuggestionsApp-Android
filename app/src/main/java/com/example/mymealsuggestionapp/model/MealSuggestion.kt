package com.example.mymealsuggestionapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
@TypeConverters(StringArrayConverter::class)
data class MealSuggestion(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val ingredients: Array<String>,
    val preparationInstructions: Array<String>,
    var isFavourite: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MealSuggestion) return false

        return id == other.id &&
                name == other.name &&
                ingredients.contentEquals(other.ingredients) &&
                preparationInstructions.contentEquals(other.preparationInstructions)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + ingredients.contentHashCode()
        result = 31 * result + preparationInstructions.contentHashCode()
        return result
    }
}

class StringArrayConverter {
    @TypeConverter
    fun fromStringArray(value: Array<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringArray(value: String): Array<String> {
        return value.split(",").toTypedArray()
    }
}