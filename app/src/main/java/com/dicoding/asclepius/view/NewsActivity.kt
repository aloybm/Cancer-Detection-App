package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(NewsViewModel::class.java)
        // Set the LayoutManager to horizontal
        val layoutManager = LinearLayoutManager(this)
        binding.tvNews.layoutManager = layoutManager


        // Removed DividerItemDecoration as it's not typically used in carousels

        newsViewModel.getListNews()

        newsViewModel.listNews.observe(this) { news ->
            displayNews(news)
        }
    }

    private fun displayNews(news: List<ArticlesItem>) {
        val adapter = NewsAdapter()
        binding.tvNews.adapter = adapter
        adapter.submitList(news)
    }
}
