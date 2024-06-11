package com.dicoding.suargaapp.data.repository

import android.app.Application
import com.dicoding.suargaapp.data.local.Article
import com.dicoding.suargaapp.data.local.ArticleDao
import com.dicoding.suargaapp.data.local.ArticleDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ArticleRepository(application: Application) {
    private val mArticleDao: ArticleDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ArticleDatabase.getDatabase(application)
        mArticleDao = db.articleDao()
    }

    fun getAllArticles() = mArticleDao.getAllArticle()

    fun insertAll(article: List<Article>) = executorService.execute { mArticleDao.insertAll(article) }


}