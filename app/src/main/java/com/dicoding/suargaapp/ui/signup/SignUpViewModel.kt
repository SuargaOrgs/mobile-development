package com.dicoding.suargaapp.ui.signup

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(email: String, password: String, fullName: String, birthday: String, pregnancyDate: String): LiveData<RegisterResponse> {
        val resultLiveData = MutableLiveData<RegisterResponse>()
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.register(email, password, fullName, birthday, pregnancyDate)
                if (result.error == false) {
                    val name = result.data?.namaLengkap
                    val user = result.data?.token?.let { token ->
                        name?.let { name ->
                            UserModel(name, email, token, true)
                        }
                    }

                    if (user != null) {
                        repository.saveSession(user)
                    }
                }
                resultLiveData.postValue(result)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                resultLiveData.postValue(RegisterResponse(error = true, message = errorMessage))
            } finally {
                _isLoading.value = false
            }
        }
        return resultLiveData
    }
}