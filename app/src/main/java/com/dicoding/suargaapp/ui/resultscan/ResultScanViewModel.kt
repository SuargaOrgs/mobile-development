package com.dicoding.suargaapp.ui.resultscan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.remote.response.UploadResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ResultScanViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _imageUri = MutableLiveData<Uri?>()
//    val imageUri: LiveData<Uri?> get() = _imageUri
//
//    fun setImageUri(uri: Uri) {
//        _imageUri.value = uri
//    }

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