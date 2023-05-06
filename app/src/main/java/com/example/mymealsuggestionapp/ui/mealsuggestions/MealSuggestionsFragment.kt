package com.example.mymealsuggestionapp.ui.mealsuggestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymealsuggestionapp.databinding.FragmentMealSuggestionsBinding
import com.example.mymealsuggestionapp.model.MealSuggestion
import com.example.mymealsuggestionapp.viewmodel.MainViewModel
import com.example.mymealsuggestionapp.adapters.MealSuggestionsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MealSuggestionsFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel> {
        MainViewModel.MainViewModelFactory(requireContext())
    }

    private val adapter by lazy { MealSuggestionsAdapter() }

    private lateinit var binding: FragmentMealSuggestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMealSuggestionsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnClickListener(object : MealSuggestionsAdapter.OnItemClickListener {
            override fun onClick(mealSuggestion: MealSuggestion) {
                Toast.makeText(requireContext(), mealSuggestion.name, Toast.LENGTH_SHORT).show()
            }

            override fun onFavouriteClick(mealSuggestion: MealSuggestion) {
                if (mealSuggestion.isFavourite)
                    mainViewModel.removeFromSavedMeals(mealSuggestion)
                else mainViewModel.addToSavedMeals(mealSuggestion)
            }
        })

        binding.rvMeals.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentMealSuggestions.collectLatest {
                    adapter.updateMealSuggestions(it)
                }
            }
        }

        mainViewModel.getMealSuggestions()
    }
}