package com.samir.koinrestapiexample.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.domain.UserUseCase
import com.samir.koinrestapiexample.presentation.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(val userUseCase: UserUseCase) : ViewModel() {

    private val _users = MutableStateFlow<NetworkResult<List<User>>>(NetworkResult.Loading)
    val users: StateFlow<NetworkResult<List<User>>>
        get() = _users

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                _users.value = NetworkResult.Loading
                val userList = userUseCase.getUserList()
                _users.value = NetworkResult.Success(userList)
            } catch (e: Exception) {
                _users.value = NetworkResult.Error(e.message ?: "", e)
            }
        }
    }
}