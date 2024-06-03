package com.dicoding.suargaapp.data.pref

data class UserModel (
    val name: String,
    val email: String,
    val pregnancyDate: String,
    val token: String,
    val isLogin: Boolean = false
)