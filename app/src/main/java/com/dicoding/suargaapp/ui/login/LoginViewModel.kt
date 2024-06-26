package com.dicoding.suargaapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.error == false) {
                    val name = response.data?.namaLengkap
                    val pregnancy = response.data?.tanggalKehamilan
                    val user = response.data?.token?.let { token ->
                        name?.let { name ->
                            pregnancy?.let { pregnancy ->
                                // nanti ganti
                                UserModel(name, email, pregnancy, token, true)
                            }
                        }
                    }

                    if (user != null) {
                        repository.saveSession(user)
                    }

                    _loginResult.value = user?.let { LoginResult.Success(it) }
                } else {
                    val errorResponse = ErrorResponse(error = true, message = response.message ?: "Unknown error")
                    _loginResult.value = LoginResult.Error(errorResponse)
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                val errorResponse = ErrorResponse(error = true, message = errorMessage)
                _loginResult.value = LoginResult.Error(errorResponse)
            } finally {
                _isLoading.value = false
            }
        }
    }

    sealed class LoginResult {
        data class Success(val user: UserModel) : LoginResult()
        data class Error(val errorResponse: ErrorResponse) : LoginResult()
    }
}