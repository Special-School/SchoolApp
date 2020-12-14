package com.specialschool.schoolapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.specialschool.schoolapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.visibility = when (destination.id) {
                R.id.fragment_school_detail -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        /*val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_search, R.id.navigation_settings))

        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navView.setupWithNavController(navController)
    }
}
