package com.fatih.newsapp.ui.fragmentfactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.fatih.newsapp.adapter.NewsAdapter
import com.fatih.newsapp.ui.fragments.BreakingNewsFragment
import com.fatih.newsapp.ui.fragments.SavedNewsFragment
import com.fatih.newsapp.ui.fragments.SearchNewsFragment
import javax.inject.Inject

class CustomFragmentFactory @Inject constructor(private val newsAdapter: NewsAdapter): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            SearchNewsFragment::class.java.name->SearchNewsFragment(newsAdapter)
            BreakingNewsFragment::class.java.name->BreakingNewsFragment(newsAdapter)
            SavedNewsFragment::class.java.name->SavedNewsFragment(newsAdapter)
            else->return super.instantiate(classLoader, className)
        }
    }
}