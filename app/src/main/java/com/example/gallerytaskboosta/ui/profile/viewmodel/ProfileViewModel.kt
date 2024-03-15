package com.example.gallerytaskboosta.ui.profile.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerytaskboosta.network.ApiState
import com.example.gallerytaskboosta.repository.Repository
import com.example.gallerytaskboosta.utilities.checkNetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext private val application: Context,private val repository: Repository):ViewModel(){
    private val albumsMutableStateFlow : MutableStateFlow<ApiState> = MutableStateFlow (ApiState.Loading)

    val albums: StateFlow<ApiState> = albumsMutableStateFlow

    private val userMutableStateFlow : MutableStateFlow<ApiState> = MutableStateFlow (ApiState.Loading)

    val user: StateFlow<ApiState> = userMutableStateFlow

    fun getAllAlbums(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkNetworkConnectivity(application)) {
                repository.getAllAlbums(userId).catch { e ->
                    albumsMutableStateFlow.value = ApiState.Failure(e.message.toString())
                }.collect { d ->
                    albumsMutableStateFlow.value = ApiState.Success(d)
                }
            } else {
                albumsMutableStateFlow.value = ApiState.Failure("Failure: No internet connection")
            }
        }
    }
    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkNetworkConnectivity(application)) {
                repository.getUser(userId).catch { e ->
                    userMutableStateFlow.value = ApiState.Failure(e.message.toString())
                }.collect { d ->
                    userMutableStateFlow.value = ApiState.Success(d)
                }
            } else {
                userMutableStateFlow.value = ApiState.Failure("Failure: No internet connection")
            }
        }
    }

}