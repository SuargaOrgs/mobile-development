package com.dicoding.suargaapp.ui.article

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.suargaapp.data.local.Article
import com.dicoding.suargaapp.data.repository.ArticleRepository

class ArticleViewModel(application: Application) :ViewModel() {
    private val mArticleRepository: ArticleRepository = ArticleRepository(application)

    fun getAllArticles() = mArticleRepository.getAllArticles()

    fun insert (article: List<Article>) {
        mArticleRepository.insertAll(article)
    }
}
