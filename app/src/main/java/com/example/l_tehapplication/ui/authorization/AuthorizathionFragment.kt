package com.example.l_tehapplication.ui.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.l_tehapplication.R
import com.example.l_tehapplication.databinding.FragmentAuthorizathionBinding
import com.example.l_tehapplication.NewsApplication
import com.example.l_tehapplication.repository.NetworkRepository
import com.example.l_tehapplication.retrofit.RetroServiceInterface
import com.example.l_tehapplication.utils.Setting

class AuthorizathionFragment : Fragment() {


    //private val settings by lazy { NewsApplication.settings }
    private lateinit var settings:Setting
    lateinit var viewModel: AuthorizathionViewModel
    private lateinit var binding: FragmentAuthorizathionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizathionBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings = Setting(requireContext())
        val retrofitService = RetroServiceInterface.getInstance()
        val mainRepository = NetworkRepository(retrofitService)
        viewModel = ViewModelProvider(this, AuthorizathionViewModelFactory(mainRepository))[AuthorizathionViewModel::class.java]
        binding.imageLogo.setImageResource(R.drawable.newsletterbanner)
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
        if (settings.phone == "" && settings.password == "" && !settings.checkPhoneMask) {
            viewModel.getPhoneMask()
            settings.checkPhoneMask = true
        }
        else {
            binding.editTextTextPhone.setText(settings.phone)
            binding.editTextTextPassword.setText(settings.password)
        }

    }

}