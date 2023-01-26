package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.fatih.newsapp.R
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.databinding.FragmentBreakingNewsBinding
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import com.fatih.newsapp.util.Status

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news){

    private lateinit var viewModel:NewsViewModel
    private lateinit var binding:FragmentBreakingNewsBinding
    private lateinit var recyclerViewOnScrollListener: OnScrollListener
    private val newsAdapter by lazy {
        NewsAdapter()
    }

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
        recyclerViewOnScrollListener= object :OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!binding.rvBreakingNews.canScrollVertically(1) ){
                    println("dy $dy")
                    println("cant")
                    viewModel.breakingNewsPage++
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        }
        binding.rvBreakingNews.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=newsAdapter
        }
    }

    private fun observeLiveData(){
        viewModel.articleList.observe(viewLifecycleOwner){ result->
            when(result.status){
                Status.SUCCESS->{
                    binding.rvBreakingNews.addOnScrollListener(recyclerViewOnScrollListener)
                    newsAdapter.articleList=result.data?: listOf()
                    showProgressBar(false)
                }
                Status.ERROR->{
                    showToastMessage(result.message)
                    showProgressBar(false)
                }
                Status.LOADING->{
                    binding.rvBreakingNews.removeOnScrollListener(recyclerViewOnScrollListener)
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