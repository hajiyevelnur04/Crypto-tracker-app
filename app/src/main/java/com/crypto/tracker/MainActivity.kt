package com.crypto.tracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.crypto.tracker.databinding.ActivityMainBinding
import com.crypto.tracker.utils.setupWithNavigationController

class MainActivity : AppCompatActivity() {

    private var destination = 0

    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationBar()

        // check if service need to start
        if(prefs?.isServiceRunnable() == true)
            startService()
        else
            stopService()
    }

    private fun startService() {
        if (!AlertTypeService.isRunning)
            startService(Intent(appContext, AlertTypeService::class.java))
    }

    private fun stopService() {
        if (AlertTypeService.isRunning)
            stopService(Intent(appContext, AlertTypeService::class.java))
    }

    private fun setupBottomNavigationBar() {
        setSupportActionBar(binding.toolbar)
        val navGraphIds = listOf(
            R.navigation.navigation_home,
            R.navigation.navigation_history,
            R.navigation.navigation_favorite)

        binding.navView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.navigation_home -> {
                }
                R.id.navigation_history -> {
                }
                R.id.navigation_favorite -> {
                }
            }
            Log.e("Data", it.title.toString())

            true
        }

        val controller = binding.navView.setupWithNavigationController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller.observe(this) { navController ->
            navController.removeOnDestinationChangedListener(addOnDestinationChangedListener)
            navController.addOnDestinationChangedListener(addOnDestinationChangedListener)
            setupActionBarWithNavController(navController)
        }

        var navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment

        var navController = navHostFragment.navController
        controller.postValue(navController)
    }

    @SuppressLint("RestrictedApi")
    private val addOnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->

            if (this@MainActivity.destination != destination.id) {
                this@MainActivity.destination = destination.id

                when (destination.id) {
                    R.id.homeFragment -> {
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }
                    else -> {
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                }

                when (destination.id) {

                    R.id.homeFragment,
                    R.id.historyFragment,
                    R.id.favoriteFragment
                    -> {
                        binding.navView.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.navView.visibility = View.GONE
                    }
                }
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}