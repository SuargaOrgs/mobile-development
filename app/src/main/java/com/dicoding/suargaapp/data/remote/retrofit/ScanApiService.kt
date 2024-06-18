package com.dicoding.suargaapp.data.remote.retrofit

import com.dicoding.suargaapp.data.remote.response.ScanPredictResponse
import com.dicoding.suargaapp.data.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ScanApiService {

    @Multipart
    @POST("nutrition/upload")
    suspend fun upload(
        @Part file: MultipartBody.Part,
        @Part("namaAktivitas") namaAktivitas: RequestBody,
        @Part("waktuMakan") waktuMakan: RequestBody,
        @Part("idMakanan") idMakanan: RequestBody,
        @Part("porsi") porsi: RequestBody
    ): UploadResponse

    @Multipart
    @POST("nutrition/predict")
    suspend fun predict(
        @Part file: MultipartBody.Part
    ): ScanPredictResponse

}