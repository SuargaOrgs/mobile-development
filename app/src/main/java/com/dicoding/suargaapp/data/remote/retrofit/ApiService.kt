package com.dicoding.suargaapp.data.remote.retrofit

import com.dicoding.suargaapp.data.remote.response.AssessmentResponse
import com.dicoding.suargaapp.data.remote.response.GetAssessmentResponse
import com.dicoding.suargaapp.data.remote.response.ListFoodResponse
import com.dicoding.suargaapp.data.remote.response.LoginResponse
import com.dicoding.suargaapp.data.remote.response.RegisterResponse
import com.dicoding.suargaapp.data.remote.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fullName") fullName: String,
        @Field("birthday") birthday: String,
        @Field("pregnancyDate") pregnancyDate: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("assessment")
    suspend fun saveAssessment(
        @Field("tinggiBadan") tinggiBadan: Int,
        @Field("beratBadan") beratBadan: Int,
        @Field("aktivitasHarian") aktivitasHarian: String,
        @Field("faktor") faktor : String,
        @Field("karbohidrat") karbohidrat: Int,
        @Field("protein") protein: Int,
        @Field("lemak") lemak: Int
    ): AssessmentResponse

    @GET("assessment")
    suspend fun getAssessmentResult(): GetAssessmentResponse

//    @Multipart
//    @POST("https://suarga-app-dev-api.vercel.app/uploadGambar")
//    suspend fun uploadImage(
//        @Part file: MultipartBody.Part
//    ): UploadImageResponse

    @GET("nutrition/foodStorage")
    suspend fun listFood() : ListFoodResponse

}