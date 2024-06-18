package com.dicoding.suargaapp.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.suargaapp.data.repository.UserRepository
import com.dicoding.suargaapp.di.Injection
import com.dicoding.suargaapp.ui.addfood.AddFoodViewModel
import com.dicoding.suargaapp.ui.asesmen.AssessmentViewModel
import com.dicoding.suargaapp.ui.camera.CameraViewModel
import com.dicoding.suargaapp.ui.home.HomeViewModel
import com.dicoding.suargaapp.ui.login.LoginViewModel
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.ui.resultscan.ResultScanViewModel
import com.dicoding.suargaapp.ui.signup.SignUpViewModel

class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AssessmentViewModel::class.java) -> {
                AssessmentViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResultScanViewModel::class.java) -> {
                ResultScanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddFoodViewModel::class.java) -> {
                AddFoodViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = AuthViewModelFactory(Injection.provideUserRepository(context))
    }
}