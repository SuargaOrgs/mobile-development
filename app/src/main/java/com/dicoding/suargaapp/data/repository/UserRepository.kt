package com.dicoding.suargaapp.data.repository

import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.pref.UserPreference
import com.dicoding.suargaapp.data.remote.response.LoginResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (response.error == false) {
            val user = response.loginResult?.token?.let { UserModel(email, it, true) }
            if (user != null) {
                saveSession(user)
            }
        }
        return response
    }
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}