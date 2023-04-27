package com.example.mymealsuggestionapp

import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mymealsuggestionapp.R
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import com.example.mymealsuggestionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val appBarConfiguration: AppBarConfiguration = Builder(
            R.id.navigation_home,
            R.id.navigation_Meal,
            R.id.navigation_save_Meal,
            R.id.navigation_setting
        )
            .build()
        val navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        setupWithNavController(binding!!.navView, navController)
    }
}