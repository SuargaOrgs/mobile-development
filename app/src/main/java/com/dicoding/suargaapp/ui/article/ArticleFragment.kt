package com.dicoding.suargaapp.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.pref.Article

class ArticleFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        viewPager = rootView.findViewById(R.id.viewPager)
        recyclerView = rootView.findViewById(R.id.recyclerView)

        val hotArticleList = listOf(
            Article(R.drawable.article_image_1, "Title 1", "Description 1"),
            Article(R.drawable.article_image_2, "Title 2", "Description 2"),
            Article(R.drawable.article_image_2, "Title 3", "Description 3")
        )

        val articleList = listOf(
            Article(R.drawable.article_image_1, "Title 1", "Description 1"),
            Article(R.drawable.article_image_2, "Title 2", "Description 2"),
            Article(R.drawable.article_image_2, "Title 3", "Description 3"),
            Article(R.drawable.article_image_2, "Title 4", "Description 4"),
            Article(R.drawable.article_image_2, "Title 5", "Description 5")

        )

        val viewPagerAdapter = ViewPagerAdapter(requireContext(), hotArticleList)
        viewPager.adapter = viewPagerAdapter

        val articleAdapter = ArticleAdapter(requireContext(), articleList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = articleAdapter

        return rootView
    }
}
