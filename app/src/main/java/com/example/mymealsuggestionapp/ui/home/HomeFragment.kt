package com.example.mymealsuggestionapp.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymealsuggestionapp.R
import com.example.mymealsuggestionapp.databinding.FragmentHomeBinding
import com.example.mymealsuggestionapp.model.Ingredient
import com.example.mymealsuggestionapp.MainActivity
import com.example.mymealsuggestionapp.viewmodel.MainViewModel
import com.example.mymealsuggestionapp.adapters.IngredientsAdapter
import com.example.mymealsuggestionapp.utils.AppPreferences
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel> {
        MainViewModel.MainViewModelFactory(requireContext())
    }

    private val adapter by lazy { IngredientsAdapter() }

    private val preferences by lazy { AppPreferences(requireContext()) }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
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

        binding.btnAddIngredient.setOnClickListener {
            val nameOfIngredient = binding.etIngredient.text.toString()
            if (nameOfIngredient.isNotBlank()) {
                mainViewModel.addIngredient(Ingredient(nameOfIngredient = nameOfIngredient))
                binding.etIngredient.text.clear()
            } else Toast.makeText(
                requireContext(),
                "Please enter an ingredient.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.generateMeals.setOnClickListener {
            if (mainViewModel.getIngredients() == "") {
                Toast.makeText(
                    requireContext(),
                    "Please enter at least 1 ingredient.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var influence = ""
                if (preferences.getNationalityOfInfluence() != null) {
                    influence = preferences.getNationalityOfInfluence().toString()
                }
                mainViewModel.clearCurrentMealSuggestions()
                mainViewModel.getMealSuggestions(influence = influence)
                (activity as? MainActivity)?.navigateToNavBarDestination(R.id.mealSuggestions)
            }

        }

        adapter.setOnClickListener {
            mainViewModel.removeIngredient(it)
        }

        binding.rvIngredients.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentIngredients.collectLatest {
                    adapter.updateIngredients(it)
                }
            }
        }
    }
}