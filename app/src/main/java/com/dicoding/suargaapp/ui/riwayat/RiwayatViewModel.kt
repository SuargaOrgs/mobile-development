package com.dicoding.suargaapp.ui.riwayat

import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.remote.response.HistoryResponse
import com.dicoding.suargaapp.data.repository.UserRepository

class RiwayatViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun historyFood(): HistoryResponse {
        return repository.historyFood()
    }

}