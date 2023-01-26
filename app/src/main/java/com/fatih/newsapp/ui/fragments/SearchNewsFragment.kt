package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.newsapp.R
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.databinding.FragmentSearchNewsBinding
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import com.fatih.newsapp.util.Constants.SEARCH_NEWS_TIME_DELAY
import com.fatih.newsapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {

    private lateinit var viewModel:NewsViewModel
    private lateinit var binding:FragmentSearchNewsBinding
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    private var job:Job?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search_news,container,false)
        viewModel=(requireActivity() as NewsActivity).viewModel
        setupRecyclerView()
        observeLiveData()
        searchOnTextChange()
        setItemClickListener()
        return binding.root
    }

    private fun setItemClickListener(){
        newsAdapter.setMyArticleOnClickListener {
            findNavController().navigate(SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it))
        }
    }

    private fun searchOnTextChange(){
        binding.etSearch.addTextChangedListener { editable->
            editable?.let {
                try {
                    job?.cancel()
                    val inputString=it.toString()
                    if(inputString.isNotEmpty()){
                        job=lifecycleScope.launch(Dispatchers.IO) {
                            delay(SEARCH_NEWS_TIME_DELAY)
                            viewModel.searchNews(inputString)
                        }
                    }else{
                        job?.cancel()
                    }
                }catch (e:Exception){
                    showToastMessage(e.message)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvSearchNews.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=newsAdapter
        }
    }

    private fun observeLiveData(){
        viewModel.searchList.observe(viewLifecycleOwner){ result->
            when(result.status){
                Status.SUCCESS->{
                    newsAdapter.articleList=result.data?: listOf()
                    showProgressBar(false)

                }
                Status.ERROR->{
                    showToastMessage(result.message)
                    showProgressBar(false)
                }
                Status.LOADING->{
                    showProgressBar(true)
                }
            }
        }
    }

    private fun showProgressBar(boolean: Boolean){
        if(boolean) binding.paginationProgressBar.visibility=View.VISIBLE
        else binding.paginationProgressBar.visibility=View.GONE
    }

    private fun showToastMessage(message:String?){
        Toast.makeText(requireContext(),message?:"Error occurred", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }
}