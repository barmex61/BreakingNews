package com.fatih.newsapp.repository

import androidx.lifecycle.LiveData
import com.fatih.newsapp.entities.Article
import com.fatih.newsapp.util.Constants.START_PAGE
import com.fatih.newsapp.util.Resource

interface NewsRepositoryInterface {

    suspend fun insertArticle(article: Article):Long
    suspend fun deleteArticle(article: Article)
    fun getAllSavedArticles():LiveData<List<Article>>
    fun getSelectedArticle(idInput:Int):Article
    suspend fun getBreakingNews(countryCode:String,page:Int=START_PAGE): Resource<List<Article>>
    suspend fun searchNews(query:String,page:Int=START_PAGE):Resource<List<Article>>

}