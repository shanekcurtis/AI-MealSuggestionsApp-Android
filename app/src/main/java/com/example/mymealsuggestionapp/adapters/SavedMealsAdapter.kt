package com.example.mymealsuggestionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.databinding.ItemSavedMealBinding
import com.example.mymealsuggestionapp.model.MealSuggestion

class SavedMealsAdapter(private var savedMeals: List<MealSuggestion> = ArrayList()) :
    RecyclerView.Adapter<SavedMealsAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSavedMealBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(savedMeals[position])
    }

    override fun getItemCount() = savedMeals.size

    fun updateMealSuggestions(newMeals: List<MealSuggestion>) {
        val diffCallback =
            MealSuggestionsAdapter.DiffCallback(savedMeals, newMeals)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        savedMeals = newMeals
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun interface OnItemClickListener {
        fun onRemove(mealSuggestion: MealSuggestion)
    }

    inner class ViewHolder(private val binding: ItemSavedMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mealSuggestion: MealSuggestion) {
            binding.mealName.text = mealSuggestion.name

            val ingredientsAdapter = MealIngredientsAdapter(mealSuggestion.ingredients)
            binding.rvIngredients.adapter = ingredientsAdapter

            val instructionsAdapter =
                MealInstructionsAdapter(mealSuggestion.preparationInstructions)
            binding.rvInstructions.adapter = instructionsAdapter

            binding.btnRemove.setOnClickListener {
                onItemClickListener?.onRemove(mealSuggestion)
            }
        }
    }

    class DiffCallback(
        private val oldList: List<MealSuggestion>,
        private val newList: List<MealSuggestion>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }
    }
}