package com.samir.koinrestapiexample.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.samir.koinrestapiexample.databinding.ActivityMainBinding
import com.samir.koinrestapiexample.presentation.utils.NetworkResult
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            userViewModel.users.collect {
                when (it) {
                    is NetworkResult.Loading -> {
                        showLoading(true)
                    }

                    is NetworkResult.Error -> {
                        showLoading(false)
                        Log.d("MainActivity", "Failed to load Data::")
                        it.exception.printStackTrace()
                    }

                    is NetworkResult.Success -> {
                        showLoading(false)

                        Log.d("MainActivity", "Total data ${it.data[0]}::")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}