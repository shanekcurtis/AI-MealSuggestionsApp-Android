package com.example.mymealsuggestionapp.ui.mealsuggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymealsuggestionapp.databinding.FragmentMealsuggestionsBinding

class MealSuggestionsFragment : Fragment() {
    private var binding: FragmentMealsuggestionsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealsuggestionsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}