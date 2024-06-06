package com.dicoding.suargaapp.data.repository

import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.pref.UserPreference
import com.dicoding.suargaapp.data.remote.response.AssessmentResponse
import com.dicoding.suargaapp.data.remote.response.GetAssessmentResponse
import com.dicoding.suargaapp.data.remote.response.LoginResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.remote.response.UploadImageResponse
import com.dicoding.suargaapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    suspend fun register(email: String, password: String, fullName: String, birthday: String, pregnancyDate: String): RegisterResponse {
        return apiService.register(email, password, fullName, birthday, pregnancyDate)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        if (response.error == false) {
            val name = response.data?.namaLengkap
            val pregnancy = response.data?.tanggalKehamilan
            val user = response.data?.token?.let { token ->
                name?.let { name ->
                    pregnancy?.let { pregnancy ->
                        UserModel(name, email, pregnancy,token, false)
                    }
                }
            }

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

    fun getToken(): Flow<String?> {
        return userPreference.getToken()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun saveAssessment(
        tinggiBadan: Int,
        beratBadan: Int,
        aktivitasHarian: String,
        faktor: String,
        karbohidrat: Int,
        protein: Int,
        lemak: Int
    ): AssessmentResponse {
        val response = apiService.saveAssessment(tinggiBadan, beratBadan, aktivitasHarian, faktor, karbohidrat, protein, lemak)
        return response
    }

    suspend fun getAssessmentResult(): GetAssessmentResponse {
        return apiService.getAssessmentResult()
    }

    suspend fun uploadImage(file: MultipartBody.Part): UploadImageResponse {
        return apiService.uploadImage(file)
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}