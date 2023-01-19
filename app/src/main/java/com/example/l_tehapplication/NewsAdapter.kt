package com.example.l_tehapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.l_tehapplication.databinding.ItemNewsBinding
import com.example.l_tehapplication.model.News

interface NewsActionListener{

    fun onNewsDetails(news:News)

}


class NewsAdapter(
    private val actionListener: NewsActionListener
):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(), View.OnClickListener {

    private var news: List<News> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setNewsList(news: List<News>){
        this.news = news
    }

    override fun onClick(v: View) {
        val news = v.tag as News
        actionListener.onNewsDetails(news)

    }

    class NewsViewHolder(
        val binding: ItemNewsBinding
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return NewsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = news[position]
        val date = news.date.split("T")
        with(holder.binding) {
            holder.itemView.tag = news
            tittle.text = news.title
            textNews.text = news.text
            DateNews.text = date[0] + " " + date[1].substring(0, date[1].length - 1)
            if (news.image.isNotBlank()) {
                Glide.with(image.context)
                    .load("http://dev-exam.l-tech.ru" + news.image)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.ic_baseline_photo_24)
            }

        }
    }

    override fun getItemCount(): Int = news.size
}