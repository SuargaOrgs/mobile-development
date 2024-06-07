package com.dicoding.suargaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.remote.response.GetAssessmentResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun getAssessmentResult(): GetAssessmentResponse {
        return repository.getAssessmentResult()
    }

}