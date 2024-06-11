package com.dicoding.suargaapp.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.suargaapp.ui.article.ArticleViewModel

class ArticleViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ArticleViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ArticleViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ArticleViewModelFactory::class.java) {
                    INSTANCE = ArticleViewModelFactory(application)
                }
            }
            return INSTANCE as ArticleViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}