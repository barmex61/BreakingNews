package com.fatih.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.newsapp.entities.Article
import com.fatih.newsapp.repository.NewsRepositoryInterface
import com.fatih.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepositoryInterface):ViewModel() {

    private val _articleList=MutableLiveData<Resource<List<Article>>>()
    val articleList:LiveData<Resource<List<Article>>>
        get() = _articleList

    private val _searchList=MutableLiveData<Resource<List<Article>>>()
    val searchList:LiveData<Resource<List<Article>>>
        get() = _searchList

    var breakingNewsPage=1
    var searchNewsPage=1

    init {
        getBreakingNews("tr")
    }

    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        _articleList.value=Resource.loading(null,null)
        _articleList.value=newsRepository.getBreakingNews(countryCode, breakingNewsPage)
    }

    fun searchNews(query:String)=viewModelScope.launch {
        _searchList.value= Resource.loading(null,null)
        _searchList.value= newsRepository.searchNews(query,searchNewsPage)
    }
}