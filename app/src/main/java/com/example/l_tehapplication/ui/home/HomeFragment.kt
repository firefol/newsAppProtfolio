package com.example.l_tehapplication.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.l_tehapplication.utils.NewsActionListener
import com.example.l_tehapplication.utils.NewsAdapter
import com.example.l_tehapplication.R
import com.example.l_tehapplication.databinding.FragmentHomeBinding
import com.example.l_tehapplication.NewsApplication
import com.example.l_tehapplication.model.News
import com.example.l_tehapplication.repository.NetworkRepository
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import com.example.l_tehapplication.ui.authorization.AuthorizathionViewModel
import com.example.l_tehapplication.ui.authorization.AuthorizathionViewModelFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerAdapter: NewsAdapter
    lateinit var homeViewModel:HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.toolbar.inflateMenu(R.menu.update)
        binding.toolbar.title = "Лента новостей"
        initRecycleView()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinner3)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            val retrofitService = RetroServiceInterface.getInstance()
            println(retrofitService)
            val networkRepository = NetworkRepository(retrofitService)
            homeViewModel = ViewModelProvider(this, HomeViewModelFactory(networkRepository))[HomeViewModel::class.java]
            homeViewModel.getLiveDataObserver().observe(viewLifecycleOwner) {
                if (it != null) {
                    recyclerAdapter.setNewsList(it)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
            homeViewModel.getPosts()
        }
        binding.toolbar.setOnMenuItemClickListener {
            item: MenuItem? ->
            when(item!!.itemId) {
                R.id.action_update -> homeViewModel.getPosts()
            }
            true
        }
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(Runnable {
            homeViewModel.getPosts()
        }, 120, 120, TimeUnit.SECONDS)
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        recyclerAdapter = NewsAdapter(object : NewsActionListener{
            override fun onNewsDetails(news: News) {
                NewsApplication.NewsDetail = news
                val controller = findNavController()
                controller.navigate(R.id.action_navigation_home_to_detailsFragment)
            }

        })
        binding.recyclerView.adapter = recyclerAdapter
    }


}