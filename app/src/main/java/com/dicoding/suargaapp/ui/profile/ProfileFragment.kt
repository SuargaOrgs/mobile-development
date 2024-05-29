package com.dicoding.suargaapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.FragmentHomeBinding
import com.dicoding.suargaapp.databinding.FragmentProfileBinding
import com.dicoding.suargaapp.ui.home.HomeViewModel
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class ProfileFragment : Fragment() {

    private val profleViewModel by viewModels<ProfileViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.buttonLogout.setOnClickListener {
            profleViewModel.logout()
        }
    }
}