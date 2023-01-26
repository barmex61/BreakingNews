package com.fatih.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.newsapp.R
import com.fatih.newsapp.databinding.ItemArticleRowBinding
import com.fatih.newsapp.entities.Article
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var myOnClickListener:((Article)->Unit)?=null

    fun setMyArticleOnClickListener(listener:(Article)->Unit){
        this.myOnClickListener=listener
    }

    private val diffUtilCallback=object :DiffUtil.ItemCallback<Article>(){
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }
    }

    private val asyncListDiffer=AsyncListDiffer(this,diffUtilCallback)

    var articleList:List<Article>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    inner class NewsViewHolder(val binding:ItemArticleRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding=DataBindingUtil.inflate<ItemArticleRowBinding>(LayoutInflater.from(parent.context),
            R.layout.item_article_row,parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.article=articleList[position]
        holder.binding.ivArticleImage.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.fade_transition))
        holder.binding.containerLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.fade_scale_animation))
        holder.itemView.setOnClickListener {
            myOnClickListener?.let {
                it(articleList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}