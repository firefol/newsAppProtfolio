package com.example.l_tehapplication.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.l_tehapplication.databinding.FragmentDetailsBinding
import com.example.l_tehapplication.NewsApplication
import com.example.l_tehapplication.model.News
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by activityViewModels()

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

        detailsViewModel.newsLiveData.observe(activity as LifecycleOwner) {
            val date = it.date.split("T")
            binding.datedetail.text = date[0] + " " + date[1].substring(0,date[1].length - 1)
            binding.titledetail.text = it.title
            binding.textdetail.text = it.text
            Glide.with(binding.imagedetail.context)
                .load("http://dev-exam.l-tech.ru" + it.image)
                .into(binding.imagedetail)
        }
    }

}