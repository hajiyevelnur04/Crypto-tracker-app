package com.crypto.tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.crypto.tracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
    }
}