package com.dicoding.suargaapp.ui.camera

import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.remote.response.UploadImageResponse
import com.dicoding.suargaapp.data.repository.UserRepository
import okhttp3.MultipartBody

class CameraViewModel(private val repository: UserRepository) : ViewModel() {

//    suspend fun uploadImage(file: MultipartBody.Part): UploadImageResponse {
//        return repository.uploadImage(file)
//    }
}