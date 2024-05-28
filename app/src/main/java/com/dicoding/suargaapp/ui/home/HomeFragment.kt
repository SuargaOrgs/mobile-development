package com.dicoding.suargaapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.FragmentHomeBinding
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        observeViewModel()
    }

    private fun setupAction() {
        binding.buttonLogout.setOnClickListener {
            homeViewModel.logout()
        }
    }

    private fun observeViewModel() {
        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hai, ${user.name}"
            binding.tvGreeting.text = greetings
            if (user.hasCompletedAssessment) {
                binding.tvAssessment.text = "sudah mengisi asesmen"
            } else {
                binding.tvAssessment.text = "belum mengisi asesmen"
            }
        }
    }
}
