package com.fatih.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatih.newsapp.api.NewsApi
import com.fatih.newsapp.database.ArticleDao
import com.fatih.newsapp.entities.Article
import com.fatih.newsapp.util.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

class NewsRepository @Inject constructor(private val articleDao: ArticleDao,private val articleApi:NewsApi) :NewsRepositoryInterface {

    override suspend fun insertArticle(article: Article): Long {
        return try {
            articleDao.insertArticle(article)
        }catch (e:Exception){
            e.printStackTrace()
            -1L
        }
    }

    override suspend fun deleteArticle(article: Article) {
        try {
            articleDao.deleteArticle(article)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getAllSavedArticles(): LiveData<List<Article>> {
        return try {
            articleDao.getAllSavedArticles()
        }catch (e:Exception){
            e.printStackTrace()
            MutableLiveData()
        }
    }

    override suspend fun getBreakingNews(countryCode: String, page: Int): Resource<List<Article>> {
       return try {
           val result = articleApi.getBreakingNews(countryCode, page)
           if (result.isSuccessful){
               result.body()?.let {
                   Resource.success(it.articles)
               }?: Resource.error("Data null")
           }else{
               Resource.error(result.message())
           }
       }catch (e:Exception){
           Resource.error(e.message!!)
       }
    }

    override suspend fun searchNews(query: String, page: Int): Resource<List<Article>> {
        return try {
            val result = articleApi.searchNews(query, page)
            if (result.isSuccessful){
                result.body()?.let {
                    Resource.success(it.articles)
                }?: Resource.error("Data null")
            }else{
                Resource.error(result.message())
            }
        }catch (e:Exception){
            Resource.error(e.message!!)
        }
    }
}