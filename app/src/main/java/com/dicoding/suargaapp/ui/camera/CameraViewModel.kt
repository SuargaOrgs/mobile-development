package com.dicoding.suargaapp.ui.camera

import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.remote.response.ScanPredictResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import okhttp3.MultipartBody

class CameraViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun predict(file: MultipartBody.Part): ScanPredictResponse {
        return repository.predict(file)
    }
}