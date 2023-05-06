package com.example.mymealsuggestionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.R
import com.example.mymealsuggestionapp.databinding.ItemMealSuggestionsBinding
import com.example.mymealsuggestionapp.model.MealSuggestion

class MealSuggestionsAdapter(private var mealsSuggestions: List<MealSuggestion> = ArrayList()) :
    RecyclerView.Adapter<MealSuggestionsAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMealSuggestionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, mealsSuggestions[position])
    }

    override fun getItemCount() = mealsSuggestions.size

    fun updateMealSuggestions(newMealsSuggestions: List<MealSuggestion>) {
        val diffCallback = DiffCallback(mealsSuggestions, newMealsSuggestions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mealsSuggestions = newMealsSuggestions
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemMealSuggestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, mealSuggestion: MealSuggestion) {

            binding.mealName.text = mealSuggestion.name

            val adapter = MealIngredientsAdapter(mealSuggestion.ingredients)
            binding.rvIngredients.adapter = adapter

            if (mealSuggestion.isFavourite)
                binding.btnFavourite.setImageResource(R.drawable.ic_favorite_selected)
            else
                binding.btnFavourite.setImageResource(R.drawable.ic_favorite_unselected)

            binding.btnFavourite.setOnClickListener {
                onItemClickListener?.onFavouriteClick(mealSuggestion)

                mealSuggestion.isFavourite = !mealSuggestion.isFavourite
                notifyItemChanged(position)
            }

            binding.root.setOnClickListener {
                onItemClickListener?.onClick(mealSuggestion)
            }
        }
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(mealSuggestion: MealSuggestion)

        fun onFavouriteClick(mealSuggestion: MealSuggestion)
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