package com.c23ps160.nutriscan.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c23ps160.nutriscan.Model.Article
import com.c23ps160.nutriscan.Model.QuickFood
import com.c23ps160.nutriscan.R

class ArticleAdapter(private val articleList : List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val articleImageView : ImageView = itemView.findViewById( R.id.articleImage)
        val articleTitleTextView : TextView = itemView.findViewById( R.id.articleTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_card , parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.articleImageView.setImageResource(article.articleImage)
        holder.articleTitleTextView.text = article.articleTitle
    }
}