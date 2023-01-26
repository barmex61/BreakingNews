package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatih.newsapp.R
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.databinding.FragmentSavedNewsBinding
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel:NewsViewModel
    private lateinit var binding:FragmentSavedNewsBinding
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_saved_news,container,false)
        viewModel=(requireActivity() as NewsActivity).viewModel
        setItemClickListener()
        observeSavedArticles()
        setupRecyclerView(binding.root)
        return binding.root
    }

    private fun setItemClickListener(){
        newsAdapter.setMyArticleOnClickListener {
            findNavController().navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it))
        }
    }

    private fun observeSavedArticles(){
        viewModel.savedArticlesList.observe(viewLifecycleOwner){
            newsAdapter.articleList=it
        }
    }

    private fun setupRecyclerView(view:View){
        val itemTouchHelperCallback=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val article=newsAdapter.articleList[viewHolder.adapterPosition]
                    viewModel.deleteArticle(article)
                    Snackbar.make(view,"Successfully deleted article",Snackbar.LENGTH_LONG)
                        .setAction("Undo"){
                            viewModel.insertArticle(article)
                        }.show()
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return true
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        binding.rvSavedNews.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=newsAdapter
        }
    }

}