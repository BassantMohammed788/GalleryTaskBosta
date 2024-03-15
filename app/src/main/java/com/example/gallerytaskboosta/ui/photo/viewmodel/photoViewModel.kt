package com.example.gallerytaskboosta.ui.photo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gallerytaskboosta.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class photoViewModel @Inject constructor(private val repository: Repository):ViewModel(){
}