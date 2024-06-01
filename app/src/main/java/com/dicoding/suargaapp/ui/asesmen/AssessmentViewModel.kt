package com.dicoding.suargaapp.ui.asesmen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.remote.response.AssessmentResponse
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AssessmentViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveAssessment(
        tinggiBadan: Int,
        beratBadan: Int,
        aktivitasHarian: String,
        faktor: String,
        karbohidrat: Int,
        protein: Int,
        lemak: Int
    ): LiveData<AssessmentResponse> {
        val resultLiveData = MutableLiveData<AssessmentResponse>()
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.saveAssessment(tinggiBadan, beratBadan, aktivitasHarian, faktor, karbohidrat, protein, lemak)
                resultLiveData.postValue(result)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                resultLiveData.postValue(AssessmentResponse(error = true, message = errorMessage))
            } finally {
                _isLoading.value = false
            }
        }
        return resultLiveData
    }

}