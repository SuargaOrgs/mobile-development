package com.dicoding.suargaapp.di

import android.content.Context
import com.dicoding.suargaapp.data.pref.UserPreference
import com.dicoding.suargaapp.data.pref.dataStore
import com.dicoding.suargaapp.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}