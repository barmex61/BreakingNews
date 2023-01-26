package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fatih.newsapp.R
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment @Inject constructor(private val newsAdapter: NewsAdapter): Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel:NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(requireActivity() as NewsActivity).viewModel
    }
}