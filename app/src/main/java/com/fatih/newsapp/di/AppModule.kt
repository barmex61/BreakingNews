package com.fatih.newsapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.fatih.newsapp.api.NewsApi
import com.fatih.newsapp.database.ArticleDao
import com.fatih.newsapp.database.ArticleDatabase
import com.fatih.newsapp.repository.NewsRepository
import com.fatih.newsapp.repository.NewsRepositoryInterface
import com.fatih.newsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient()=OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun provideNewsApi(okHttpClient: OkHttpClient)=Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build().create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideArticleDao(@ApplicationContext context:Context)= Room.databaseBuilder(context,ArticleDatabase::class.java,"ArticleDatabase")
        .build().articleDao()

    @Singleton
    @Provides
    fun provideNewsRepo(articleDao:ArticleDao,articleApi: NewsApi)=NewsRepository(articleDao, articleApi) as NewsRepositoryInterface

}