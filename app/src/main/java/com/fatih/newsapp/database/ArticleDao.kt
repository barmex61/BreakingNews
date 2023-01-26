package com.fatih.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fatih.newsapp.entities.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article):Long

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM Article")
    fun getAllSavedArticles():LiveData<List<Article>>

    @Query("SELECT * FROM Article WHERE id = :idInput")
    suspend fun getSelectedArticle(idInput:Int):Article

}