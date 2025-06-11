package com.samir.koinrestapiexample.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.samir.koinrestapiexample.R
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.databinding.ActivityMainBinding
import com.samir.koinrestapiexample.presentation.adapter.UserListAdapter
import com.samir.koinrestapiexample.presentation.utils.NetworkResult
import com.samir.koinrestapiexample.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private var menuFilter: MenuItem? = null

    private val userViewModel: UserViewModel by scope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        getData()
    }

    private fun getData() {
        userViewModel.getUserData()
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

                        setDataToRecyclerView(it.data)
                        Log.d("MainActivity", "Total data ${it.data[0]}::")
                    }
                }
            }
        }
    }

    private fun setDataToRecyclerView(data: List<User>) {
        binding.recyclerview.adapter = UserListAdapter(data)
        menuFilter?.isVisible = true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    //filter option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        menuFilter = menu?.findItem(R.id.filter)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                filterDataOnBaseOfEmail()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun filterDataOnBaseOfEmail() {
        if (userViewModel.users.value is NetworkResult.Success) {
            val listOfUsers = (userViewModel.users.value as NetworkResult.Success).data
            val filterListByEmail = userViewModel.filterDataByEmail("biz", listOfUsers)
            setDataToRecyclerView(filterListByEmail)
        }
    }
}