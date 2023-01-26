package com.fatih.newsapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fatih.newsapp.R
import com.fatih.newsapp.databinding.ActivityNewsBinding
import com.fatih.newsapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewsBinding
    lateinit var viewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_news)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this)[NewsViewModel::class.java]
        setupNavController()
    }

    private fun setupNavController(){
        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

}