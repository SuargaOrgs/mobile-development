package com.dicoding.suargaapp.di

import android.content.Context
import com.dicoding.suargaapp.data.pref.UserPreference
import com.dicoding.suargaapp.data.pref.dataStore
import com.dicoding.suargaapp.data.remote.retrofit.ApiConfig
import com.dicoding.suargaapp.data.remote.retrofit.ScanApiConfig
import com.dicoding.suargaapp.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val token = user.token
        val apiService = ApiConfig.getApiService(token)
        val scanApiService = ScanApiConfig.getApiService(token)

        return UserRepository.getInstance(pref, apiService, scanApiService)
    }
}