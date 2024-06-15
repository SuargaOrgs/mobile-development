package com.dicoding.suargaapp.data.repository

import com.dicoding.suargaapp.data.pref.UserModel
import com.dicoding.suargaapp.data.pref.UserPreference
import com.dicoding.suargaapp.data.remote.response.AssessmentResponse
import com.dicoding.suargaapp.data.remote.response.DetailNutritionResponse
import com.dicoding.suargaapp.data.remote.response.GetAssessmentResponse
import com.dicoding.suargaapp.data.remote.response.ListFoodResponse
import com.dicoding.suargaapp.data.remote.response.LoginResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.remote.response.UploadResponse
import com.dicoding.suargaapp.data.remote.retrofit.ApiService
import com.dicoding.suargaapp.data.remote.retrofit.ScanApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
    private val scanApiService: ScanApiService
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

    suspend fun listFood() : ListFoodResponse {
        return apiService.listFood()
    }

    suspend fun listNutrition() : DetailNutritionResponse {
        return apiService.listNutrition()
    }

    suspend fun upload(
        file: MultipartBody.Part,
        namaAktivitas: RequestBody,
        waktuMakan: RequestBody,
        idMakanan: RequestBody,
        porsi: RequestBody
    ): UploadResponse {
        return scanApiService.upload(file, namaAktivitas, waktuMakan, idMakanan, porsi)
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            scanApiService: ScanApiService
        ) = UserRepository(userPreference, apiService, scanApiService)
    }
}