package com.example.mymealsuggestionapp.adapters

import com.example.mymealsuggestionapp.model.Ingredient
import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.adapters.IngredientAdapter.IngredientViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.mymealsuggestionapp.R
import android.widget.TextView
import android.widget.ImageButton

class IngredientAdapter(private val ingredientList: MutableList<Ingredient>) :
    RecyclerView.Adapter<IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.ingredientTextView.text = ingredient.name
        holder.deleteButton.setOnClickListener { v: View? ->
            ingredientList.remove(ingredient)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientTextView: TextView
        val deleteButton: ImageButton

        init {
            ingredientTextView = itemView.findViewById(R.id.ingredient_text)
            deleteButton = itemView.findViewById(R.id.delete_icon)
        }
    }
}