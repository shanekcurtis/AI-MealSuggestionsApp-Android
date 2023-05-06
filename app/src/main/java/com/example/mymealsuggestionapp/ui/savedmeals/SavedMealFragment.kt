package com.example.mymealsuggestionapp.ui.savedmeals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mymealsuggestionapp.databinding.FragmentSavedMealsBinding
import com.example.mymealsuggestionapp.viewmodel.MainViewModel
import com.example.mymealsuggestionapp.adapters.SavedMealsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedMealFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel> {
        MainViewModel.MainViewModelFactory(requireContext())
    }

    private val adapter by lazy {
        SavedMealsAdapter()
    }

    private lateinit var binding: FragmentSavedMealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSavedMealsBinding.inflate(layoutInflater)
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

        adapter.setOnClickListener {
            mainViewModel.removeFromSavedMeals(it)
        }

        binding.rvMeals.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.savedMealSuggestions.collectLatest {
                    adapter.updateMealSuggestions(it)
                }
            }
        }

        mainViewModel.getSavedMealSuggestions()
    }
}