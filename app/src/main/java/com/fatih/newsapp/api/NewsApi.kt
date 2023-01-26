package com.fatih.newsapp.api

import com.fatih.newsapp.entities.NewsResponse
import com.fatih.newsapp.util.Constants.API_KEY
import com.fatih.newsapp.util.Constants.START_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=52e58260607f4d63a6c7f822f0ca7a4c
    //https://newsapi.org/v2/top-headlines?country=tr&apiKey=52e58260607f4d63a6c7f822f0ca7a4c

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(@Query("country") countryCode:String = "tr",
                                @Query("page") page:Int = START_PAGE,
                                @Query("apiKey") apiKey:String=API_KEY):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(@Query("q") query:String,
                           @Query("page") page:Int= START_PAGE,
                           @Query("apiKey") apiKey:String=API_KEY):Response<NewsResponse>

}