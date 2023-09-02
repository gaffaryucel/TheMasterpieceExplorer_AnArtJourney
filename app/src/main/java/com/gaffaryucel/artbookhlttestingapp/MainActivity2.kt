package com.gaffaryucel.artbookhlttestingapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gaffaryucel.artbookhlttestingapp.databinding.ActivityMain2Binding
import com.gaffaryucel.artbookhlttestingapp.view.ArtDetailsFragment
import com.gaffaryucel.artbookhlttestingapp.view.ArtFragmentFactory
import com.gaffaryucel.artbookhlttestingapp.view.ArtListFragmentDirections
import com.gaffaryucel.artbookhlttestingapp.view.SearchArtFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        supportActionBar?.hide()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        var navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_notifications , R.id.navigation_discover,R.id.navigation_arts,
                R.id.navigation_arts
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.fab.setOnClickListener {

        }
    }
}
