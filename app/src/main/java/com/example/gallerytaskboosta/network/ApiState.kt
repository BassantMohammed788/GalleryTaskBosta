package com.example.gallerytaskboosta.network

sealed class ApiState{
    class Success<T>(val data : T) : ApiState()
    class Failure(val msg: String) : ApiState()
    data object Loading : ApiState()
}