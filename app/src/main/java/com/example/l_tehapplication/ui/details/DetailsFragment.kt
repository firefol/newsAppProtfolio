package com.example.l_tehapplication.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.l_tehapplication.R
import com.example.l_tehapplication.databinding.FragmentDetailsBinding
import com.example.l_tehapplication.ltehApplication

class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = ltehApplication.NewsDetail.date.split("T")
        binding.datedetail.text = date[0] + " " + date[1].substring(0,date[1].length - 1)
        binding.titledetail.text = ltehApplication.NewsDetail.title
        binding.textdetail.text = ltehApplication.NewsDetail.text
        Glide.with(binding.imagedetail.context)
                .load("http://dev-exam.l-tech.ru" + ltehApplication.NewsDetail.image)
                .into(binding.imagedetail)
    }

}