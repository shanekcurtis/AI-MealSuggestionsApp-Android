package com.example.mymealsuggestionapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.mymealsuggestionapp.R
import com.example.mymealsuggestionapp.databinding.FragmentSettingsBinding
import com.example.mymealsuggestionapp.utils.AppPreferences

class SettingsFragment : Fragment() {

    private val preferences by lazy { AppPreferences(requireContext()) }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
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

        val nationalities = resources.getStringArray(R.array.nationalities)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            nationalities
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nationalitySpinner.adapter = adapter

        binding.nationalitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    preferences.saveNationalityOfInfluence(nationalities[position])
                    preferences.saveNationalityOfInfluenceItemPosition(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.nationalitySpinner.setSelection(preferences.getNationalityOfInfluenceItemPosition())
    }
}