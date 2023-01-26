package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.newsapp.R
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.databinding.FragmentBreakingNewsBinding
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import com.fatih.newsapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment @Inject constructor(private val newsAdapter: NewsAdapter): Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel:NewsViewModel
    private lateinit var binding:FragmentBreakingNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_breaking_news,container,false)
        viewModel=(requireActivity() as NewsActivity).viewModel
        setupRecyclerView()
        observeLiveData()
        setItemClickListener()
        return binding.root
    }

    private fun setItemClickListener(){
        newsAdapter.setMyArticleOnClickListener {
            findNavController().navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it))
        }
    }

    private fun setupRecyclerView(){
        binding.rvBreakingNews.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=newsAdapter
        }
    }

    private fun observeLiveData(){
        viewModel.articleList.observe(viewLifecycleOwner){ result->
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
        Toast.makeText(requireContext(),message?:"Error occurred",Toast.LENGTH_SHORT).show()
    }

}