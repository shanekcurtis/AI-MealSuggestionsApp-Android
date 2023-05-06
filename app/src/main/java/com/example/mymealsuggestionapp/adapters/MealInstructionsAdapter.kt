package com.example.mymealsuggestionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymealsuggestionapp.databinding.ItemMealInstructionBinding

class MealInstructionsAdapter(private val preparationInstructions: Array<String>) :
    RecyclerView.Adapter<MealInstructionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMealInstructionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(preparationInstructions[position])
    }

    override fun getItemCount() = preparationInstructions.size

    inner class ViewHolder(private val binding: ItemMealInstructionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(preparationInstruction: String) {
            binding.instruction.text = preparationInstruction
        }
    }
}