package com.fatih.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fatih.newsapp.R
import com.fatih.newsapp.databinding.FragmentArticleBinding
import com.fatih.newsapp.entities.Article
import com.fatih.newsapp.ui.activity.NewsActivity
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

class ArticleFragment: Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding:FragmentArticleBinding
    private val args:ArticleFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_article,container,false)
        viewModel=(requireActivity() as NewsActivity).viewModel
        val article=args.article
        setWebView(article)
        binding.fab.setOnClickListener {
            viewModel.insertArticle(article)
            Toast.makeText(requireContext(),"Successfully saved in database",Toast.LENGTH_SHORT).show()
        }
        addOnBackPressedDispatcher()
        return binding.root
    }

    private fun addOnBackPressedDispatcher(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.webView.canGoBack()){
                    binding.webView.goBack()
                }else{
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun setWebView(article: Article){
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl( article.url)
        }
    }

    override fun onStop() {
        println("onstop")
        findNavController().popBackStack(R.id.articleFragment,true)
        super.onStop()
    }

    override fun onDestroy() {
        println("ondestroy")
        super.onDestroy()
    }
}