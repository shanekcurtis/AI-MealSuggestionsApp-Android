package com.example.mymealsuggestionapp.ui.savedmeals

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymealsuggestionapp.databinding.FragmentSavedmealsBinding

class SavedMealFragment : Fragment() {
    private var binding: FragmentSavedmealsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedmealsBinding.inflate(
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