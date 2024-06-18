package com.dicoding.suargaapp.ui.resultscan

import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.remote.response.UploadResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ResultScanViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun upload(
        file: MultipartBody.Part,
        namaAktivitas: RequestBody,
        waktuMakan: RequestBody,
        idMakanan: RequestBody,
        porsi: RequestBody
    ): UploadResponse {
        return repository.upload(file, namaAktivitas, waktuMakan, idMakanan, porsi)
    }
}