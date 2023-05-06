package com.example.mymealsuggestionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.databinding.ItemIngredientBinding
import com.example.mymealsuggestionapp.model.Ingredient

class IngredientsAdapter(private var ingredients: List<Ingredient> = ArrayList()) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount() = ingredients.size

    fun updateIngredients(newIngredients: List<Ingredient>) {
        val diffCallback = DiffCallback(ingredients, newIngredients)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        ingredients = newIngredients
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.ingredientName.text = ingredient.nameOfIngredient

            binding.root.setOnClickListener {
                onItemClickListener?.onClick(ingredient)
            }
        }
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun interface OnItemClickListener {
        fun onClick(ingredient: Ingredient)
    }

    class DiffCallback(
        private val oldList: List<Ingredient>,
        private val newList: List<Ingredient>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].nameOfIngredient == newList[newItemPosition].nameOfIngredient
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