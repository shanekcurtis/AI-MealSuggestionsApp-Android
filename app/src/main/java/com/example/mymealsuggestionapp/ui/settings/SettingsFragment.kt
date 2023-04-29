package com.example.mymealsuggestionapp.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.mymealsuggestionapp.R
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        view = inflater.inflate(R.layout.fragment_settings, container, false)
        val mySpinner = view.findViewById<Spinner>(R.id.nationality_spinner)
        val items = arrayOf("None", "American", "Asian", "European", "African", "Hispanic")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter
        return view
    }
}