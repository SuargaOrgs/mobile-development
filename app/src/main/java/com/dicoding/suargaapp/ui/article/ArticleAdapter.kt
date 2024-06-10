package com.dicoding.suargaapp.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.pref.Article

class ArticleAdapter(
    private val context: Context,
    private val articleList: List<Article>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.imageView.setImageResource(article.photoResId)
        holder.titleView.text = article.title
        holder.descriptionView.text = article.description
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_item_photo)
        val titleView: TextView = itemView.findViewById(R.id.tv_article_title)
        val descriptionView: TextView = itemView.findViewById(R.id.tv_desc_article)
    }
}
