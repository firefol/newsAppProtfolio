package com.example.l_tehapplication.ui.authorization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.l_tehapplication.R
import com.example.l_tehapplication.databinding.FragmentAuthorizathionBinding
import com.example.l_tehapplication.databinding.FragmentHomeBinding
import com.example.l_tehapplication.ltehApplication
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import kotlinx.coroutines.*

class AuthorizathionFragment : Fragment() {

    private val settings by lazy { ltehApplication.settings }
    private lateinit var viewModel: AuthorizathionViewModel
    private lateinit var binding: FragmentAuthorizathionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizathionBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthorizathionViewModel::class.java]
        binding.imageLogo.setImageResource(R.drawable.lteh)
        val controller = findNavController()
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner){
            if (it != null){
                binding.editTextTextPhone.setText(it)
            }
        }
        viewModel.getLiveDataObserverLogin().observe(viewLifecycleOwner){
            if (it != null && it == true){
                settings.phone = binding.editTextTextPhone.text.toString()
                settings.password = binding.editTextTextPassword.text.toString()
                controller.navigate(R.id.action_authorizathionFragment_to_navigation_home)
            } else {
                binding.editTextTextPassword.setBackgroundResource(R.drawable.drawable_error)
                binding.textViewError.visibility = VISIBLE
            }
        }
        binding.button.setOnClickListener {
            val params = HashMap<String?, String?>()
            params["phone"] =  binding.editTextTextPhone.text?.split("+")?.get(1).toString()
            params["password"] = binding.editTextTextPassword.text.toString()
            viewModel.Login(params)
        }
        if (settings.phone == "" && settings.password == "") {
            viewModel.getPhoneMask()
        }
        else {
            binding.editTextTextPhone.setText(settings.phone)
            binding.editTextTextPassword.setText(settings.password)
        }

    }

}