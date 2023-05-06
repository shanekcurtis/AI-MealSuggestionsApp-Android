package com.example.mymealsuggestionapp.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.adapters.MealSuggestionAdapter.MealSuggestionViewHolder
import com.example.mymealsuggestionapp.model.MealSuggestion
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.mymealsuggestionapp.R
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList


class MealSuggestionAdapter : RecyclerView.Adapter<MealSuggestionViewHolder>() {
    private var mealSuggestions: List<MealSuggestion>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealSuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal_suggestions, parent, false)
        return MealSuggestionViewHolder(view)
    }

    fun setMealSuggestions() {
        setMealSuggestions()
    }

    fun setMealSuggestions(mealSuggestions: List<MealSuggestion>) {
        this.mealSuggestions = mealSuggestions
    }

    override fun onBindViewHolder(holder: MealSuggestionViewHolder, position: Int) {
        val mealSuggestion = mealSuggestions[position]
        holder.mealNameTextView.text = mealSuggestion.mealName
        holder.ingredientsTextView.text = TextUtils.join("\n", mealSuggestion.ingredients)
        holder.favoriteIconImageView.setImageResource(if (mealSuggestion.isFavorite) R.drawable.fav else R.drawable.unfav)
        holder.favoriteIconImageView.setOnClickListener { v: View? ->
            mealSuggestion.isFavorite = !mealSuggestion.isFavorite
            if (mealSuggestion.isFavorite) {
                holder.favoriteIconImageView.setImageResource(R.drawable.fav)
            } else {
                holder.favoriteIconImageView.setImageResource(R.drawable.unfav)
            }
        }
    }

    override fun getItemCount(): Int {
        return mealSuggestions.size
    }

    inner class MealSuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealNameTextView: TextView
        val ingredientsTextView: TextView
        val favoriteIconImageView: ImageView

        init {
            mealNameTextView = itemView.findViewById(R.id.meal_name_text_view)
            ingredientsTextView = itemView.findViewById(R.id.ingredients_text_view)
            favoriteIconImageView = itemView.findViewById(R.id.favorite_icon_image_view)
        }
    }

    init {
        mealSuggestions = ArrayList()
    }
}