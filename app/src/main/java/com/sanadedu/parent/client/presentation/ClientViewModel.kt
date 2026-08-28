package com.sanadedu.parent.client.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.data.UserCredentials
import com.sanadedu.parent.client.data.repository.ClientRepositoryImpl
import com.sanadedu.parent.client.domain.repository.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ClientViewModel(context: Context) : ViewModel() {

    private val clientRepository: ClientRepository = ClientRepositoryImpl(context)

    private val _userCredentialsState = MutableStateFlow(UserCredentials())
    val userCredentialsState: StateFlow<UserCredentials> = _userCredentialsState

    init {
        viewModelScope.launch {
            fetchUserCredentials()
        }
    }

    private suspend fun fetchUserCredentials() {
        _userCredentialsState.value = clientRepository.getUserPreferences()
    }

    fun setUserCredentials(token: String, username: String, profileImage: String, callback: () -> Unit) {
        viewModelScope.launch {
            clientRepository.setUserCredentials(
                token = token,
                username = username,
                profileImage = profileImage
            )
            fetchUserCredentials()
        }.invokeOnCompletion {
            callback()
        }
    }

    fun updateClientStatus(status: ClientStatus) {
        viewModelScope.launch {
            clientRepository.updateClientStatus(status)
        }
    }

    fun logout(callback: () -> Unit) {
        viewModelScope.launch {
            clientRepository.clearUserData()
        }.invokeOnCompletion { callback() }
    }



    class ClientViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClientViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}