package com.dicoding.suargaapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAllArticle(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(article: List<Article>)

}